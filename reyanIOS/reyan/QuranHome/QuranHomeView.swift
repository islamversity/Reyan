
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
//        
        Timer.scheduledTimer(withTimeInterval: 0.3, repeats: false) { (t) in
            presenter.processIntents(intents: QuranHomeIntent.Initial.init())
        }

//        presenter.states().collect(collector: flowCollector, completionHandler: flowCollector.errorHandler(ku:error:))

//        let db : Main = resolver.resolve()
//        let surahList = db.nameQueries.getAllNames().executeAsList()
//        print("surahList count = \(surahList.count)")
    }
    
    var body: some View {
        
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
                            
                            Text("Type your search")
                                .foregroundColor(.gray)
                                .overlay(RoundedRectangle(cornerRadius: 10).stroke(Color.white, lineWidth: 0))
                                .padding(.vertical, 10.0)
                                .padding(.horizontal, 20.0)
                                .font(.custom("Vazir", size: 14))
                            
                            Spacer()
                            
                            Image.ic_search
                                .resizable()
                                .frame(width: 24, height: 24, alignment: .center)
                                .padding(.trailing, 20)
                        }
                    }
                }
                .padding(.top, 30)
                .cornerRadius(15.0)
                .fixedSize(horizontal: false, vertical: true)
                
                HStack{
                    Button(action: {
                        tabPosition = 0
//                        presenter.processIntents(intents: QuranHomeIntent.SelectTab.init(position: 0))
                    }, label: {
                        TabView(text: "Surah", isSelected: tabPosition == 0 ? true : false)
                    })
                    
                    Button(action: {
                        tabPosition = 1
//                        presenter.processIntents(intents: QuranHomeIntent.SelectTab.init(position: 1))
                    }, label: {
                        TabView(text: "Parts", isSelected: tabPosition == 1 ? true : false)
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
    }
}



//
//struct QuranView_Previews: PreviewProvider {
//    static var previews: some View {
//        QuranHomeView()
//    }
//}



//    var body: some View {
//
//        VStack(spacing: 30) {
//
//            Text("Success RUN")
//                .foregroundColor(.black)
//
//            Button(action: {
////                presenter.processIntents(intents: QuranHomeIntent.SearchClicked.init())
//
//                let db : Main = resolver.resolve()
////                db.calligraphyQueries.insertCalligraphy(id: "2397ry8274r3r-34r-34-r24r24r", languageCode: "en", name: "simple", friendlyName: "english text", code: Calligraphy__(languageCode: "en", calligraphyName: "simple"))
//                let calig = db.nameQueries.getAllNames().executeAsList()
//                let ayas = db.ayaContentQueries.getAllAyaContents().executeAsList()
//
//
//                print("calig = \(ayas.count)")
//            })
//            {
//                Text("send intent")
//                    .fontWeight(.bold)
//                    .foregroundColor(.white)
//                    .multilineTextAlignment(.center)
//                    .lineLimit(1)
//                    .padding(20.0)
//                    .font(.custom("Vazir", size: 20.0))
//                    .cornerRadius(10.0)
//                    .background(Color.black)
//            }
//
//            Text("state = \(String(describing: flowCollector.uiState))")
//                .foregroundColor(.black)
//
//        }
//        .navigationBarTitle("MVI")
//    }

