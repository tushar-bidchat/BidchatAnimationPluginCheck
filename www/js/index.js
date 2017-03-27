var isCountdownTimerOn = false;

var app = {
    // Application Constructor
    initialize: function () {
        document.addEventListener('deviceready', this.onDeviceReady.bind(this), false);
    },

    // deviceready Event Handler
    //
    // Bind any cordova events here. Common events are:
    // 'pause', 'resume', etc.
    onDeviceReady: function () {
        this.receivedEvent('deviceready');
    },

    // Update DOM on a Received Event
    receivedEvent: function (id) {

        document.getElementById("buttonShare").addEventListener("click", function () {
            cordova.plugins.BidchatAnimations.showShareMenu(null, null, "SelectedShareButton");
        });

        document.getElementById("buttonFlashNotification").addEventListener("click", function () {
            cordova.plugins.BidchatAnimations.showFlashNotification(null, null, "Selected Share Button")
        });

        document.getElementById("buttonCountdown").addEventListener("click", function () {
            if (isCountdownTimerOn) {
                isCountdownTimerOn = false;
                cordova.plugins.BidchatAnimations.stopCountdownTimer(null, null, "Time Extended");
                document.getElementById("buttonCountdown").innerHTML = "Countdown Start";
            } else {
                isCountdownTimerOn = true;
                cordova.plugins.BidchatAnimations.startCountdownTimer(null, null, 10, "onCountdownComplete");
                document.getElementById("buttonCountdown").innerHTML = "Countdown Stop";
            }
        });

        document.getElementById("buttonLikes").addEventListener("click", function () {
            var frame = elementPosition(document.getElementById('buttonLikes'))
            var userId = "1";
            var userName = "Tushar";
            var userImageUrl = "https://lh3.googleusercontent.com/-Y86IN-vEObo/AAAAAAAAAAI/AAAAAAAKyAM/6bec6LqLXXA/s0-c-k-no-ns/photo.jpg";
            cordova.plugins.BidchatAnimations.likes(null, null, frame.x, frame.y, frame.toplocation, userId, userName, userImageUrl);
        });

        document.getElementById("buttonLol").addEventListener("click", function () {
            var frame = elementPosition(document.getElementById('buttonLol'))
            var userId = "1";
            var userName = "Tushar";
            var userImageUrl = "https://lh3.googleusercontent.com/-Y86IN-vEObo/AAAAAAAAAAI/AAAAAAAKyAM/6bec6LqLXXA/s0-c-k-no-ns/photo.jpg";
            cordova.plugins.BidchatAnimations.lol(null, null, frame.x, frame.y, frame.toplocation, userId, userName, userImageUrl);
        });

        document.getElementById("buttonMarryMe").addEventListener("click", function () {
            var frame = elementPosition(document.getElementById('buttonMarryMe'))
            var userId = "2";
            var userName = "Swarupa";
            var userImageUrl = "https://lh3.googleusercontent.com/-Y86IN-vEObo/AAAAAAAAAAI/AAAAAAAKyAM/6bec6LqLXXA/s0-c-k-no-ns/photo.jpg";
            cordova.plugins.BidchatAnimations.marryMe(null, null, frame.x, frame.y, frame.toplocation, userId, userName, userImageUrl);
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
    isCountdownTimerOn = false;
    document.getElementById("buttonCountdown").innerHTML = "Countdown Start";
}

/*
 ================================
 Get Element position in the Dom
 ================================
 */
elementPosition = function (el) {
    var xPos = 0;
    var yPos = 0;
    var viewPortHeight = $(window).height();
    var oneFourthHeight = viewPortHeight / 3;
    var toplocation = 0;

    while (el) {
        if (el.tagName == "BODY") {
            // deal with browser quirks with body/window/document and page scroll
            var xScroll = el.scrollLeft || document.documentElement.scrollLeft;
            var yScroll = el.scrollTop || document.documentElement.scrollTop;

            xPos += (el.offsetLeft - xScroll + el.clientLeft);
            yPos += (el.offsetTop - yScroll + el.clientTop);
        } else {
            // for all other non-BODY elements
            xPos += (el.offsetLeft - el.scrollLeft + el.clientLeft);
            yPos += (el.offsetTop - el.scrollTop + el.clientTop);
        }

        el = el.offsetParent;
    }

    if (yPos > oneFourthHeight)
        toplocation = yPos - oneFourthHeight;

    return {
        x: ""+xPos+"",
        y: ""+yPos+"",
        toplocation: ""+toplocation+""
    };
};
