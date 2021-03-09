
import SwiftUI
import nativeShared
import Resolver

struct LaunchView: View, Resolving {
        
    @State private var showingPaymentAlert = false
    
    init() {
        
        let iOSDatabaseFiller : IOSDatabaseFiller = resolver.resolve()
        
        iOSDatabaseFiller.fillerUseCase.needsFilling(completionHandler: { (kbool, error) in
            
            if let isNotFilled = kbool {
                if isNotFilled as! Bool {
                    print("is not Filled")
                    
//                        alert(isPresented: $showingPaymentAlert) {
//                            Alert(title: Text("Order confirmed"), message: Text("Your total was, thank you!"), dismissButton: .default(Text("OK")))
//                        }
                    
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
    
    var body: some View {
        ZStack {
            Image.background_main
                .resizable()
                .edgesIgnoringSafeArea(.all)
            
            Text("Welcome")
            
            

        }
        
    }
}
