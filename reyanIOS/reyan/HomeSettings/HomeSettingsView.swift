
import SwiftUI
import nativeShared

struct HomeSettingsView: View {
        
    @ObservedObject public var flowCollector: SettingsStateCollector = SettingsStateCollector()
    var presenter : SettingsPresenter

    init(presenter : SettingsPresenter) {
        
        self.presenter = presenter

        flowCollector.bindState(presenter: presenter)

        Timer.scheduledTimer(withTimeInterval: 0.3, repeats: false) { (t) in
            presenter.processIntents(intents: SettingsIntent.Initial.init())
        }
    }
    
    
    
    
    
    
    
    @State var language = currentLanguage
    
    var body: some View {
        
        let state = flowCollector.uiState

        VStack{
            
            Button(action: {
                Language.setLanguage(lang: .english)
                self.language = Constants.englishLang
                
                
//                presenter.processIntents(intents: SettingsIntent.Initial.)
//                state.base
//                state.quranTextFontSize
//                state.secondSurahNameCalligraphies
//                state.secondTranslationCalligraphies
//                state.selectedFirstTranslationCalligraphy
//                state.selectedSecondSurahNameCalligraphy
//                state.selectedSecondTranslationCalligraphy
//                state.translateTextFontSize

                
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
        .navigationBarTitle(NSLocalizedString("Settings", comment: ""))
        .navigationBarItems(
            trailing:
                Button(action: {
                    iOSNavigator.root?.setViewControllers([UIHostingController(rootView: LaunchView())], animated: true)
                }, label: {
                    Image(systemName: "chevron.backward")
                })
        )
        .environment(\.locale, .init(identifier: language))

    }
}
//
//struct HomeSettingsView_Previews: PreviewProvider {
//    static var previews: some View {
//        HomeSettingsView()
//    }
//}


