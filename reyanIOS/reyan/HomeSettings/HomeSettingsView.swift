
import SwiftUI
import nativeShared
import Combine


struct HomeSettingsView: View {
    
    @ObservedObject public var flowCollector: SettingsStateCollector = SettingsStateCollector()
    var presenter : SettingsPresenter
    
    @State var language = currentLanguage
    @State private var showLanguages = false
    @State private var showSurahCalligraphies = false
    @State private var showFirstTranslationCalligraphies = false
    @State private var showSecondTranslationCalligraphies = false

    @ObservedObject var sliderIntent = SliderIntent()
    var cancellableSet: Set<AnyCancellable> = []
    
    init(presenter : SettingsPresenter) {
        
        self.presenter = presenter
        
        flowCollector.bindState(presenter: presenter)
        
        Timer.scheduledTimer(withTimeInterval: 0.3, repeats: false) { (t) in
            presenter.processIntents(intents: SettingsIntent.Initial.init())
        }
                
        sliderIntent.$mainTextSize
            .sink { mtfs in
                presenter.processIntents(intents: SettingsIntent.ChangeQuranFontSize.init(size: Int32(mtfs)))
            }
            .store(in: &cancellableSet)
        sliderIntent.$translateTextSize
            .sink { tfs in
                presenter.processIntents(intents: SettingsIntent.ChangeTranslateFontSize.init(size: Int32(tfs)))
            }
            .store(in: &cancellableSet)
    }
    
    
    var body: some View {
        
        let state = flowCollector.uiState
        
        let row_languages =
            VStack {
                SettingsSelectiveRow(title: NSLocalizedString("App Language", comment: ""), selectedOption: currentLanguage.fullText())
            }
            .actionSheet(isPresented: $showLanguages) {
                ActionSheet(
                    title:
                        Text(NSLocalizedString("Change Language", comment: ""))
                    ,
                    //                    message:
                    //                        Text("Select one"),
                    buttons: [
                        .default(Text(NSLocalizedString("English", comment: ""))) {
                            Language.setLanguage(lang: .english)
                            self.language = .english
//                            presenter.processIntents(intents: SettingsIntent.init())
                        },
                        .default(Text(NSLocalizedString("Arabic", comment: ""))) {
                            Language.setLanguage(lang: .arabic)
                            self.language = .arabic
//                            presenter.processIntents(intents: SettingsIntent.Initial.init())
                        },
                        .default(Text(NSLocalizedString("Farsi", comment: ""))) {
                            Language.setLanguage(lang: .farsi)
                            self.language = .farsi
//                            presenter.processIntents(intents: SettingsIntent.Initial.init())
                        },
                        
                        .cancel(Text(NSLocalizedString("Cancel", comment: "")))
                    ]
                )
            }
        
        let row_surah_name_calligraphies =
            VStack {
                SettingsSelectiveRow(title: NSLocalizedString("Surah Caligraphy", comment: ""), selectedOption: NSLocalizedString(state.selectedSecondSurahNameCalligraphy?.name ??  "", comment: "") )
            }
            .actionSheet(isPresented: $showSurahCalligraphies) {
                ActionSheet(
                    title:
                        Text(NSLocalizedString("Caligraphy of surah name", comment: ""))
                    ,
                    buttons:
                        flowCollector.surahCaligraphyButtonList
                )
            }
        
        let row_first_translation_calligraphy =
            VStack {
                SettingsSelectiveRow(title: NSLocalizedString("First Translation Caligraphy", comment: ""), selectedOption: state.selectedFirstTranslationCalligraphy?.name ??  "")
            }
            .actionSheet(isPresented: $showFirstTranslationCalligraphies) {
                ActionSheet(
                    title:
                        Text(NSLocalizedString("Caligraphy of first translation box on each Aya", comment: ""))
                    ,
                    buttons:
                        flowCollector.firstTranslationCaligraphyButtonList
                )
            }
        
        let row_second_translation_calligraphy =
            VStack {
                SettingsSelectiveRow(title: NSLocalizedString("Second Translation Caligraphy", comment: ""), selectedOption: state.selectedSecondTranslationCalligraphy?.name ??  "")
            }
            .actionSheet(isPresented: $showSecondTranslationCalligraphies) {
                ActionSheet(
                    title:
                        Text(NSLocalizedString("Caligraphy of second translation box on each Aya", comment: ""))
                    ,
                    buttons:
                        flowCollector.secondTranslationCaligraphyButtonList
                )
            }
        
//        print("quranTextFontSize=\(state.quranTextFontSize)")

        
        return ZStack {
            Image.background_main
                .resizable()
                .edgesIgnoringSafeArea(.all)
            
            ScrollView {
                
                VStack (alignment: .center){
                    
                    Button(action: {
                        self.showLanguages = true
                    }, label: {
                        row_languages
                            .padding(.top, 10)
                    })
                    
                    Button(action: {
                        self.showSurahCalligraphies = true
                    }, label: {
                        row_surah_name_calligraphies
                            .padding(.top, 10)
                    })
                    
                    Button(action: {
                        self.showFirstTranslationCalligraphies = true
                    }, label: {
                        row_first_translation_calligraphy
                            .padding(.top, 10)
                    })
                    
                    Button(action: {
                        self.showSecondTranslationCalligraphies = true
                    }, label: {
                        row_second_translation_calligraphy
                            .padding(.top, 10)
                    })
                    
                    SettingsSliderRow(
                        title: NSLocalizedString("Main Text Size", comment: "") ,
                        mainText: NSLocalizedString("In the name of Allah, The Beneficent, The Merciful", comment: ""),
                        minSize: Double(QuranReadFontSize.Companion.init().MAIN_AYA_MIN.size),
                        maxSize: Double(QuranReadFontSize.Companion.init().MAX.size),
                        selectedSize: Double(state.quranTextFontSize),
                        mainTextSize: $sliderIntent.mainTextSize
                    )
                    
                    SettingsSliderRow(
                        title: NSLocalizedString("Translate Text Size", comment: ""),
                        mainText: NSLocalizedString("In the name of Allah, The Beneficent, The Merciful", comment: ""),
                        minSize: Double(QuranReadFontSize.Companion.init().TRANSLATION_MIN.size),
                        maxSize: Double(QuranReadFontSize.Companion.init().MAX.size),
                        selectedSize: Double(state.translateTextFontSize),
                        mainTextSize: $sliderIntent.translateTextSize
                    )
                }
            }
        }
        .navigationBarTitle(NSLocalizedString("Settings", comment: ""))
        .navigationBarItems(
            leading:
                Button(action: {
                    iOSNavigator.root?.setViewControllers([UIHostingController(rootView: LaunchView())], animated: true)
                }, label: {
                    
                    //                    Image(systemName: "chevron.backward")
                    //                        .rotationEffect(.init(radians: UIApplication.isRTL() ? .pi : .zero))
                    
                    Text("Back")
                    
                })
            
        )
        .environment(\.locale, .init(identifier: language.rawValue))
        
    }
    
}

