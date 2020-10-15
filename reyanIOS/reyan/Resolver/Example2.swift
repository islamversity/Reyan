////
////  Example2.swift
////  reyan
////
////  Created by meghdad on 10/15/20.
////
//
//import Foundation
//import Resolver
//
//
//class XYZViewModel {
//    private var fetcher: XYZFetching
//    private var updater: XYZUpdating
//    private var service: XYZService
//
//    init(fetcher: XYZFetching, updater: XYZUpdating, service: XYZService) {
//        self.fetcher = fetcher
//        self.updater = updater
//        self.service = service
//    }
//    // Implmentation
//}
//
//class XYZCombinedService: XYZFetching, XYZUpdating {
//    private var session: XYZSessionService
//    init(_ session: XYZSessionService) {
//        self.session = session
//    }
//    // Implmentation
//}
//
//struct XYZService {
//    // Implmentation
//}
//
//class XYZSessionService {
//    // Implmentation
//}
//
//
////
////extension Resolver: ResolverRegistering {
////    public static func registerAllServices() {
////        register { XYZViewModel(fetcher: resolve(), updater: resolve(), service: resolve()) }
////        register { XYZCombinedService(resolve()) }
////            .implements(XYZFetching.self)
////            .implements(XYZUpdating.self)
////        register { XYZService() }
////        register { XYZSessionService() }
////    }
////}
//
//
///////// AT LAST ///////////////////
//class MyViewController: UIViewController, Resolving {
//    lazy var viewModel: XYZViewModel = resolver.resolve()
//    @LazyInjected var service: XYZNameService
//    @OptionalInjected var service: XYZService?
//
//}
