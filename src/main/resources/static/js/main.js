document.addEventListener('DOMContentLoaded', () => {
    const calendar = document.getElementById('calendar');
    const currentMonthElement = document.getElementById('current-month');
    const selectedHabitNameElement = document.getElementById('selected-habit-name');
    const currentDateElement = document.getElementById('current-date');

    let habitNames;
    let habitId;
    let currentDate = new Date();
    const today = new Date().toLocaleDateString('sv-SE');

    let lastDate = currentDate;
    let selectedHabitName = '';
    let fetchingLogs = false;
    let checkboxFetchingLogs = false;
    let isCheckbox = false;
    let habitLogs = {};

    function getCurrentMonth(date) {
        return date.toISOString().slice(0, 7);
    }

    const firstHabit = document.querySelector('.nox-habits');
    if (firstHabit) {
        const habitElem = firstHabit.querySelector('.habit-elem');
        const habitInput = firstHabit.querySelector('input[name="id"]');

        if (habitElem && habitInput) {
            selectedHabitName = habitElem.textContent;
            selectedHabitNameElement.textContent = selectedHabitName;
            habitId = habitInput.value;
            firstHabit.classList.add('active');

            renderHabitLogs(habitId);
            renderAllHabitLogs(habitId);

            updateCurrentMonthLabel(lastDate);
        } else {
            console.error('Error: Missing habit elements.');
        }
    }

    const date = new Date();
    const options = {weekday: 'long'};
    const dayOfWeek = date.toLocaleString('en-US', options);
    document.getElementById('current-weekday').textContent = dayOfWeek;

    document.getElementById('go-today').addEventListener('click', () => {
        lastDate = new Date();
        renderCalendar(lastDate);
    });

    const dateInput = Object.assign(document.createElement('input'), {
        type: 'date',
        className: 'dateinput',
        style: 'display: none'
    });
    document.getElementById('controls').appendChild(dateInput);

    document.getElementById('go-date').addEventListener('click', () => {
        dateInput.style.display = 'block';
        dateInput.focus();
    });

    dateInput.addEventListener('change', debounce(() => {
        lastDate = new Date(dateInput.value);
        renderCalendar(lastDate);
        dateInput.style.display = 'none';
    }, 500));

    async function renderCalendar(date) {
        if (!(date instanceof Date) || isNaN(date)) {
            throw new Error('Invalid date object passed to renderCalendar.');
        }

        updateCurrentMonthLabel(date);

        const {year, month, firstDayIndex, lastDay, prevLastDay, nextDays} = getMonthDetails(date);

        const fragment = document.createDocumentFragment();

        renderWeekdays(fragment);
        renderPreviousMonthDays(fragment, year, month, firstDayIndex, prevLastDay);
        renderCurrentMonthDays(fragment, year, month, lastDay);
        renderNextMonthDays(fragment, year, month, nextDays);

        calendar.innerHTML = '';
        calendar.appendChild(fragment);

        if (habitId) {
            const currentMonth = getCurrentMonth(date);
            if (habitLogs[habitId] && habitLogs[habitId][currentMonth]) {
                applyHabitLogsToCalendar(habitLogs[habitId][currentMonth]);
            } else {
                await renderHabitLogs(habitId);
            }
        } else {
            console.error('habitId is undefined or not found');
        }
    }

    function updateCurrentMonthLabel(date) {
        currentMonthElement.textContent = date.toLocaleString('default', {month: 'long', year: 'numeric'});
    }

    function getMonthDetails(date) {
        const year = date.getFullYear();
        const month = date.getMonth();

        const firstDayIndex = new Date(year, month, 1).getDay();
        const lastDay = new Date(year, month + 1, 0).getDate();
        const prevLastDay = new Date(year, month, 0).getDate();

        const lastDayWeekIndex = (new Date(year, month, lastDay).getDay() + 7) % 7;
        const nextDays = lastDayWeekIndex < 6 ? 6 - lastDayWeekIndex : 0;

        return {year, month, firstDayIndex, lastDay, prevLastDay, nextDays};
    }

    function renderWeekdays(fragment) {
        const daysOfWeek = ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'];
        daysOfWeek.forEach(day => {
            const dayDiv = document.createElement('div');
            dayDiv.className = 'day-of-week';
            dayDiv.innerHTML = `<div>${day}</div>`;
            fragment.appendChild(dayDiv);
        });
    }

    function renderPreviousMonthDays(fragment, year, month, firstDayIndex, prevLastDay) {
        for (let x = firstDayIndex; x > 0; x--) {
            const prevMonthDate = new Date(year, month, 0);
            prevMonthDate.setDate(prevLastDay - x + 1);
            const dayDiv = createDayDiv('prev-month', prevMonthDate);
            fragment.appendChild(dayDiv);
        }
    }

    function renderCurrentMonthDays(fragment, year, month, lastDay) {
        const today = new Date();
        const isCurrentMonth = year === today.getFullYear() && month === today.getMonth();

        for (let i = 1; i <= lastDay; i++) {
            const dateStr = `${year}-${String(month + 1).padStart(2, '0')}-${String(i).padStart(2, '0')}`;
            const dayDiv = document.createElement('div');
            dayDiv.className = `day${isCurrentMonth && i === today.getDate() ? ' today' : ''}`;
            dayDiv.setAttribute('data-date', dateStr);
            dayDiv.textContent = i;

            fragment.appendChild(dayDiv);
        }
    }

    function renderNextMonthDays(fragment, year, month, nextDays) {
        for (let j = 1; j <= nextDays; j++) {
            const nextMonthDate = new Date(year, month + 1, j);
            const dayDiv = createDayDiv('next-month', nextMonthDate);
            fragment.appendChild(dayDiv);
        }
    }

    function createDayDiv(className, date) {
        const dayDiv = document.createElement('div');
        dayDiv.className = `day ${className}`;
        dayDiv.setAttribute('data-date', date.toLocaleDateString('sv-SE'));
        dayDiv.textContent = date.getDate();
        return dayDiv;
    }

    let updatingLogs = false;

    async function updateHabitLog(habitId, logDate, isChecked) {
        if (updatingLogs || !habitId) return;
        updatingLogs = true;

        try {
            const response = await fetchApi(`api/habits/${habitId}/logs`, isChecked ? 'POST' : 'DELETE', {date: logDate});
            if (response) {
                const currentMonth = getCurrentMonth(new Date(logDate));
                if (habitLogs[habitId]) {
                    delete habitLogs[habitId][currentMonth];
                }
                await renderHabitLogs(habitId);
            }
        } catch (error) {
            console.error('Error updating habit log:', error);
        } finally {
            updatingLogs = false;
        }
    }

    calendar.addEventListener('click', (event) => {
        if (!habitId) return console.error('habitId is undefined or not found');

        const dayElement = event.target.closest('.day');
        if (!dayElement) return;

        const dateStr = dayElement.getAttribute('data-date');
        const clickedDate = new Date(dateStr).setHours(0, 0, 0, 0);
        const today = new Date().setHours(0, 0, 0, 0);

        const lastDateMonth = lastDate.getMonth();
        const lastDateYear = lastDate.getFullYear();

        const clickedDateObj = new Date(dateStr);
        const clickedMonth = clickedDateObj.getMonth();
        const clickedYear = clickedDateObj.getFullYear();

        if (clickedDate > today) {
            return;
        }

        if (clickedMonth !== lastDateMonth || clickedYear !== lastDateYear) {
            return;
        }

        updateHabitLog(habitId, dateStr, !dayElement.classList.contains('completed'))
            .catch(error => console.error('Error updating habit log:', error));
    });

    async function renderAllHabitLogs(habitId) {
        if (checkboxFetchingLogs) return;
        checkboxFetchingLogs = true;

        try {
            const todayLogsResponse = await fetch(`/api/users/user/logs/today`);
            const todayLogs = await todayLogsResponse.json();

            applyLogsToCheckboxes(todayLogs, habitId);
        } catch (errorData) {
            console.error('Error fetching habit logs:', errorData);
        } finally {
            checkboxFetchingLogs = false;
        }
    }

    async function renderHabitLogs(habitId) {
        if (fetchingLogs) return;
        fetchingLogs = true;
        if (!habitId) {
            console.error('habitId is not valid');
            return;
        }

        try {
            const lastYearMonth = getCurrentMonth(lastDate);

            habitLogs[habitId] = habitLogs[habitId] || {};
            if (habitLogs[habitId] && habitLogs[habitId][lastYearMonth]) {
                console.log(`Using cached data for habit ${habitId}`);
                applyHabitLogsToCalendar(habitLogs[habitId][lastYearMonth]);
                fetchingLogs = false;
                return;
            }

            const monthLogsResponse = await fetch(`/api/${habitId}/logs/${lastYearMonth}`);
            const monthLogs = await monthLogsResponse.json();

            if (!habitLogs[habitId]) {
                habitLogs[habitId] = {};
            }
            habitLogs[habitId][lastYearMonth] = monthLogs;

            applyHabitLogsToCalendar(monthLogs);

            const currentYearMonth = new Date().toISOString().slice(0, 7);

            if (lastYearMonth === currentYearMonth) {
                applyLogsToCheckbox(monthLogs, habitId);
            }

        } catch (error) {
            console.error('Error fetching habit logs:', error);
        } finally {
            fetchingLogs = false;
        }
    }

    function applyLogsToCheckbox(logs, curHabitId) {
        const today = new Date().toLocaleDateString('en-US');
        const checkbox = document.querySelector(`.habit-checkbox[data-habit-id="${curHabitId}"]`);
        if (!checkbox) return;

        checkbox.checked = logs.some(log =>
            new Date(log.date).toLocaleDateString('en-US') === today
        );
    }

    function applyLogsToCheckboxes(logs, curHabitId = null) {
        const checkboxes = Array.from(document.querySelectorAll('.habit-checkbox'));
        const today = new Date().toLocaleDateString('en-US');

        checkboxes.forEach(checkbox => {
            checkbox.checked = false;
            const checkboxHabitId = checkbox.getAttribute('data-habit-id');

            logs.forEach(log => {
                const logDate = new Date(log.date).toLocaleDateString('en-US');

                if (log.habit_id.toString() === checkboxHabitId && today === logDate) {
                    checkbox.checked = true;
                }
            });
        });
    }

    function applyHabitLogsToCalendar(logs) {
        clearCalendar();
        const dayElements = new Map();
        document.querySelectorAll('.day[data-date]').forEach(day => {
            dayElements.set(day.getAttribute('data-date'), day);
        });

        logs.forEach(({date}) => {
            const formattedDate = date.split('T')[0];
            dayElements.get(formattedDate)?.classList.add('completed');
        });
    }

    function setupHabitClickHandlers() {
        if (isCheckbox) return;
        habitNames = document.querySelectorAll('.nox-habits');

        habitNames.forEach(habit => {
            habit.addEventListener('click', () => {
                if (habit.classList.contains('active')) return;

                habitNames.forEach(h => h.classList.remove('active'));
                habit.classList.add('active');

                const formElement = habit.querySelector('form');
                const newHabitId = formElement.querySelector('input[name="id"]').value;

                const habitName = habit.querySelector('.habit-elem').textContent;
                selectedHabitNameElement.textContent = habitName;
                selectedHabitName = habitName;

                clearCalendar();

                if (newHabitId !== habitId) {
                    habitId = newHabitId;

                    if (habitId) {
                        renderHabitLogs(habitId);
                    } else {
                        console.error('habitId is not valid');
                        return;
                    }
                    renderCalendar(lastDate);
                }
            });
        });
    }

    function clearCalendar() {
        const days = document.querySelectorAll('.day');
        days.forEach(day => day.classList.remove('completed'));
    }

    setupHabitClickHandlers();

    const form = document.querySelector('.habit-input form');
    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        const formData = new FormData(form);
        const formObject = Object.fromEntries(formData.entries());
        const newHabit = await fetchApi(form.action, 'POST', formObject, 'Failed to add habit');
        if (newHabit) {
            addHabitToList(newHabit);
            form.reset();
        }
    });

    function addHabitToList(habit) {
        const habitList = document.querySelector('.habit-list');
        const habitDiv = document.createElement('div');
        habitDiv.classList.add('habit');
        habitDiv.innerHTML = `
                    <div class="nox-habits">
                        <form id="habitForm${habit.id}" action="/api/users/user/logs" method="post">
                            <input type="hidden" value="${habit.id}" name="id"/>
                            <input type="hidden" value="${habit.user_id}" name="user_id"/>
                            <input type="checkbox" class="checkbox-round habit-checkbox" data-habit-id="${habit.id}"/>
                            <div class="habit-elem">
                                <div class="habit-name" data-habit-id="${habit.id}">${habit.name}</div>
                            </div>
                        </form>
                        <form id="deleteHabitForm${habit.id}" action="api/habits/${habit.id}" method="delete" style="display:inline;">
                            <input type="hidden" value="${habit.id}" name="id"/>
                            <button type="submit" class="delete-button"></button>
                        </form>
                    </div>
                `;
        habitList.appendChild(habitDiv);
        habitDiv.querySelector('.habit-checkbox').addEventListener('change', handleCheckboxChange);
        habitDiv.querySelector('.delete-button').addEventListener('click', deleteHabit);
        setupHabitClickHandlers();
        const newHabitElem = habitDiv.querySelector('.habit-name');
        addDoubleClickListener(newHabitElem);
    }

    document.querySelectorAll('.habit-checkbox').forEach(checkbox => {
        checkbox.addEventListener('change', handleCheckboxChange);
    });

    async function handleCheckboxChange(event) {
        isCheckbox = true;

        const isChecked = this.checked;
        const habitId = this.getAttribute('data-habit-id');

        if (!habitId) {
            console.error('habitId is not valid');
            return;
        }
        await updateHabitLog(habitId, today, isChecked)
            .finally(() => {
                isCheckbox = false;
            });
    }

    function debounce(func, delay) {
        let timer;
        return (...args) => {
            clearTimeout(timer);
            timer = setTimeout(() => func(...args), delay);
        };
    }

    async function deleteHabit(e) {
        e.preventDefault();
        const form = e.target.closest('form');
        const habitId = form.querySelector('input[name="id"]').value;

        await fetchApi(`api/habits/${habitId}`, 'DELETE');
        removeHabitFromList(habitId);
    }

    function removeHabitFromList(habitId) {
        const habitDiv = document.querySelector(`.habit input[data-habit-id='${habitId}']`).closest('.habit');
        if (habitDiv) {
            habitDiv.remove();
        }
        if (document.querySelectorAll('.habit').length === 0) {
            resetCalendar();
        } else {
            const firstHabit = document.querySelector('.nox-habits');
            const habitName = firstHabit.querySelector('.habit-elem').textContent;
            selectedHabitNameElement.textContent = habitName;
            selectedHabitName = habitName;
            firstHabit.classList.add('active');

            habitId = firstHabit.querySelector('input[name="id"]').value;
            updateHabitId(habitId);

            clearCalendar();

            if (!habitId) {
                console.error('habitId is not valid');
                return;
            }
            renderHabitLogs(habitId);
        }

    }

    function updateHabitId(newHabitId) {
        habitId = newHabitId;
    }

    const calendarTitle = document.getElementById('selected-habit-name');

    function resetCalendar() {
        calendarTitle.textContent = 'Habit';
        clearCalendar();
    }

    document.querySelectorAll('.delete-button').forEach(button => {
        button.addEventListener('click', deleteHabit);
    });

    let isEditing = false;

    function handleDoubleClick(event) {
        if (isEditing) return;
        isEditing = true;

        const habitElem = event.target;
        const habitId = habitElem.getAttribute('data-habit-id');
        if (!habitId) {
            console.error('Habit ID not found');
            isEditing = false;
            return;
        }

        const oldName = habitElem.textContent.trim();
        const input = document.createElement('input');
        input.type = 'text';
        input.value = oldName;
        input.className = 'habit-name-input';

        habitElem.textContent = '';
        habitElem.appendChild(input);
        input.focus();

        const saveChanges = async () => {
            const newName = input.value.trim();
            input.removeEventListener('blur', saveChanges);
            input.removeEventListener('keydown', handleKeydown);

            if (!newName || newName === oldName) {
                habitElem.textContent = oldName;
                isEditing = false;
                return;
            }

            try {
                await updateHabitName(habitId, newName);
                habitElem.textContent = newName;

                const normalizedOldName = oldName.trim();
                const normalizedSelectedHabitName = selectedHabitName.trim();

                if (normalizedSelectedHabitName === normalizedOldName) {
                    selectedHabitNameElement.textContent = newName;
                    selectedHabitName = newName;
                }
            } catch (error) {
                console.error('Error updating habit name:', error);
                habitElem.textContent = oldName;
            } finally {
                isEditing = false;
            }
        };

        const handleKeydown = (e) => {
            if (e.key === 'Enter') {
                e.preventDefault();
                saveChanges();
            } else if (e.key === 'Escape') {
                habitElem.textContent = oldName;
                isEditing = false;
            }
        };

        input.addEventListener('keydown', handleKeydown);
        input.addEventListener('blur', saveChanges, {once: true});
    }

    function addDoubleClickListener(newHabitElem) {
        newHabitElem.addEventListener('touchstart', function (event) {
            let pressTimer;
            pressTimer = setTimeout(() => handleLongPress(event), 500);
        });
        newHabitElem.addEventListener('touchend', () => {
            clearTimeout(pressTimer);
        });
        newHabitElem.addEventListener('touchmove', () => {
            clearTimeout(pressTimer);
        });
        newHabitElem.addEventListener('touchcancel', () => {
            clearTimeout(pressTimer);
        });
        newHabitElem.addEventListener('dblclick', (event) => {
            handleDoubleClick(event);
        });

    }

    document.querySelectorAll('.habit-name').forEach(addDoubleClickListener);

    async function updateHabitName(habitId, newName) {
        await fetchApi(`api/habits/${habitId}`, 'PATCH', {id: habitId, name: newName}, "Update habit name error")
    }

    async function fetchApi(url, method = 'GET', body = null, errorMessage = 'Fetch error') {
        const options = {
            method,
            headers: {
                'Content-Type': 'application/json'
            },
            ...(body && {body: JSON.stringify(body)})
        };

        try {
            const response = await fetch(url, options);
            const responseText = await response.text();

            if (!response.ok) {
                console.error('HTTP Error:', response.status, response.statusText, 'Response:', responseText);
                throw new Error(`Error fetching data: ${response.statusText} - ${responseText}`);
            }
            if (!responseText.trim()) {
                console.warn('Warning: Empty response body');
                return null;
            }
            try {
                return JSON.parse(responseText);
            } catch (jsonError) {
                console.warn('Response is not JSON, returning as text');
                return responseText;
            }
        } catch (error) {
            console.error(`${errorMessage}:`, error);
            return null;
        }
    }

    function displayCurrentDate() {
        const options = {day: 'numeric', month: 'long'};
        const formattedDate = currentDate.toLocaleDateString('en-US', options);
        currentDateElement.textContent = formattedDate;
    }

    document.getElementById('controls').addEventListener('click', (event) => {
        if (event.target.id === 'next-month') changeMonth(1);
        if (event.target.id === 'prev-month') changeMonth(-1);
    });

    function changeMonth(offset) {
        lastDate.setMonth(lastDate.getMonth() + offset);
        renderCalendar(lastDate);
    }

    renderCalendar(lastDate);
    displayCurrentDate();

});