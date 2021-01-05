
import SwiftUI
import NavigationRouter
import nativeShared
import Combine

struct QuranView: View {
    
    @ObservedObject public var flowCollector: SurahStateCollector = SurahStateCollector()
    
    var presenter : SurahPresenter
    var initialData : SurahLocalModel
    let rowIntents = AyaRowIntents()
    var cancellables = Set<AnyCancellable>()
    
    init(presenter : SurahPresenter, initialData : SurahLocalModel) {
        
        self.presenter = presenter
        self.initialData = initialData
        
        flowCollector.bindState(presenter: presenter)
        
        rowIntents.$action
            .sink { action in
                switch action {
                case .None :
                    break
                case .BookmarkClick(let uiModel) :
                    presenter.processIntents(intents: SurahIntent.AyaActionsShare.init(aya: uiModel))
                }
            }
            .store(in: &cancellables)
    }
    
    var body: some View {
        
        presenter.processIntents(intents: SurahIntent.Initial.init(localModel: initialData))
        let items = flowCollector.uiState.items
        
        return VStack {
            Button(action: {
                presenter
                    .processIntents(
                        intents: SurahIntent.Initial.init(localModel: initialData))
            })
            {
                Text("init")
            }
            
            
            
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
                                origin: vm.origin,
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
//        .onAppear(perform: {
//            presenter.processIntents(intents: SurahIntent.Initial.init(localModel: initialData))
//        })
    }
}


//
//struct SurahView_Previews: PreviewProvider {
//    static var previews: some View {
//        QuranView(presenter: <#T##SurahPresenter#>, initialData: <#T##SurahLocalModel#>)
//
//    }
//}

