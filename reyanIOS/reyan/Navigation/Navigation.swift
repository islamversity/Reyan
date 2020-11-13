import UIKit
import SwiftUI
import NavigationRouter
import nativeShared


public let iOSNavigator : IOSNavigator = IOSNavigation()


public protocol IOSNavigator : Navigator{
    func loadRoutableModules()
    func getRouter() -> NavigationRouter
}

public class IOSNavigation : IOSNavigator{
   
    public func goTo(screen: Screens) {
        NavigationRouter.main.navigate(toPath: screen.url)
    }
    
    public func loadRoutableModules () {
        RoutableModulesFactory.loadRoutableModules()
    }
    
    public func getRouter() -> NavigationRouter {
        NavigationRouter.main
    }
}





