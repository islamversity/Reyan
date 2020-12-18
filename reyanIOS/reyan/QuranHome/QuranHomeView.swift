
import SwiftUI
import nativeShared
import Resolver

struct QuranHomeView: View, Resolving {
    
    @ObservedObject public var flowCollector: QuranHomeStateCollector = QuranHomeStateCollector()
    var presenter : QuranHomePresenter
    var surahListView : SurahListView
    var juzListView : JuzListView

    init(presenter : QuranHomePresenter) {
        
        self.presenter = presenter
        self.surahListView = SurahListView(presenter: Resolver.resolve())
        self.juzListView = JuzListView(presenter: Resolver.resolve())

//        presenter.states().collect(collector: flowCollector, completionHandler: flowCollector.errorHandler(ku:error:))

//        let db : Main = resolver.resolve()
//        let surahList = db.nameQueries.getAllNames().executeAsList()
//        print("surahList count = \(surahList.count)")
        
    }
    
    var body: some View {
        ZStack {
            Image("background_main")
                .resizable()
                .edgesIgnoringSafeArea(.all)
            
            VStack {
                
                HStack(alignment: .top, spacing: 0.0) {
                    
                    Image("ic_settings")
                        .resizable()
                        .frame(width: 24, height: 24, alignment: .center)
                        .position(.init(x: 18, y: 18))
                }
                .padding(.top, 40)
                .fixedSize(horizontal: false, vertical: true)
                
                SearchBarView()
                    .padding(.top, 50)
                
                if flowCollector.uiState?.tabPosition == 0 {
                    surahListView
                } else if flowCollector.uiState?.tabPosition == 1 {
                    juzListView
                }
                
                Spacer()
            }
            .padding(.horizontal, 20.0)
            .edgesIgnoringSafeArea(.all)

            
        }
        .onAppear(){
//            surahListView.presenter.processIntents(intents: SurahListIntent.Initial.init())
        }
    }
    
    
    
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
    
    
}
//
//struct QuranView_Previews: PreviewProvider {
//    static var previews: some View {
//        QuranHomeView()
//    }
//}
