        Telegram.WebApp.ready();
        const initData = Telegram.WebApp.initData;
        const initDataUnsafe = Telegram.WebApp.initDataUnsafe;
        console.log(initDataUnsafe);

        if (initDataUnsafe?.user) {
            fetch('/auth/app/callback', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({init_data: initData})
            })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        window.location.href = data.redirect_url;
                        console.log('Authentication successful');
                    } else {
                        console.error('Authentication failed', data.error || 'Unknown error');
                    }
                })
                .catch(error => {
                    console.error('Error during authentication', error);
                });
        } else {
            console.error('Telegram.WebApp.initDataUnsafe is not available');
        }