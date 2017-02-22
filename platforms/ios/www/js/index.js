
var app = {
    // Application Constructor
    initialize: function() {
        document.addEventListener('deviceready', this.onDeviceReady.bind(this), false);
    },

    // deviceready Event Handler
    //
    // Bind any cordova events here. Common events are:
    // 'pause', 'resume', etc.
    onDeviceReady: function() {
        this.receivedEvent('deviceready');
    },

    // Update DOM on a Received Event
    receivedEvent: function(id) {

        document.getElementById("buttonShare").addEventListener("click", function () {
            // TODO : Implement 
            cordova.plugins.BidchatAnimations.showShareMenu(null, null, "SelectedShareButton");
        });

    }
};

app.initialize();

var SelectedShareButton = function (buttonId) {
    switch (buttonId) {
        case 0:
            console.log("Facebook");
            break;
        case 1:
            console.log("Twitter");
            break;
        case 2:
            console.log("Instagram");
            break;
        case 3:
            console.log("Google+");
            break;
        case 4:
            console.log("Messages");
            break;
        case 5:
            console.log("Email");
            break;

        default:
            break;
    }
}