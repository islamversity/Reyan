
import SwiftUI
import NavigationRouter

/// RoutableViewParent
public protocol RoutableViewParent: Routable {
    /// Routed view
    @available(iOS 13.0, macOS 10.15, *)
    var routedView: AnyView { get }
}

/// RoutableViewParent
public extension RoutableViewParent {
    /// Routed view
    @available(iOS 13.0, macOS 10.15, *)
    var routedView: AnyView {
        EmptyView()
            .eraseToAnyView()
    }
    
    /// Routed view controller
    @available(iOS 13.0, macOS 10.15, *)
    var routedViewController: UIViewController {
        UIHostingController(rootView: self.routedView)
    }
}
