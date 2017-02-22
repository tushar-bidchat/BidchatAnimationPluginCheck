/********* BidchatAnimations.m Cordova Plugin Implementation *******/

#import <Cordova/CDV.h>
#import "PopMenu.h"

@interface BidchatAnimations : CDVPlugin {

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
    [popMenu showMenuAtView:self.webView];
}

@end
