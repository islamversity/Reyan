
import Resolver

extension Resolver: ResolverRegistering {
    
    public static func registerAllServices() {
            
        // change default scope
        // Resolver.defaultScope = Resolver.unique

        // define  custom cached  scope
        // static let session = ResolverScopeCache()

        
        registerSettingsScreen()
        registerRepos()
        registerDataSources()
        registerQueries() 
        registerMappers()
        registerNativeDatabase()
        
    }
}
