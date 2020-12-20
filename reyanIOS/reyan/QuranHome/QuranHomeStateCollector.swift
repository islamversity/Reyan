
import Foundation
import nativeShared

class QuranHomeStateCollector : ObservableObject {
   
    @Published var uiState: QuranHomeState
    
    func bindState(presenter : QuranHomePresenter) {
        
        IntropExtensionsKt.consume(presenter) { viewState in
            print("QuranHomeStateCollector : states : value(as uiState) = \(String(describing: viewState))")
            
            self.uiState = viewState as! QuranHomeState
            
        }
    }
    
    func errorHandler(ku : KotlinUnit?, error : Error?) -> Void {
        
        print("QuranHomeStateCollector : KotlinUnit = \(String(describing: ku))")
        print("QuranHomeStateCollector : error = \(String(describing: error))")
    }
    
    init() {
        let idleState = QuranHomeState.Companion.idle(QuranHomeState.Companion.init())()
        uiState = idleState
    }
}
