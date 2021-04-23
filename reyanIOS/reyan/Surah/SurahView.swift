
import SwiftUI
import nativeShared
import Combine

struct SurahView: View {
    
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
                case .ShareClick(let uiModel) :
                    presenter.processIntents(intents: SurahIntent.AyaActionsShare.init(aya: uiModel))
                }
            }
            .store(in: &cancellables)
        
        Timer.scheduledTimer(withTimeInterval: 0.3, repeats: false) { (t) in
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
                                order: Int(vm.number) ?? 0,
                                name: vm.nameTranslated,
                                originalName: vm.name,
                                origin: vm.origin.name,
                                verses: vm.verses,
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
        .navigationBarTitle(NSLocalizedString("Holy_Quran", comment: ""))
        .navigationBarBackButtonHidden(true)
        .navigationBarItems(
            trailing:
                Button(action: {
                    iOSNavigator.root?.popViewController(animated: true)
                }, label: {
                    Image(systemName: "chevron.backward")
                })
        )
        .environment(\.locale, Locale(identifier:  currentLanguage))

        
    }
}


//
//struct SurahView_Previews: PreviewProvider {
//    static var previews: some View {
//        QuranView(presenter: <#T##SurahPresenter#>, initialData: <#T##SurahLocalModel#>)
//
//    }
//}

