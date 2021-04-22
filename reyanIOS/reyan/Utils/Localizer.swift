
import UIKit
import Foundation
import SwiftUI
//let defaultLocalizer = LocalizeUtils.defaultLocalizer
//
//class LocalizeUtils: NSObject {
//
//    static let defaultLocalizer = LocalizeUtils()
//    var appbundle = Bundle.main
//
//    func setSelectedLanguage(lang: String) {
//        guard let langPath = Bundle.main.path(forResource: lang, ofType: "lproj") else {
//            appbundle = Bundle.main
//            return
//        }
//        appbundle = Bundle(path: langPath)!
//    }
//
//    func stringForKey(key: String) -> String {
//        return appbundle.localizedString(forKey: key, value: "", table: nil)
//    }
//}

class Localizer: NSObject {
    
    class func DoTheSwizzling() {
        
        print("Swizzle : DoTheSwizzling  = \(Language.currentLanguage)")

        
        MethodSwizzleGivenClassName(cls: Bundle.self, originalSelector: #selector(Bundle.localizedString(forKey:value:table:)), overrideSelector: #selector(Bundle.specialLocalizedStringForKey(_:value:table:)))
        
//        MethodSwizzleGivenClassName(cls: UIApplication.self, originalSelector: #selector(getter: UIApplication.userInterfaceLayoutDirection), overrideSelector: #selector(getter: UIApplication.cstm_userInterfaceLayoutDirection))
//        
//        MethodSwizzleGivenClassName(cls: UITextField.self, originalSelector: #selector(UITextField.layoutSubviews), overrideSelector: #selector(UITextField.cstmlayoutSubviews))
//        
//        MethodSwizzleGivenClassName(cls: UILabel.self, originalSelector: #selector(UILabel.layoutSubviews), overrideSelector: #selector(UILabel.cstmlayoutSubviews))
//
//        MethodSwizzleGivenClassName(cls: UITextView.self, originalSelector: #selector(UITextView.layoutSubviews), overrideSelector: #selector(UITextView.cstmlayoutSubviews))
//        
//        MethodSwizzleGivenClassName(cls: UIButton.self, originalSelector: #selector(UIButton.layoutSubviews), overrideSelector: #selector(UIButton.cstmlayoutSubviews))
    }
}

/// Exchange the implementation of two methods of the same Class
func MethodSwizzleGivenClassName(cls: AnyClass, originalSelector: Selector, overrideSelector: Selector) {
    
    let origMethod: Method = class_getInstanceMethod(cls, originalSelector)!
    let overrideMethod: Method = class_getInstanceMethod(cls, overrideSelector)!
    
    if (class_addMethod(cls, originalSelector, method_getImplementation(overrideMethod), method_getTypeEncoding(overrideMethod))) {
        class_replaceMethod(cls, overrideSelector, method_getImplementation(origMethod), method_getTypeEncoding(origMethod));
    } else {
        method_exchangeImplementations(origMethod, overrideMethod);
    }
}


extension Bundle {
    
    @objc func specialLocalizedStringForKey(_ key: String, value: String?, table tableName: String?) -> String {
        
        if self == Bundle.main {
            
            var bundle = Bundle()
            
            print("Swizzle Bundle : key = \(key) value = \(String(describing: value)) ,currentLanguage = \(Language.currentLanguage)")
            
            if let _path = Bundle.main.path(forResource: Language.currentLanguage, ofType: "lproj") {
                bundle = Bundle(path: _path)!
            }else {
                let _path = Bundle.main.path(forResource: "Base", ofType: "lproj")!
                bundle = Bundle(path: _path)!
            }
            return (bundle.specialLocalizedStringForKey(key, value: value, table: tableName))
        }else {
            
            return (self.specialLocalizedStringForKey(key, value: value, table: tableName))
        }
    }
}

extension UIApplication {
    @objc var cstm_userInterfaceLayoutDirection : UIUserInterfaceLayoutDirection {
        get {
            var direction = UIUserInterfaceLayoutDirection.leftToRight
            let current = Language.currentLanguage
            if  current == languages.farsi.rawValue || current == languages.arabic.rawValue {
                direction = .rightToLeft
            }
            return direction
        }
    }
}


extension UILabel {
    @objc public func cstmlayoutSubviews() {
        self.cstmlayoutSubviews()
        if self.isKind(of: NSClassFromString("UITextFieldLabel")!) {
            return // handle special case with uitextfields
        }
        if self.tag <= 0  {
            if UIApplication.isRTL()  {
                if self.textAlignment == .right {
                    return
                }
            } else {
                if self.textAlignment == .left {
                    return
                }
            }
        }
        if self.tag <= 0 {
            if UIApplication.isRTL()  {
                self.textAlignment = .right
            } else {
                self.textAlignment = .left
            }
        }
    }
}

extension UITextField {
    @objc public func cstmlayoutSubviews() {
        self.cstmlayoutSubviews()
        if self.tag <= 0 {
            if UIApplication.isRTL()  {
                if self.textAlignment == .right { return }
                self.textAlignment = .right
            } else {
                if self.textAlignment == .left { return }
                self.textAlignment = .left
            }
        }
    }
}

extension UITextView {
    @objc public func cstmlayoutSubviews() {
        self.cstmlayoutSubviews()
        if self.tag <= 0 {
            if UIApplication.isRTL()  {
                if self.textAlignment == .right { return }
                self.textAlignment = .right
            } else {
                if self.textAlignment == .left { return }
                self.textAlignment = .left
            }
        }
    }
}
extension UIButton {
    @objc public func cstmlayoutSubviews() {
        self.cstmlayoutSubviews()
        if self.tag <= 0 {
            if UIApplication.isRTL()  {
                if self.titleLabel?.textAlignment == .right { return }
                self.titleLabel?.textAlignment = .right
            } else {
                if self.titleLabel?.textAlignment == .left { return }
                self.titleLabel?.textAlignment = .left
            }
        }
    }
}

extension UIApplication {
    class func isRTL() -> Bool{
        return UIApplication.shared.userInterfaceLayoutDirection == .rightToLeft
    }
}



