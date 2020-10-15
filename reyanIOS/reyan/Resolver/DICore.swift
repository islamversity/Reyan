
import Resolver
import reyan

extension Resolver: ResolverRegistering {
    public static func registerAllServices() {
        
        // change default scope
//        Resolver.defaultScope = Resolver.unique

        
        // define  custom cached  scope
//        static let session = ResolverScopeCache()

        registerNetworkServices()
    }
}
