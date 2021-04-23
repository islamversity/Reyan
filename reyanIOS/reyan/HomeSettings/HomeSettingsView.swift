
import SwiftUI

struct HomeSettingsView: View {
        
    @State var language = currentLanguage
    
    var body: some View {
        
        VStack{
            
            Button(action: {
                Language.setLanguage(lang: .english)
                self.language = Constants.englishLang
            }, label: {
                Text("English")
            })
            
            Button(action: {
                Language.setLanguage(lang: .arabic)
                self.language = Constants.arabicLang
            }, label: {
                Text("Arabic")
            })
            
            Button(action: {
                Language.setLanguage(lang: .farsi)
                self.language = Constants.farsiLang
            }, label: {
                Text("Farsi")
            })
        }
        .navigationBarItems(
            trailing:
                Button(action: {
                    iOSNavigator.root?.setViewControllers([UIHostingController(rootView: LaunchView())], animated: true)
                }, label: {
                    Text("Home")
                })
        )
        .environment(\.locale, .init(identifier: language))

    }
}

struct HomeSettingsView_Previews: PreviewProvider {
    static var previews: some View {
        HomeSettingsView()
    }
}


