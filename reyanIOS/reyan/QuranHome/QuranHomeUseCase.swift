
import Foundation
import nativeShared

public class QuranHomeUseCase : ObservableObject {
    
    @Published var uiState: QuranHomeState = .Loading("Loading")
    
    
    func initialize() {
        let timer = Timer.scheduledTimer(withTimeInterval: 2.0, repeats: false) { timer in
            print("Timer fired!")
            self.uiState = .SuccessfullyFetched
        }
    }
    
    func goSearch() {
        
        iOSNavigator.goTo(screen: Screens.Search (model: SearchLocalModel(backTransitionName: "", textTransitionName: ""), pushAnimation: nil, popAnimation: nil))
    }
}
