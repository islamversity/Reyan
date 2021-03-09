
import SwiftUI
import NavigationRouter
import nativeShared
import Resolver

struct SurahViewParent: RoutableViewParent , Resolving{
    
    var initialData : SurahLocalModel?
    
    static var requiredParameters: [String]? {
        return [
            SurahLocalModel.Companion().EXTRA_SURAH_DETAIL
        ]
    }
    
    var navigationInterceptionExecutionFlow: NavigationInterceptionFlow?
    
    init(parameters: [String : String]?) {
                
        if let params = parameters {
            if let initailDataJson = params[SurahLocalModel.Companion().EXTRA_SURAH_DETAIL] {
                
                let formatted = initailDataJson.uppercased().base16DecodedString()!
                print("after passing= \(formatted)")
  
                initialData = SurahLocalModel.Companion().fromData(data: formatted)
            }
        }
    }
  
    // "{\"type\":\"fullsurah\",\"surahname\":\"الفاتحة\",\"surahid\":\"d65733fe-0253-47fc-9593-8a68db0cb5d0\",\"startingayaorder\":0}"
    
    var routedView: AnyView {
        SurahView(presenter: resolver.resolve(), initialData: initialData!)
            .eraseToAnyView()
    }
}
