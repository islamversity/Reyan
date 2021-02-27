
//  Created by meghdad on 1/15/21.


import SwiftUI
import NavigationRouter
import Resolver

struct OnBoardingViewParent: RoutableViewParent, Resolving{
    
    static var requiredParameters: [String]?

    var navigationInterceptionExecutionFlow: NavigationInterceptionFlow?

    init(parameters: [String : String]?) {}

    var routedView: AnyView {
        OnBoardingView(presenter: resolver.resolve(), iOSDatabaseFiller : resolver.resolve())
            .eraseToAnyView()
    }
}
