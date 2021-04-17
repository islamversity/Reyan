import Foundation
import nativeShared
import SwiftyBeaver


class IOSLogger : Logger {
    
    private let throwableStringProvider = PlatformThrowableStringProvider()
    
    private let swiftLog : SwiftyBeaver.Type
    
    init(swiftLog : SwiftyBeaver.Type) {
        self.swiftLog = swiftLog
    }
    
    override func log(severity: Severity_, message: String, tag: String, throwable: KotlinThrowable?) {
        
        let message = "\(tag): \(message)"
        
        if case Severity_.assert = severity {
            swiftLog.verbose(message)
        } else if case Severity_.debug = severity {
            swiftLog.debug(message)
        } else if case Severity_.warn = severity {
            swiftLog.warning(message)
        } else if case Severity_.error = severity {
            swiftLog.error(message)
        } else if case Severity_.info = severity {
            swiftLog.info(message)
        } else {
            swiftLog.verbose(message)
        }
        
        if let throwing = throwable {
            swiftLog.error(throwableStringProvider.getThrowableString(throwable: throwing))
        }
        
    }
    
}
