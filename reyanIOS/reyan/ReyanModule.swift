
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
            path: Screens.Home().name,
            type: QuranHomeViewModel.self,
            requiresAuthentication: false)
        
        let screenName = Screens.Search(model: SearchLocalModel(backTransitionName: "", textTransitionName: ""), pushAnimation: nil, popAnimation: nil).name
        print("registerRoutes : screenName = \(screenName)")
        let searchRoute: NavigationRoute = NavigationRoute(
            path: screenName,
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
