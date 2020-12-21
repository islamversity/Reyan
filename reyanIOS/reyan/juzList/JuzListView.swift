
//  Created by meghdad on 12/10/20.


import SwiftUI
import nativeShared

struct JuzListView : View {
        
    @ObservedObject public var flowCollector: JuzListStateCollector = JuzListStateCollector()
    var presenter : JuzListPresenter
    
    init(presenter : JuzListPresenter) {
        
        self.presenter = presenter
        
        flowCollector.bindState(presenter: presenter)
        
        UITableView.appearance().backgroundColor = UIColor.clear
        UITableViewCell.appearance().backgroundColor = UIColor.clear
    }
    
    var body: some View {
        
        ZStack {
            Color.clear
            
            VStack {
                
                List {
                    ForEach(flowCollector.uiState.juzList, id: \.self) { item in
                        
                        Button(action: {
                            presenter.processIntents(intents: JuzListIntent.ItemClick.init(action: JozRowActionModel(juz: item)))
                        }, label: {
                            JuzRowView(
                                juzUIItem: item
                            )
                            .padding(.horizontal)
                        })
                    }
                    .listRow()
                    .listRowBackground(Color.clear)
                }
            }
        }
    }
}
//
//struct JuzListView_Preview : PreviewProvider {
//    static var previews: some View {
//
//        JuzListView(items: [ "1" , "2"]

//        )
//    }
//}
