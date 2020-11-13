
import Resolver

extension Resolver: ResolverRegistering {
    
    public static func registerAllServices() {
            
        // change default scope
        // Resolver.defaultScope = Resolver.unique

        // define  custom cached  scope
        // static let session = ResolverScopeCache()

        registerHome()
        
//        registerScreens() // presenter and proccessor
//        registerUseCases()
//        registerRepos()
//        registerDataSources()
//        registerQueries()
//        registerMappers() // Repo and UI Mappers
//        registerNativeDatabase()
        registerNavigator()
    }
}
