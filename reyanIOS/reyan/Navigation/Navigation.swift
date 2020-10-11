import UIKit
import SwiftUI
import NavigationRouter


public let navigator : Navigator = Navigation()


public protocol Navigator {
    func loadRoutableModules()
    func getRouter() -> NavigationRouter
    func goTo(screen: Screen)
}

public class Navigation : Navigator{
    
    public func loadRoutableModules () {
//        print("loadRoutableModules")
        RoutableModulesFactory.loadRoutableModules()
    }
    
    public func getRouter() -> NavigationRouter {
        NavigationRouter.main
    }
    
    public func goTo(screen: Screen) {
        
        NavigationRouter.main.navigate(toPath: screen.path.urlString)
    }
}





