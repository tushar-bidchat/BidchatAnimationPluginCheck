/********* BidchatAnimations.m Cordova Plugin Implementation *******/

#import <Cordova/CDV.h>
#import "PopMenu.h"
#import <QuartzCore/QuartzCore.h>
#import "UIView+DCAnimationKit.h"
#import "BidchatiOSApp-Swift.h"

#define kTagtimeOverLabel  111111

@interface BidchatAnimations : CDVPlugin <CountdownLabelDelegate, LTMorphingLabelDelegate> {
    
    NSMutableArray *springViews;
    
    CountdownLabel * countdownLabel;
    NSString * countdownTimerCallback;
    LTMorphingLabel *timeOverLabel;
}

@end

@implementation BidchatAnimations

// - (void)showYesNoAlert:(CDVInvokedUrlCommand*)command
// {
//     CDVPluginResult* pluginResult = nil;
//     NSString* echo = [command.arguments objectAtIndex:0];

//     if (echo != nil && [echo length] > 0) {
//         pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:echo];
//     } else {
//         pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR];
//     }

//     [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
// }

#pragma mark Countdown Animation
- (void) startCountdownTimer :(CDVInvokedUrlCommand*)command {
    
    NSNumber *timerStartValue = [command.arguments objectAtIndex:0];
    countdownTimerCallback = [command.arguments objectAtIndex:1];
    
    countdownLabel = [[CountdownLabel alloc] initWithFrame:CGRectMake(40, 100, 200, 100) minutes: [timerStartValue intValue]];
    [countdownLabel setTextColor:[UIColor orangeColor]];
    [countdownLabel setCountdownDelegate:self];
    
    [countdownLabel setFont:[UIFont fontWithName:@"Courier" size: 30]];//[UIFont labelFontSize]]];
    [countdownLabel setBidchatAnimation];
    
    [self.viewController.view addSubview:countdownLabel];
    [countdownLabel start:nil];
}

- (void) stopCountdownTimer :(CDVInvokedUrlCommand*)command {
    
    if(countdownLabel != nil) {
        [countdownLabel cancel:nil];
    }
    
    NSString *timeExtendedMessage = [command.arguments objectAtIndex:0];
    if(timeExtendedMessage != nil) {
    
        timeOverLabel = [[LTMorphingLabel alloc] initWithFrame:CGRectMake(40, 100, 200, 100)];
        [timeOverLabel setText:timeExtendedMessage];
        [timeOverLabel setTextColor:[UIColor orangeColor]];
        [timeOverLabel setFont:[UIFont fontWithName:@"Courier" size: 30]];//[UIFont labelFontSize]]];
        [timeOverLabel setMorphingEffect:LTMorphingEffectSparkle];
        [timeOverLabel setDelegate:self];
        [timeOverLabel setTag:kTagtimeOverLabel];
        [self.viewController.view addSubview:timeOverLabel];
    }
}

/**
 * Displays pop menu
 **/

- (void) showShareMenu :(CDVInvokedUrlCommand*)command {
    
    NSString* callback = [command.arguments objectAtIndex:0];
    
    NSMutableArray *items = [[NSMutableArray alloc] initWithCapacity:3];
    
    MenuItem * menuItem = [[MenuItem alloc] initWithTitle:@"Facebook"
                                                 iconName:@"facebook"
                                                glowColor:[UIColor clearColor]
                                                    index:0];
    [items addObject:menuItem];
    
    menuItem = [[MenuItem alloc] initWithTitle:@"Twitter"
                                      iconName:@"twitter"
                                     glowColor:[UIColor clearColor]
                                         index:1];
    [items addObject:menuItem];
    
    menuItem = [[MenuItem alloc] initWithTitle:@"Instagram"
                                      iconName:@"instagram"
                                     glowColor:[UIColor clearColor]
                                         index:2];
    [items addObject:menuItem];
    
    menuItem = [[MenuItem alloc] initWithTitle:@"Google+"
                                      iconName:@"google-plus"
                                     glowColor:[UIColor clearColor]
                                         index:3];
    [items addObject:menuItem];
    
    menuItem = [[MenuItem alloc] initWithTitle:@"Messages"
                                      iconName:@"messages"
                                     glowColor:[UIColor clearColor]
                                         index:4];
    [items addObject:menuItem];
    
    menuItem = [[MenuItem alloc] initWithTitle:@"Email"
                                      iconName:@"email"
                                     glowColor:[UIColor clearColor]
                                         index:5];
    [items addObject:menuItem];
    
    PopMenu *popMenu = [[PopMenu alloc] initWithFrame:self.viewController.view.bounds items:items];
    
    popMenu.menuAnimationType = kPopMenuAnimationTypeNetEase;
    popMenu.perRowItemCount = 3;
    popMenu.didSelectedItemCompletion = ^(MenuItem *selectedItem) {
        
        NSString* jsMethod = [NSString stringWithFormat:@"%@(%ld);", callback, (long)selectedItem.index];
        [self.webViewEngine evaluateJavaScript:jsMethod completionHandler:^(id identifier, NSError *error) {}];
    };
    [popMenu showMenuAtView:self.viewController.view];
}

#pragma mark Flash Animation

- (void) showFlashNotification:(CDVInvokedUrlCommand*)command {
    
    NSString* textToShow = [command.arguments objectAtIndex:0];
    
    if(springViews == nil) {
        springViews = [[NSMutableArray alloc] init];
    }
    
    // Random Variable Height is used
    int y = arc4random_uniform((u_int32_t) CGRectGetHeight(self.viewController.view.frame)) - 20;
    
    UILabel *moveView = [[UILabel alloc] initWithFrame:CGRectMake(40, y, 200, 100)];
    [moveView setText:textToShow];
    [moveView setTextAlignment:NSTextAlignmentCenter];
    
    moveView.backgroundColor = [UIColor colorWithHue:drand48() saturation:1.0 brightness:1.0 alpha:1.0];
    [self.viewController.view addSubview:moveView];
    
    [moveView bounceIntoView:self.viewController.view direction:DCAnimationDirectionLeft];
    
    [NSTimer scheduledTimerWithTimeInterval:5
                                     target:self
                                   selector:@selector(hideFlashNotification:)
                                   userInfo:nil
                                    repeats:NO];
    
    [springViews cdv_enqueue:moveView];
}

- (IBAction)hideFlashNotification:(id)sender {
    
    UIView *moveView = [springViews cdv_dequeue];
    
    [UIView animateWithDuration:0.5
                          delay:0.0
                        options:UIViewAnimationOptionTransitionCrossDissolve
                     animations:^{
                         // Drop the view
                         [moveView drop:^{
                             // Remove on done
                             [moveView removeFromSuperview];
                         }];
                     }
                     completion:NULL];
}


#pragma mark LTMorphingLabelDelegate Methods

- (void) countdownFinished {
    
    NSLog(@"countdownFinished");
    
    [countdownLabel removeFromSuperview];
    countdownLabel = nil;
    
    timeOverLabel = [[LTMorphingLabel alloc] initWithFrame:CGRectMake(40, 100, 200, 100)];
    [timeOverLabel setText:@"Time Over"];
    [timeOverLabel setTextColor:[UIColor orangeColor]];
    [timeOverLabel setFont:[UIFont fontWithName:@"Courier" size: 30]];//[UIFont labelFontSize]]];
    [timeOverLabel setMorphingEffect:LTMorphingEffectSparkle];
    [timeOverLabel setDelegate:self];
    [timeOverLabel setTag:kTagtimeOverLabel];
    [self.viewController.view addSubview:timeOverLabel];
}

- (void) countdownCancelled {
    
    [countdownLabel removeFromSuperview];
    countdownLabel = nil;
}

-(void) morphingDidComplete:(LTMorphingLabel *)label {
    
    if(label.tag == kTagtimeOverLabel) {
        
        [timeOverLabel removeFromSuperview];
        timeOverLabel = nil;
        
        // callback
        NSString* jsMethod = [NSString stringWithFormat:@"%@();", countdownTimerCallback];
        [self.webViewEngine evaluateJavaScript:jsMethod completionHandler:^(id identifier, NSError *error) {}];
    }
}

- (void) likes:(CDVInvokedUrlCommand*)command {

}

@end
