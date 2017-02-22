var BidchatAnimations = (function() {

    var BidchatAnimations = {};

    BidchatAnimations.showShareMenu = function(success, error, callbackMethod) {
        return cordova.exec(success, error, "BidchatAnimations", "showShareMenu", [callbackMethod]);
    };

    return BidchatAnimations;
});

module.exports = new BidchatAnimations();