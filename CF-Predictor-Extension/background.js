chrome.runtime.onMessage.addListener(
  function(message, sender, sendResponse){
	  fetch(message.url)
		  .then(response => response.text())
	  	  .then(text => sendResponse(text))
		  .catch(error => console.log(error));
          return true;  // Will respond asynchronously.
  });
