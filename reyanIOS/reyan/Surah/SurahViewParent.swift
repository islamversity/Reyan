
import SwiftUI
import NavigationRouter
import nativeShared
import Resolver

struct SurahViewParent: RoutableViewParent , Resolving{
    
    var initialData : SurahLocalModel?
    
    static var requiredParameters: [String]? {
        return [
            "initialmodel"
        ]
    }
    
    /// Navigation interception execution flow (if any)
    var navigationInterceptionExecutionFlow: NavigationInterceptionFlow?
    
    /// Initializes a new instance
    /// - Parameter parameters: Navigation parameters
    init(parameters: [String : String]?) {
        
        print("parameters = \(parameters)")
        
        if let params = parameters {
            if let initailDataJson = params["initialmodel"] {
                
                //                 = SurahLocalModel.Companion().fromData(data: initailDataJson)
                
                let jsonData = initailDataJson.data(using: .utf8)!
                let decoder = JSONDecoder()
                
                let im = try? decoder.decode(SurahInitialModel.self, from: jsonData)
                
                print("im = \(im)")
                
                if let sim = im {
                    initialData = SurahLocalModel.FullSurah.init(surahName: sim.name, surahID: sim.id, startingAyaOrder: sim.startAyaOrder)
                }
                
                print("initialData = \(initialData)")
            }
        }
        
    }
    
    // "{\"type\":\"fullsurah\",\"surahname\":\"الفاتحة\",\"surahid\":\"d65733fe-0253-47fc-9593-8a68db0cb5d0\",\"startingayaorder\":0}"
    
    /// View body
    var routedView: AnyView {
        SurahView(presenter: resolver.resolve(), initialData: initialData)
            .eraseToAnyView()
    }
}

struct SurahInitialModel : Codable {
    let type : String
    let id :  String
    let name :  String
    let startAyaOrder : Int64
    
    enum CodingKeys: String, CodingKey {
        case  type = "type"
        case id = "surahid"
        case name = "surahname"
        case startAyaOrder = "startingayaorder"
    }
}
