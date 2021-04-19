//
//  AMPLocalizeUtils.swift
//  MyCatalogue
//
//  Created by anoopm on 09/02/17.
//  Copyright © 2017 anoopm. All rights reserved.
//

import UIKit

let defaultLocalizer = LocalizeUtils.defaultLocalizer

class LocalizeUtils: NSObject {

    static let defaultLocalizer = LocalizeUtils()
    var appbundle = Bundle.main
    
    func setSelectedLanguage(lang: String) {
        guard let langPath = Bundle.main.path(forResource: lang, ofType: "lproj") else {
            appbundle = Bundle.main
            return
        }
        appbundle = Bundle(path: langPath)!
    }
    
    func stringForKey(key: String) -> String {
        return appbundle.localizedString(forKey: key, value: "", table: nil)
    }
}
