

//  Created by meghdad on 11/29/20.

import SwiftUI
import nativeShared

struct SurahListView : View {
    
    
    @ObservedObject public var flowCollector: SurahListStateCollector = SurahListStateCollector()
    var presenter : SurahListPresenter
//    @State var uiState: SurahListState = SurahListState.Companion.idle(SurahListState.Companion.init())()
    
    
    init(presenter : SurahListPresenter) {
        
        self.presenter = presenter

//        presenter.consumeStates().collect(collector: flowCollector, completionHandler: flowCollector.errorHandler(ku:error:))

        flowCollector.bindState(presenter: presenter)
        
   
        
        UITableView.appearance().backgroundColor = UIColor.clear
        UITableViewCell.appearance().backgroundColor = UIColor.clear
    }
    
    var body: some View {
            
     
        let  _ =  presenter.processIntents(intents: SurahListIntent.Initial.init())

        ZStack {
            Color.clear
            
            VStack {
                
//                Button {
//                    presenter.processIntents(intents: SurahListIntent.Initial.init())
//                } label: {
//                    Text("get Surah list")
//                }

                List {
                    ForEach(flowCollector.uiState.surahList, id: \.self) { item in
                        
                        SurahRowView(
                            surahUIItem: item
                        ).padding(.horizontal)
                        
                    }
                    .listRow()
                    .listRowBackground(Color.clear)
                }
            }
        }
        .onAppear{
            presenter.processIntents(intents: SurahListIntent.Initial.init())
        }
    }
}



//
//
//    let surahItems : [SurahUIModel]//[SurahUIModel]
//
//    init(surahItems : [SurahUIModel]) {
//
//        self.surahItems = surahItems
//
//
////        if #available(iOS 14.0, *) {
////            // iOS 14 doesn't have extra separators below the list by default.
////        } else {
////            // To remove only extra separators below the list:
////            UITableView.appearance().tableFooterView = UIView()
////        }
//
//    }


//
//struct SurahListView_Preview : PreviewProvider {
//
//    static var previews: some View {
//        SurahListView(
//
//            //            surahItems: [
//            //            SurahUIModel(id: SurahID(id: "1") , order: 1, arabicName: "حمد", mainName: "Hamd", revealedType: RevealedType.medinan, ayaCount: 7),
//            //            SurahUIModel(id: SurahID(id: "2"), order: 2, arabicName: "حمد", mainName: "Hamd", revealedType: RevealedType.medinan, ayaCount: 256),
//            //            SurahUIModel(id: SurahID(id: "3"), order: 3, arabicName: "حمد", mainName: "Hamd", revealedType: RevealedType.medinan, ayaCount: 128)
//            //            ]
//            surahItems: ["1","2","3"]
//        )
//        .background(Color.gray.luminanceToAlpha())
//    }
//
//}


