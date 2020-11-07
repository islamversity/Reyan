//
//import Resolver
//
//final class TestDI {
//    func setup() {
//        Resolver.register {
//            QuranHomeViewModel()
//        }
//        .implements(CoffeesController.self)
//    }
//}
//


//public protocol CoffeesController: BusinessController {
//    // ...
//}
//class CoffeesControllerImplementation: CoffeesController {
//    // ...
//}
//final class CoffeesBusinessModule: BusinessModule {
//    func setup() {
//        Resolver.register {
//            CoffeesControllerImplementation()
//        }
//        .implements(CoffeesController.self)
//    }
//}
//
//class CoffeesListViewModel: RoutableViewModel, ObservableObject {
//    @LazyInjected private var businessController : CoffeesBusinessController
//    // ...
//}
