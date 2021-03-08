
import SwiftUI
import nativeShared
import Resolver

struct LaunchView: View, Resolving {
    
    init() {
        
        let iOSDatabaseFiller : IOSDatabaseFiller = resolver.resolve()
        
        iOSDatabaseFiller.fillerUseCase.needsFilling(completionHandler: { (kbool, error) in
            
            if let isNotFilled = kbool {
                if isNotFilled as! Bool {
                    print("is not Filled")

                    iOSNavigator.goTo(screen: Screens.OnBoarding())
                }else {
                    print("isFilled")
                    
                    iOSNavigator.goTo(screen: Screens.Home())
                }
            }
        })
    }
    
    var body: some View {
        ZStack {
            Image.background_main
                .resizable()
                .edgesIgnoringSafeArea(.all)
        }
    }
}
