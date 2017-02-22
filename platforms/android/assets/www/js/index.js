
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