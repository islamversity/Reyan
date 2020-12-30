
import SwiftUI
import NavigationRouter
import nativeShared

struct SurahView: View {
    
    @ObservedObject public var flowCollector: SurahStateCollector = SurahStateCollector()
    var presenter : SurahPresenter
    var initialData : SurahLocalModel?

    init(presenter : SurahPresenter, initialData : SurahLocalModel?) {
        
        self.presenter = presenter
        self.initialData = initialData

        flowCollector.bindState(presenter: presenter)
        
        
    }
    
    
    var body: some View {
        
        
        
        Button(action: {
            
            if let lm = initialData {
                let _ = presenter.processIntents(intents: SurahIntent.Initial.init(localModel: lm))
            }
        })
        {
            Text("Surah View")
        }
    }
}
//
//struct SurahView_Previews: PreviewProvider {
//    static var previews: some View {
//        SurahView()
//    }
//}

