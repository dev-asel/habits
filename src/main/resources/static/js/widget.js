  document.addEventListener("DOMContentLoaded", function() {
    var observer = new MutationObserver(function(mutations) {
      mutations.forEach(function(mutation) {
          var widget = document.querySelector('.telegram-widget');
          if (widget) {
            var iframe = widget.firstElementChild;
            if (iframe && iframe.tagName.toLowerCase() === 'iframe') {
              var iframeDoc = iframe.contentDocument || iframe.contentWindow.document;
              var loginButton = iframeDoc.querySelector('button');
              if (loginButton) {
                loginButton.style.backgroundColor = '#10121e !important';
                loginButton.style.color = 'rgba(255, 255, 255, 0.8)';
                loginButton.style.borderRadius = '40px';
                observer.disconnect();
              }
            }
          }
      });
    });


    observer.observe(document.body, {
      childList: true,
      subtree: true
    });
  });