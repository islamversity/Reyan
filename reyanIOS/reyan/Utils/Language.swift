//
//  Language.swift
//  reyan
//
//  Created by meghdad on 4/21/21.
//

import UIKit
import Foundation


enum languages : String {
    case english = "en"
    case farsi = "fa"
    case arabic = "ar"
}

public var currentLanguage : String = languages.english.rawValue


class Language {
    
    
    class func setLanguage(lang: String) {
        switch lang {
        case Constants.englishLang:
            setLanguage(lang: .english)
        case Constants.farsiLang:
            setLanguage(lang: .farsi)
        case Constants.arabicLang:
            setLanguage(lang: .arabic)
        default:
            setLanguage(lang: .english)
        }
    }
    
    class func setLanguage(lang: languages) {
        print("setLanguage : Language = \(lang)")
        if let localeLang = Locale.current.languageCode {
            print("setLanguage : local language is : \(localeLang)")
        }else{
            print("setLanguage : local language is empty")
        }

        currentLanguage = lang.rawValue
        saveLanguage(lang: currentLanguage)

        // for changing language without restarting
//        Localizer.DoTheSwizzling()
        
//        setProperSemantic()
    }
    
    
    class func getSavedLanguage() -> String {
        let userdef = UserDefaults.standard
        let savedLang = userdef.string(forKey: Constants.APPLE_LANGUAGE_KEY) ?? languages.english.rawValue
        
        return savedLang
    }
    
    class func saveLanguage(lang: String) {
        let userdef = UserDefaults.standard
        userdef.set(lang, forKey: Constants.APPLE_LANGUAGE_KEY)
        userdef.synchronize()
    }
    
    class func setProperSemantic() {
        let savedLang = Language.getSavedLanguage()

        var semantic = UISemanticContentAttribute.forceLeftToRight
        
        if savedLang == languages.farsi.rawValue || savedLang == languages.arabic.rawValue {
            semantic = .forceRightToLeft
        }
        
        UIView.appearance().semanticContentAttribute = semantic
        UITextView.appearance().semanticContentAttribute = semantic
        UILabel.appearance().semanticContentAttribute = semantic
        UIButton.appearance().semanticContentAttribute = semantic
        UITextField.appearance().semanticContentAttribute = semantic
        
        UserDefaults.standard.set(UIApplication.isRTL(), forKey: "AppleTextDirection")
        UserDefaults.standard.set(UIApplication.isRTL(), forKey: "NSForceRightToLeftWritingDirection")
        UserDefaults.standard.synchronize()
    }
    
}
