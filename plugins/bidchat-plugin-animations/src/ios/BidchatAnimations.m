/********* BidchatAnimations.m Cordova Plugin Implementation *******/

#import <Cordova/CDV.h>
#import "PopMenu.h"
#import <QuartzCore/QuartzCore.h>
#import "UIView+DCAnimationKit.h"
#import "BidchatiOSApp-Swift.h"

#define kTagtimeOverLabel  111111

// For Heart Animation
static CGFloat heartSize = 36;

#define kCoutdownFontSize 35
#define kCoutdownFontName @"Avenir-Heavy"
#define kPostCoutdownDelayDuration 1.5f
#define kAppColor [UIColor colorWithRed:255.0f/255.0f green:51.0f/255.0f blue:80.0f/255.0f alpha:1.0f]

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

#pragma mark Countdown Animation
- (void) startCountdownTimer :(CDVInvokedUrlCommand*)command {
    
    NSNumber *timerStartValue = [command.arguments objectAtIndex:0];
    countdownTimerCallback = [command.arguments objectAtIndex:1];
    
    countdownLabel = [[CountdownLabel alloc] initWithFrame:CGRectMake(40, 100, 200, 100) minutes: [timerStartValue intValue]];
    [countdownLabel setCountdownDelegate:self];
    [countdownLabel setBidchatAnimation];
    
    [self.viewController.view addSubview:countdownLabel];
    
    [self updateCoutdownView:countdownLabel];
    
    [countdownLabel start:nil];
}

- (void) updateCoutdownView:(UIView*) viewToUpdate {
    
    [(UILabel*)viewToUpdate setTextColor:kAppColor];
    //    [(UILabel*)viewToUpdate setBackgroundColor:[UIColor colorWithRed:255.0f/255.0f green:255.0f/255.0f blue:80.0f/255.0f alpha:1.0f]];
    [(UILabel*)viewToUpdate setTextAlignment:NSTextAlignmentCenter];
    [(UILabel*)viewToUpdate setFont:[UIFont fontWithName:kCoutdownFontName size: kCoutdownFontSize]];
    
    // Show in Center with Constraint
    viewToUpdate.translatesAutoresizingMaskIntoConstraints = NO;
    
    NSLayoutConstraint * constraintCenterHorizontal = [viewToUpdate.centerXAnchor constraintEqualToAnchor:self.viewController.view.centerXAnchor];
    NSLayoutConstraint * constraintCenterVertical = [viewToUpdate.centerYAnchor constraintEqualToAnchor:self.viewController.view.centerYAnchor];
    
    NSLayoutConstraint * constraintLeftMargin = [viewToUpdate.leftAnchor constraintEqualToAnchor:self.viewController.view.leftAnchor constant:16];
    NSLayoutConstraint * constraintRightMargin = [viewToUpdate.rightAnchor constraintEqualToAnchor:self.viewController.view.rightAnchor constant:-16];
    
    //    NSLayoutConstraint * constraintWidth = [viewToUpdate.widthAnchor constraintEqualToConstant:100];
    //    NSLayoutConstraint * constraintHeight = [viewToUpdate.heightAnchor constraintEqualToConstant:100];
    [NSLayoutConstraint activateConstraints:@[constraintCenterHorizontal, constraintCenterVertical, constraintLeftMargin, constraintRightMargin]];
    
}

- (void) stopCountdownTimer :(CDVInvokedUrlCommand*)command {
    
    if(countdownLabel != nil) {
        [countdownLabel cancel:nil];
    }
    
    NSString *timeExtendedMessage = [command.arguments objectAtIndex:0];
    if(timeExtendedMessage != nil) {
        
        timeOverLabel = [[LTMorphingLabel alloc] initWithFrame:CGRectMake(40, 100, 200, 100)];
        [timeOverLabel setText:timeExtendedMessage];
        [timeOverLabel setMorphingEffect:LTMorphingEffectSparkle];
        [timeOverLabel setDelegate:self];
        [timeOverLabel setTag:kTagtimeOverLabel];
        [timeOverLabel setMorphingDuration:kPostCoutdownDelayDuration];
        [self.viewController.view addSubview:timeOverLabel];
        
        [self updateCoutdownView:timeOverLabel];
    }
}

#pragma mark LTMorphingLabelDelegate Methods

- (void) countdownFinished {
    
    NSLog(@"countdownFinished");
    
    [countdownLabel removeFromSuperview];
    countdownLabel = nil;
    
    timeOverLabel = [[LTMorphingLabel alloc] initWithFrame:CGRectMake(40, 100, 200, 100)];
    [timeOverLabel setText:@"Time Over"];
    [timeOverLabel setMorphingEffect:LTMorphingEffectSparkle];
    [timeOverLabel setDelegate:self];
    [timeOverLabel setTag:kTagtimeOverLabel];
    [timeOverLabel setMorphingDuration:kPostCoutdownDelayDuration];
    [self.viewController.view addSubview:timeOverLabel];
    [self updateCoutdownView:timeOverLabel];
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
    NSString *x = [command.arguments objectAtIndex:0];
    NSString *y = [command.arguments objectAtIndex:1];

    NSString *userId = [command.arguments objectAtIndex:2];
    NSString *userName = [command.arguments objectAtIndex:3];
    NSString *userImageUrl = [command.arguments objectAtIndex:4];

    HeartView *heartview = [[HeartView alloc] initWithFrame:CGRectMake(0, 0, heartSize, heartSize)];
    [self.viewController.view addSubview:heartview];
    
    float fountX = heartSize/2.0+20;
    float fountY = self.viewController.view.bounds.size.height - heartSize/2.0 - 10;
    
    heartview.center =  CGPointMake(fountX,fountY);
    [heartview animateInView:self.viewController.view];
}

- (void) showLolAnimation:(CDVInvokedUrlCommand*)command {

    NSString *x = [command.arguments objectAtIndex:0];
    NSString *y = [command.arguments objectAtIndex:1];

    NSString *userId = [command.arguments objectAtIndex:2];
    NSString *userName = [command.arguments objectAtIndex:3];
    NSString *userImageUrl = [command.arguments objectAtIndex:4];

}

- (void) showMarryMeAnimation:(CDVInvokedUrlCommand*)command {

    NSString *x = [command.arguments objectAtIndex:0];
    NSString *y = [command.arguments objectAtIndex:1];

    NSString *userId = [command.arguments objectAtIndex:2];
    NSString *userName = [command.arguments objectAtIndex:3];
    NSString *userImageUrl = [command.arguments objectAtIndex:4];

}

@end
