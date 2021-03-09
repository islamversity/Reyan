
import SwiftUI
import NavigationRouter
import nativeShared
import Combine

struct SurahView: View {
    
    
    init(presenter : SurahPresenter, initialData : SurahLocalModel) {
        
        self.presenter = presenter
        self.initialData = initialData
        
        flowCollector.bindState(presenter: presenter)
        
        rowIntents.$action
            .sink { action in
                switch action {
                case .None :
                    break
                case .ShareClick(let uiModel) :
                    presenter.processIntents(intents: SurahIntent.AyaActionsShare.init(aya: uiModel))
                }
            }
            .store(in: &cancellables)
        
        Timer.scheduledTimer(withTimeInterval: 0.1, repeats: false) { (t) in
            presenter.processIntents(intents: SurahIntent.Initial.init(localModel: initialData))
        }
    }
    
    var body: some View {
        
        let items = flowCollector.uiState.items
        
        return VStack {
//            Button(action: {
//                presenter
//                    .processIntents(
//                        intents: SurahIntent.Initial.init(localModel: initialData))
//            })
//            {
//                Text("init")
//            }
            
            ScrollView {
                
                VStack {
                    
                    ForEach(items, id: \.rowId) { item in
                        
                        if item is SurahHeaderUIModel {
                            
                            let vm = item as! SurahHeaderUIModel
                            
                            SurahHeaderView(
                                id: vm.rowId,
                                order: vm.number,
                                name: vm.nameTranslated,
                                originalName: vm.name,
                                origin: vm.origin.name,
                                verses: String(vm.verses),
                                showismillah: vm.showBismillah
                            )
                        }else if item is AyaUIModel {
                            
                            AyaRowView (uiModel: item as! AyaUIModel, rowIntents: rowIntents)
                            
                            Divider()
                                .padding(.horizontal, 20)
                        }
                    }
                }
                .fixedSize(horizontal: false, vertical: true)
            }
        }
        .navigationBarTitle("Holy Quran", displayMode: .inline)
       
//        .navigationBarTitle(Text().foregroundColor(.green_800))
        
    }
}


//
//struct SurahView_Previews: PreviewProvider {
//    static var previews: some View {
//        QuranView(presenter: <#T##SurahPresenter#>, initialData: <#T##SurahLocalModel#>)
//
//    }
//}

