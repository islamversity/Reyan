//
//  File.swift
//  reyan
//
//  Created by meghdad on 12/3/20.

import Foundation
import nativeShared


class SurahListStateCollector : FlowCollector, ObservableObject {
   

    @Published var uiState: SurahListState
    let idleState = SurahListState.Companion.idle(SurahListState.Companion.init())()
    
    
    func emit(value: Any?, completionHandler: @escaping (KotlinUnit?, Error?) -> Void) {
        
        print("SurahListStateCollector : states : value(as uiState) = \(String(describing: value))")
//        print("SurahListStateCollector : completionHandler = \(String(describing: completionHandler))")
        
        uiState = value as? SurahListState ?? idleState
    }
    
    func errorHandler(ku : KotlinUnit?, error : Error?) -> Void {
        
//        print("SurahListStateCollector : KotlinUnit = \(String(describing: ku))")
        print("SurahListStateCollector : error = \(String(describing: error))")
    }
    
    
    init() {
        uiState = idleState
    }
}
