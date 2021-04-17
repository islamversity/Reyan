
import UIKit
import nativeShared
import Resolver
import SwiftyBeaver

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        
        setupLogger()
        whereIsMySQLite()
                
        return true
    }

    // MARK: UISceneSession Lifecycle

    func application(_ application: UIApplication, configurationForConnecting connectingSceneSession: UISceneSession, options: UIScene.ConnectionOptions) -> UISceneConfiguration {
        // Called when a new scene session is being created.
        // Use this method to select a configuration to create the new scene with.
        return UISceneConfiguration(name: "Default Configuration", sessionRole: connectingSceneSession.role)
    }

    func application(_ application: UIApplication, didDiscardSceneSessions sceneSessions: Set<UISceneSession>) {
        // Called when the user discards a scene session.
        // If any sessions were discarded while the application was not running, this will be called shortly after application:didFinishLaunchingWithOptions.
        // Use this method to release any resources that were specific to the discarded scenes, as they will not return.
    }

    func whereIsMySQLite() {
        let path = FileManager
            .default
            .urls(for: .applicationSupportDirectory, in: .userDomainMask)
            .last?
            .absoluteString
            .replacingOccurrences(of: "file://", with: "")
            .removingPercentEncoding

        print("sqlite address")
        print(path ?? "Not found")
    }
    
    func setupLogger(){
        let swiftLog = SwiftyBeaver.self
        let console = ConsoleDestination()  // log to Xcode Console
        let file = FileDestination()  // log to default swiftybeaver.log file

        // use custom format and set console output to short time, log level & message
        console.format = "$DHH:mm:ss$d $L $M"
        // or use this for JSON output: console.format = "$J"

        // add the destinations to SwiftyBeaver
        swiftLog.addDestination(console)
        swiftLog.addDestination(file)
        
        swiftLog.debug("to see live logs copy and paste command below: \n tail -f \(file.logFileURL!.path)")
        
        Printer().doInit(loggers: [IOSLogger(swiftLog: swiftLog)])
    }
   
}

