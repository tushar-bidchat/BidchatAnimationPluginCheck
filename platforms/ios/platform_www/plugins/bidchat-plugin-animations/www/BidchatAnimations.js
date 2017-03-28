cordova.define("bidchat-plugin-animations.BidchatAnimations", function(require, exports, module) {
var BidchatAnimations = (function() {

    var BidchatAnimations = {};

    BidchatAnimations.showShareMenu = function(success, error, callbackMethod) {
        return cordova.exec(success, error, "BidchatAnimations", "showShareMenu", [callbackMethod]);
    };

    BidchatAnimations.showFlashNotification = function(success, error, textToSHow) {
        return cordova.exec(success, error, "BidchatAnimations", "showFlashNotification", [textToSHow]);
    };

    BidchatAnimations.startCountdownTimer = function(success, error, timerStartValue, callbackMethod) {
        return cordova.exec(success, error, "BidchatAnimations", "startCountdownTimer", [timerStartValue, callbackMethod]);
    };

    BidchatAnimations.stopCountdownTimer = function(success, error, timeExtendedMessage) {
        return cordova.exec(success, error, "BidchatAnimations", "stopCountdownTimer", [timeExtendedMessage]);
    };

    BidchatAnimations.likes = function(success, error, x, y, userId, userName, userImageUrl) {
        return cordova.exec(success, error, "BidchatAnimations", "likes", [x, y, userId, userName, userImageUrl]);
    };

    BidchatAnimations.lol = function(success, error,x, y, userId, userName, userImageUrl) {
        return cordova.exec(success, error, "BidchatAnimations", "lol", [x, y, userId, userName, userImageUrl]);
    };

    BidchatAnimations.marryMe = function(success, error,x, y, userId, userName, userImageUrl) {
        return cordova.exec(success, error, "BidchatAnimations", "marryMe", [x, y, userId, userName, userImageUrl]);
    };

    return BidchatAnimations;
});

module.exports = new BidchatAnimations();
});
