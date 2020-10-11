
import SwiftUI
import NavigationRouter

/// Routable view model
struct SearchViewModel: RoutableViewModel {
    // MARK: - Routing

    /// Required navigation parameters (if any)
    static var requiredParameters: [String]?

    /// Navigation interception execution flow (if any)
    var navigationInterceptionExecutionFlow: NavigationInterceptionFlow?

    /// Initializes a new instance
    /// - Parameter parameters: Navigation parameters
    init(parameters: [String : String]?) {
        // Do something with parameters (e.g. instantiating a model)
    }

    /// View body
    var routedView: AnyView {
        SearchView(viewModel: self)
            .eraseToAnyView()
    }
    
    
}
