
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
        
//        print("QuranHomeViewParent url = \(Screens.HomeCompanion().url)")
        let onBoardingRoute: NavigationRoute = NavigationRoute(
            path: Screens.OnBoarding().url,
            type: OnBoardingViewParent.self,
            requiresAuthentication: false)
        
        let quranHomeRoute: NavigationRoute = NavigationRoute(
            path: Screens.HomeCompanion().url,
            type: QuranHomeViewParent.self,
            requiresAuthentication: false)
        
        let searchRoute: NavigationRoute = NavigationRoute(
            path: Screens.SearchCompanion().url,
            type: SearchViewParent.self,
            requiresAuthentication: false)
        
        let homeSettingsRoute: NavigationRoute = NavigationRoute(
            path: Screens.SettingsCompanion().url,
            type: HomeSettingsViewParent.self,
            requiresAuthentication: false)
        
        let surahRoute: NavigationRoute = NavigationRoute(
            path: Screens.SurahCompanion().url + "/:\(SurahLocalModel.Companion().EXTRA_SURAH_DETAIL)",
            type: SurahViewParent.self,
            requiresAuthentication: false)

        
        // Register routes
        
        NavigationRouter.bind(routes: [onBoardingRoute, quranHomeRoute, searchRoute, homeSettingsRoute, surahRoute])

    }
}
