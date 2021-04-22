import UIKit
import SwiftUI
import nativeShared
import Resolver

// global navigator
public let iOSNavigator : IOSNavigation = IOSNavigation()

// Navigator type
public protocol IOSNavigator : Navigator, Resolving{}

// Navigation impl
public class IOSNavigation : IOSNavigator{
    
    public var root : UINavigationController? = nil
   
    public func goTo(screen: Screens) {
        Printer().log(message: "going to \(screen.url)")
        
        let extraData = screen.extras?.second as String?
        
        if screen is Screens.Home {
            root?.pushViewController(
                UIHostingController(rootView: QuranHomeView(presenter: resolver.resolve())),
                animated: false
            )
        } else if screen is Screens.OnBoarding {
            root?.pushViewController(
                UIHostingController(rootView: OnBoardingView(presenter: resolver.resolve(),iOSDatabaseFiller: resolver.resolve())),
                animated: false
            )
        } else if let surahScreen = screen as? Screens.Surah {
            root?.pushViewController(
                UIHostingController(rootView: SurahView(presenter: resolver.resolve(), initialData: surahScreen.model)),
                animated: true
            )
        } else if screen is Screens.Settings {
            root?.setViewControllers([UIHostingController(rootView: HomeSettingsView())], animated: true) 
//            pushViewController(
//                UIHostingController(rootView: HomeSettingsView()),
//                animated: true
//            )
        }
    }
}
