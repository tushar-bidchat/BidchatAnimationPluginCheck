var BidchatAnimations = (function() {

    var BidchatAnimations = {};

    BidchatAnimations.showShareMenu = function(success, error, callbackMethod) {
        return cordova.exec(success, error, "BidchatAnimations", "showShareMenu", [callbackMethod]);
    };

    BidchatAnimations.showFlashNotification = function(success, error, textToSHow) {
        return cordova.exec(success, error, "BidchatAnimations", "showFlashNotification", [textToSHow]);
    };

    BidchatAnimations.showCountdownTimer = function(success, error, timerStartValue, callbackMethod) {
        return cordova.exec(success, error, "BidchatAnimations", "showCountdownTimer", [timerStartValue, callbackMethod]);
    };

    return BidchatAnimations;
});

module.exports = new BidchatAnimations();