
import SwiftUI
import NavigationRouter
import nativeShared


struct QuranHomeView: View {
    
    @ObservedObject public var flowCollector: QuranHomeStateCollector = QuranHomeStateCollector()
    var presenter : QuranHomePresenter
    
    init(presenter : QuranHomePresenter) {
        
        self.presenter = presenter
        presenter.states().collect(collector: flowCollector, completionHandler: flowCollector.errorHandler(ku:error:))
        
        print("isLoading = \(String(describing: flowCollector.uiState?.base.showLoading))")
    }
    
    var body: some View {
        
        VStack(spacing: 30) {
            
            Text("Success RUN")
                .foregroundColor(.black)
                
            Button(action: {
                presenter.processIntents(intents: QuranHomeIntent.SearchClicked.init())
            })
            {
                Text("send intent")
                    .fontWeight(.bold)
                    .foregroundColor(.white)
                    .multilineTextAlignment(.center)
                    .lineLimit(1)
                    .padding(20.0)
                    .font(.custom("Vazir", size: 20.0))
                    .cornerRadius(10.0)
                    .background(Color.black)
            }

            Text("state = \(String(describing: flowCollector.uiState))")
                .foregroundColor(.black)
            
        }
        .navigationBarTitle("MVI")
    }
}
//
//struct QuranView_Previews: PreviewProvider {
//    static var previews: some View {
//        QuranHomeView()
//    }
//}
