
import NavigationRouter

/// Test feature 1 module definition
public final class ReyanModule: RoutableModule {
    // MARK: - Initializers
    
    /// Initializes a new instance
    public init() {
        // Initialize instance here as needed
    }
    
    // MARK: - Routing
    
    /// Registers navigation routers
    public func registerRoutes() {
        
        // Define routes
                
        let quranHomeRoute: NavigationRoute = NavigationRoute(
            path: QuranHomeScreen().path.urlString,
            type: QuranHomeViewModel.self,
            requiresAuthentication: false)
        
        let searchRoute: NavigationRoute = NavigationRoute(
            path: SearchScreen().path.urlString,
            type: SearchViewModel.self,
            requiresAuthentication: false)
        
        
        // Register routes
        
        NavigationRouter.bind(routes: [quranHomeRoute, searchRoute])

    }
}
