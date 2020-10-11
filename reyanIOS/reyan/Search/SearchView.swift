
import SwiftUI
import NavigationRouter

struct SearchView: RoutableView {
    
    var viewModel: SearchViewModel
        
    init(viewModel: SearchViewModel) {
           self.viewModel = viewModel
       }
    
    var body: some View {
        Text("Search View")
    }
}

struct SearchView_Previews: PreviewProvider {
    static var previews: some View {
        SearchView(viewModel: SearchViewModel(parameters: nil))
    }
}

