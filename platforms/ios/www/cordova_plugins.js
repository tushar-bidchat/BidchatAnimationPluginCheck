cordova.define('cordova/plugin_list', function(require, exports, module) {
module.exports = [
    {
        "id": "bidchat-plugin-animations.BidchatAnimations",
        "file": "plugins/bidchat-plugin-animations/www/BidchatAnimations.js",
        "pluginId": "bidchat-plugin-animations",
        "clobbers": [
            "cordova.plugins.BidchatAnimations"
        ]
    }
];
module.exports.metadata = 
// TOP OF METADATA
{
    "cordova-plugin-whitelist": "1.3.1",
    "cordova-plugin-cocoapod-support": "1.3.0",
    "bidchat-plugin-animations": "1.0.0"
};
// BOTTOM OF METADATA
});