
import SwiftUI

//
//class UpdateUI: ObservableObject {
//    @Published var toUpdate = false
//}


struct HomeSettingsView: View {
    
//    @EnvironmentObject var language: LanguageSettings
//    @State var updating = false
    
    init() {}
    
//    func updateUI() {
//        updating = !updating
//    }
    
    @State var language = Language.getSavedLanguage()
    
    var body: some View {
        
        VStack{
//            Text("Home Settings : language = \(language.lang)")
            
            Button(action: {
                Language.setLanguage(lang: .english)
//                updateUI()
                self.language = "en"
                                
            }, label: {
//                Text(NSLocalizedString("English", comment: ""))
                Text("English")

            })
            
            Button(action: {
                Language.setLanguage(lang: .arabic)
//                updateUI()
                self.language = "ar"


            }, label: {
//                Text(NSLocalizedString("Arabic", comment: ""))
                Text("Arabic")

            })
            
            Button(action: {
                Language.setLanguage(lang: .farsi)
//                updateUI()
                self.language = "fa"

            }, label: {
                Text("Farsi")

//                Text( NSLocalizedString("Farsi", comment: ""))
            })
        }
        .environment(\.locale, .init(identifier: language))
        .navigationBarItems(
            trailing: Button("Home") {
                iOSNavigator.root?.setViewControllers([UIHostingController(rootView: LaunchView())], animated: true)
            }
        )
    }
}

struct HomeSettingsView_Previews: PreviewProvider {
    static var previews: some View {
        HomeSettingsView()
    }
}


