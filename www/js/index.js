
var isCountdownTimerOn;

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
            cordova.plugins.BidchatAnimations.showShareMenu(null, null, "SelectedShareButton");
        });

        document.getElementById("buttonFlashNotification").addEventListener("click", function () {
            cordova.plugins.BidchatAnimations.showFlashNotification(null, null, "Selected Share Button")
        });

        document.getElementById("buttonCountdown").addEventListener("click", function () {
            if(isCountdownTimerOn) {
                isCountdownTimerOn = false;
                cordova.plugins.BidchatAnimations.stopCountdownTimer();
                document.getElementById("buttonCountdown").innerHTML("Countdown Start");
            }
            else {
                isCountdownTimerOn = true;
                cordova.plugins.BidchatAnimations.startCountdownTimer(null, null, 10, "onCountdownComplete");
                document.getElementById("buttonCountdown").innerHTML("Countdown Stop");
            }
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

var onCountdownComplete = function () {
    console.log('onCountdownComplete Called');
    document.getElementById("buttonCountdown").innerHTML("Countdown Start");
}