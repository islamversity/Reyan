
//  Created by meghdad on 12/10/20.


import SwiftUI
import nativeShared

struct JuzListView : View {
    
//    let items : [String]

    @ObservedObject public var flowCollector: JuzListStateCollector = JuzListStateCollector()
    var presenter : JuzListPresenter

    init(presenter : JuzListPresenter) {

        self.presenter = presenter
        presenter.states().collect(collector: flowCollector, completionHandler: flowCollector.errorHandler(ku:error:))

        // presenter.processIntents(intents: SurahListIntent.Initial.init())

        UITableView.appearance().backgroundColor = UIColor.clear
        UITableViewCell.appearance().backgroundColor = UIColor.clear
    }
    
    var body: some View {
            
        ZStack {
            Color.clear
            
            VStack {

                List {
                    ForEach(flowCollector.uiState.juzList, id: \.self) { item in
                        
                        JuzRowView(
                            juzUIItem: item
                        )
                            .padding(.horizontal)
                        
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
