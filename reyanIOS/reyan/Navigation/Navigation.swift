import UIKit
import SwiftUI
import NavigationRouter
import nativeShared
import Resolver



public let iOSNavigator : IOSNavigation = IOSNavigation()

public protocol IOSNavigator : Navigator, Resolving{
    func loadRoutableModules()
    func getRouter() -> NavigationRouter
}

public class IOSNavigation : IOSNavigator{
    
    public var root : UINavigationController? = nil
   
    public func goTo(screen: Screens) {
        Printer().log(message: "going to \(screen.url)")
        
        let extraData = screen.extras?.second as String?
//"{\"type\":\"fullSurah\",\"surahName\":\"البقرة\",\"surahID\":\"946431D8-92A2-4375-ABF3-E2D7EE27999A\",\"startingAyaOrder\":0}"
//        root!.pushViewController(UIHostingController(rootView: SurahView(presenter: resolver.resolve(), initialData: SurahLocalModel.FullSurah(surahName: "testing", surahID: "946431D8-92A2-4375-ABF3-E2D7EE27999A", startingAyaOrder: 0))), animated: true)
//        if screen is Screens.Home{
//            root!.pushViewController(Creator().create(), animated: true)
//        }else{
//        if extraData == nil {
//            NavigationRouter.main.navigate(toPath: screen.url)
//        }else{
//            let baseExtra = extraData!.base16EncodedString
////            print("path=\(screen.url + "/" + baseExtra)")
//            NavigationRouter.main.navigate(toPath: screen.url + "/" + baseExtra)
//        }
//        }
        
        if screen is Screens.Home {
//            root!.popViewController(animated: false)
            
            root!.pushViewController(UIHostingController(rootView: QuranHomeView(presenter: resolver.resolve())), animated: false)
            
        } else if screen is Screens.OnBoarding {
            
            root!.pushViewController(UIHostingController(
                                        rootView: OnBoardingView(
                                            presenter: resolver.resolve(),
                                            iOSDatabaseFiller: resolver.resolve())),
                                     animated: false)
        } else if let surahScreen = screen as? Screens.Surah {
            root!.pushViewController(UIHostingController(
                rootView: SurahView(presenter: resolver.resolve(), initialData: surahScreen.model)
            ), animated: true)
        } else if screen is Screens.Settings {
            root!.pushViewController(UIHostingController(
            rootView: HomeSettingsView()), animated: true)
        }
        
        
    }
    
    public func loadRoutableModules () {
        RoutableModulesFactory.loadRoutableModules()
    }
    
    public func getRouter() -> NavigationRouter {
        NavigationRouter.main
    }
    
//    private func createController(screen : Screens) -> UIHostingController<_> {
//        if case Screens.Home = screen {
//            return
//        }
//    }
}

//let encoder = JSONEncoder()
//       encoder.outputFormatting = .sortedKeys //.prettyPrinted
//       let payload: Data? = try? encoder.encode(initialModel)
//       path = ScreenURL(urlString: "/address/upsert/\(String(data: payload ?? Data(), encoding: .utf8)!)")
//       




