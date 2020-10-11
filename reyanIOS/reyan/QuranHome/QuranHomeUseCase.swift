
import Foundation

public class QuranHomeUseCase : ObservableObject {
    
    @Published var uiState: QuranHomeState = .Loading("Loading")
    
    
    func initialize() {
        let timer = Timer.scheduledTimer(withTimeInterval: 2.0, repeats: false) { timer in
            print("Timer fired!")
            self.uiState = .SuccessfullyFetched
        }
    }
    
    func goSearch() {
        navigator.goTo(screen: SearchScreen())
    }
}
