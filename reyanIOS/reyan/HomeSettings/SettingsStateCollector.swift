//
//  SettingsStateCollector.swift
//  reyan
//
//  Created by meghdad on 4/23/21.
//


import Foundation
import nativeShared


class SettingsStateCollector : ObservableObject {
   
    @Published var uiState: SettingsState
    
    func bindState(presenter : SettingsPresenter) {
        
        IntropExtensionsKt.consume(presenter) { viewState in
            print("SettingsStateCollector : states : value(as uiState) = \(String(describing: viewState))")
            
            self.uiState = viewState as! SettingsState
        }
    }
    
    func errorHandler(ku : KotlinUnit?, error : Error?) -> Void {
        print("SettingsStateCollector : KotlinUnit = \(String(describing: ku))")
        print("SettingsStateCollector : error = \(String(describing: error))")
    }
    
    init() {
        let idleState = SettingsState.Companion.idle(SettingsState.Companion.init())()
        uiState = idleState
    }
}
