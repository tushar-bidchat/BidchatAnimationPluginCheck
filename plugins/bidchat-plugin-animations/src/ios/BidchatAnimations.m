/********* BidchatAnimations.m Cordova Plugin Implementation *******/

#import <Cordova/CDV.h>
#import "PopMenu.h"
#import <QuartzCore/QuartzCore.h>
#import "UIView+DCAnimationKit.h"


@interface NSMutableArray (QueueAdditions)
- (id) dequeue;
- (void) enqueue:(id)obj;
- (id) top;
@end

@implementation NSMutableArray (QueueAdditions)
- (id) dequeue {
    id headObject = [self objectAtIndex:0];
    if (headObject != nil) {
        [self removeObjectAtIndex:0];
    }
    return headObject;
}

- (id) top {
    return [self objectAtIndex:0];
}

// Add to the tail of the queue (no one likes it when people cut in line!)
- (void) enqueue:(id)anObject {
    [self addObject:anObject];
}
@end

@interface BidchatAnimations : CDVPlugin {

    NSMutableArray *springViews;
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

- (void) showCountdownTimer :(CDVInvokedUrlCommand*)command {

    int timerStartValue = [command.arguments objectAtIndex:0];
    NSString* callback = [command.arguments objectAtIndex:1];

    //TODO: Implement Countdown Timer

    // callback
    // NSString* jsMethod = [NSString stringWithFormat:@"%@(%ld);", callback, (long)selectedItem.index];
    [self.webViewEngine evaluateJavaScript:callback completionHandler:^(id identifier, NSError *error) {}];

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


- (void) showFlashNotification:(CDVInvokedUrlCommand*)command {
    
    NSString* textToShow = [command.arguments objectAtIndex:0];
    
    if(springViews == nil) {
        springViews = [[NSMutableArray alloc] init];
    }
//    int x = arc4random_uniform((u_int32_t) CGRectGetWidth(self.mainViewController.view.frame)); // Random x
    int y = arc4random_uniform((u_int32_t) CGRectGetHeight(self.viewController.view.frame)); // Random y
    
    UIView *moveView = [[UIView alloc] initWithFrame:CGRectMake(40, y, 200, 100)];
    
    moveView.backgroundColor = [UIColor colorWithHue:drand48() saturation:1.0 brightness:1.0 alpha:1.0];
    [self.viewController.view addSubview:moveView];

    [moveView bounceIntoView:self.viewController.view direction:DCAnimationDirectionLeft];
    
    [NSTimer scheduledTimerWithTimeInterval:5
                                     target:self
                                   selector:@selector(hideFlashNotification:)
                                   userInfo:nil
                                    repeats:NO];
    
    [springViews enqueue:moveView];
}

- (IBAction)hideFlashNotification:(id)sender {
    
    UIView *moveView = [springViews dequeue];
    
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

@end
