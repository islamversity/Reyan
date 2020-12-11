//
//  JuzListStateCollector.swift
//  reyan
//
//  Created by meghdad on 12/10/20.
//


import Foundation
import nativeShared


class JuzListStateCollector : FlowCollector, ObservableObject {
   

    @Published var uiState: JuzListState
    let idleState = JuzListState.Companion.idle(JuzListState.Companion.init())()
    
    
    func emit(value: Any?, completionHandler: @escaping (KotlinUnit?, Error?) -> Void) {
        
        print("JuzListStateCollector : states : value(as uiState) = \(String(describing: value))")
//        print("SurahListStateCollector : completionHandler = \(String(describing: completionHandler))")
        
        uiState = value as? JuzListState ?? idleState
    }
    
    func errorHandler(ku : KotlinUnit?, error : Error?) -> Void {
        
//        print("SurahListStateCollector : KotlinUnit = \(String(describing: ku))")
        print("JuzListStateCollector : error = \(String(describing: error))")
    }
    
    
    init() {
        uiState = idleState
    }
}
