
import SwiftUI


class LanguageSettings: ObservableObject {
    @Published var lang = "en"
}

struct HomeSettingsView: View {
    
//    @EnvironmentObject var language: LanguageSettings
    @ObservedObject var languageSettings = LanguageSettings()
    
    init() {}
    
    var body: some View {
        
        VStack{
//            Text("Home Settings : language = \(language.lang)")
            
            Button(action: {
                defaultLocalizer.setSelectedLanguage(lang: "en")
                languageSettings.lang = "en"
                
            }, label: {
                Text(defaultLocalizer.stringForKey(key: "English"))
            })
            
            Button(action: {
                defaultLocalizer.setSelectedLanguage(lang: "ar")
                languageSettings.lang = "ar"

            }, label: {
                Text(defaultLocalizer.stringForKey(key: "Arabic"))
            })
            
            Button(action: {
                defaultLocalizer.setSelectedLanguage(lang: "fa")
                languageSettings.lang = "fa"

            }, label: {
                Text( defaultLocalizer.stringForKey(key: "Farsi"))
            })
        }
        .environmentObject(languageSettings)
        
    }
}

struct HomeSettingsView_Previews: PreviewProvider {
    static var previews: some View {
        HomeSettingsView()
    }
}


