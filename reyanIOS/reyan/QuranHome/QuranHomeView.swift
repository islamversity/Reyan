
import SwiftUI
import NavigationRouter


struct QuranHomeView: RoutableView {
    
    var viewModel: QuranHomeViewModel
    @ObservedObject public var uc: QuranHomeUseCase
    
    init(viewModel: QuranHomeViewModel) {
        self.viewModel = viewModel
        self.uc = QuranHomeUseCase()
        
        uc.initialize()
    }
    
    var body: some View {
        
        VStack(spacing: 30) {
            
            Group { () -> AnyView in
                
                switch uc.uiState {
                    
                case .Loading(let message):
                    return AnyView(
                        Text(message)
                    )
                    
                case .SuccessfullyFetched:

                    return AnyView(
                        VStack(spacing: 30) {
                            Text("SuccessfullyFetched")
                            
                            Button(action: self.uc.goSearch) {
                                Text("Go Search")
                                    .fontWeight(.semibold)
                                    .font(.title)
                            }
                        }
                    )
                }
            }
            .navigationBarTitle("Navigation")
        }
    }
}

struct QuranView_Previews: PreviewProvider {
    static var previews: some View {
        QuranHomeView(viewModel: QuranHomeViewModel(parameters: nil))
    }
}

