
import NavigationRouter
import nativeShared

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
            path: Screens.HomeCompanion().url,
            type: QuranHomeViewModel.self,
            requiresAuthentication: false)
        
        let searchRoute: NavigationRoute = NavigationRoute(
            path: Screens.SearchCompanion().url,
            type: SearchViewModel.self,
            requiresAuthentication: false)
        
//        let surahRoute: NavigationRoute = NavigationRoute(
//            path: Screens.Surah(model: SurahLocalModel(surahID: "", surahName: "", startingAyaOrder: 0), pushAnimation: nil, popAnimation: nil).name,
//            type: SurahViewModel.self,
//            requiresAuthentication: false)
//
        
        // Register routes
        
        NavigationRouter.bind(routes: [quranHomeRoute, searchRoute])

    }
}
