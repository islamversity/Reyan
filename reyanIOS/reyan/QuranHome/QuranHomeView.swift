
import SwiftUI
import nativeShared
import Resolver

struct QuranHomeView: View, Resolving {
        
    @ObservedObject public var flowCollector: QuranHomeStateCollector = QuranHomeStateCollector()
    var presenter : QuranHomePresenter
    var surahListView : SurahListView
    var juzListView : JuzListView
    
    @State var tabPosition : Int = 0


    init(presenter : QuranHomePresenter) {
        
        self.presenter = presenter
        self.surahListView = SurahListView(presenter: Resolver.resolve())
        self.juzListView = JuzListView(presenter: Resolver.resolve())

        flowCollector.bindState(presenter: presenter)

        Timer.scheduledTimer(withTimeInterval: 0.3, repeats: false) { (t) in
            presenter.processIntents(intents: QuranHomeIntent.Initial.init())
        }
    }
    
    var body: some View {
        
        let searchTextView =
            Text(NSLocalizedString("Type_your_search", comment: ""))
                .foregroundColor(.gray)
                .overlay(RoundedRectangle(cornerRadius: 10).stroke(Color.white, lineWidth: 0))
                .padding(.vertical, 10.0)
                .padding(.horizontal, 20.0)
                .font(.custom("Vazir", size: 14))
        
        let searchIcon =
            Image.ic_search
                .resizable()
                .frame(width: 24, height: 24, alignment: .center)
        
        
        ZStack {
            
            Image.background_main
                .resizable()
                .edgesIgnoringSafeArea(.all)
            
            VStack {
                
                HStack {
                                        
                    Button(action: {
                        presenter.processIntents(intents: QuranHomeIntent.SettingsClicked.init())
                    })
                    {
                        Image.ic_settings
                            .resizable()
                            .frame(width: 24, height: 24, alignment: .center)
                    }
                    
                    Spacer()
                }
                .padding(.top, 20)
                .fixedSize(horizontal: false, vertical: true)
                
                ZStack {
                    Color.white
                    
                    Button(action: {
                        
                        presenter.processIntents(intents: QuranHomeIntent.SearchClicked.init())
                        
                        
                    })
                    {
                        HStack {
                            
                            if UIApplication.isRTL() {
                                searchIcon
                                    .padding(.leading, 20)
                                Spacer()
                                searchTextView
                            }else{
                                searchTextView
                                Spacer()
                                searchIcon
                                    .padding(.trailing, 20)
                            }
                        }
                    }
                }
                .padding(.top, 30)
                .cornerRadius(15.0)
                .fixedSize(horizontal: false, vertical: true)
                
                HStack{
                    Button(action: {
                        tabPosition = 0
                    }, label: {
                        TabView(text: NSLocalizedString("Surah", comment: ""), isSelected: tabPosition == 0 ? true : false)
                    })
                    
                    Button(action: {
                        tabPosition = 1
                    }, label: {
                        TabView(text: NSLocalizedString("Parts", comment: ""), isSelected: tabPosition == 1 ? true : false)
                    })
                }
                
                if tabPosition == 0 {
                    surahListView
                } else if tabPosition == 1 {
                    juzListView
                }
            }
            .padding(.horizontal, 20.0)
//            .edgesIgnoringSafeArea(.all)
        }
        .navigationBarHidden(true)
        .environment(\.locale, Locale(identifier:  currentLanguage.rawValue))

    }
}
