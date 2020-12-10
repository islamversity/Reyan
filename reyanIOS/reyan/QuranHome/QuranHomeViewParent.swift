
import SwiftUI
import NavigationRouter
import Resolver

/// Routable view model
struct QuranHomeViewParent: RoutableViewParent, Resolving{
    
//    lazy var mapper: Mapper = resolver.resolve(name : "ahmad_2")

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
        QuranHomeView(presenter: resolver.resolve())
            .eraseToAnyView()
    }
}
