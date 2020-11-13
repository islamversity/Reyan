
import Foundation
import nativeShared


class QuranHomeStateCollector : FlowCollector, ObservableObject {
   
    @Published var uiState: QuranHomeState?
    
    func emit(value: Any?, completionHandler: @escaping (KotlinUnit?, Error?) -> Void) {
        print("states : value = \(String(describing: value))")
        print("states : completionHandler = \(String(describing: completionHandler))")

        uiState = value as? QuranHomeState
    }
    
    func errorHandler(ku : KotlinUnit?, error : Error?) -> Void {
        
        print("states : KotlinUnit , ku = \(String(describing: ku))")
        print("states : error = \(String(describing: error))")
    }
}
