
protocol ScreenUrlType {
    var urlString : String { get set }
}

public class ScreenURL : ScreenUrlType {
    public var urlString: String = ""
    
    init(urlString : String) {
        self.urlString = urlString
    }
}
