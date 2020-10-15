//
//
//import Resolver
//
//
//// 1. Interface Injection
//
//class XYZViewModel {
//
//    lazy var fetcher: XYZFetching = getFetcher()
//    lazy var service: XYZService = getService()
//
//    func load() -> Data {
//        return fetcher.getData(service)
//    }
//
//}
//
//extension XYZViewModel: Resolving {
//    func getFetcher() -> XYZFetching { return resolver.resolve() }
//    func getService() -> XYZService { return resolver.resolve() }
//}
//
//func setupMyRegistrations {
//    register { XYZFetcher() as XYZFetching }
//    register { XYZService() }
//}
//
//// 2. Property Injection
//
//class XYZViewModel {
//
//    var fetcher: XYZFetching!
//    var service: XYZService!
//
//    func load() -> Data {
//        return fetcher.getData(service)
//    }
//
//}
//
//func setupMyRegistrations {
//    register { XYZViewModel() }
//        .resolveProperties { (resolver, model) in
//            model.fetcher = resolver.optional() // Note property is an ImplicitlyUnwrappedOptional
//            model.service = resolver.optional() // Ditto
//        }
//}
//
//
//func setupMyRegistrations {
//    register { XYZFetcher() as XYZFetching }
//    register { XYZService() }
//}
//
//// 3. Constructor Injection
//
//class XYZViewModel {
//
//    private var fetcher: XYZFetching
//    private var service: XYZService
//
//    init(fetcher: XYZFetching, service: XYZService) {
//        self.fetcher = fetcher
//        self.service = service
//    }
//
//    func load() -> Image {
//        let data = fetcher.getData(token)
//        return service.decompress(data)
//   }
//
//}
//
// //
//func setupMyRegistrations {
//    register { XYZViewModel(fetcher: resolve(), service: resolve()) }
//    register { XYZFetcher() as XYZFetching }
//    register { XYZService() }
//}
//
//
//func name(<#parameters#>) -> <#return type#> {
//    
//    register { SettingsProccessor(settingsRepo : resolve()) as MviProcessor<Intent  , Result> }
//    register{ SettingsRepoImpl() as SettingsRepo }
//    
//}
