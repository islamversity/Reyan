
import SwiftUI
import nativeShared
import Resolver

struct LaunchView: View, Resolving {
    
    init() {
        let savedLang = Language.getSavedLanguage()
        Language.setLanguage(lang: savedLang)
    }
    
    var body: some View {
        ZStack {
            Image.background_main
                .resizable()
                .edgesIgnoringSafeArea(.all)
        }.onAppear(perform: {
            checkDB()
        })
        
    }
    
    
    func checkDB(){
        let iOSDatabaseFiller : IOSDatabaseFiller = resolver.resolve()
        
        iOSDatabaseFiller.fillerUseCase.needsFilling(completionHandler: { (kbool, error) in
            
            if let isNotFilled = kbool {
                if isNotFilled as! Bool {
                    print("is not Filled")
                    
                    Timer.scheduledTimer(withTimeInterval: 0.3, repeats: false) { (t) in
                        iOSNavigator.goTo(screen: Screens.OnBoarding())
                    }
                    
                }else {
                    print("isFilled")
                    
                    Timer.scheduledTimer(withTimeInterval: 0.3, repeats: false) { (t) in
                        iOSNavigator.goTo(screen: Screens.Home())
                    }
                }
            }
        })
    }
}
