# Bidchat Animations

## Overview
This Cordova plugin for iOS/Android native animations for Bidchat App.

### Example Usage

#### Share Menu

Shows a share menu

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
        
        cordova.plugins.BidchatAnimations.showShareMenu(null, null, "SelectedShareButton");

#### Countdown

Shows coutdown

        var onCountdownComplete = function () {
          console.log('onCountdownComplete Called');
          isCountdownTimerOn = false;
          document.getElementById("buttonCountdown").innerHTML = "Countdown Start";
        }
        
        var coutdownTime = 10;
        cordova.plugins.BidchatAnimations.startCountdownTimer(null, null, coutdownTime, "onCountdownComplete");

        var messageOnStopCoutdown = "Time Extended";
        cordova.plugins.BidchatAnimations.stopCountdownTimer(null, null, messageOnStopCoutdown);


#### Likes

Shows Heart flow animation for likes

        cordova.plugins.BidchatAnimations.likes(null, null)




