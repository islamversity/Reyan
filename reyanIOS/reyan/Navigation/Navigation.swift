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
        
        let extraData = screen.extras?.second as String?
        
        print("extraData:\(String(describing: extraData))")
        
        if extraData == nil {
            NavigationRouter.main.navigate(toPath: screen.url)
        }else{
            let baseExtra = extraData!.base16EncodedString
            print("path=\(screen.url + "/" + baseExtra)")
            NavigationRouter.main.navigate(toPath: screen.url + "/" + baseExtra)
        }
    }
    
    public func loadRoutableModules () {
        RoutableModulesFactory.loadRoutableModules()
    }
    
    public func getRouter() -> NavigationRouter {
        NavigationRouter.main
    }
}

//let encoder = JSONEncoder()
//       encoder.outputFormatting = .sortedKeys //.prettyPrinted
//       let payload: Data? = try? encoder.encode(initialModel)
//       path = ScreenURL(urlString: "/address/upsert/\(String(data: payload ?? Data(), encoding: .utf8)!)")
//       




