//
//  JuzListStateCollector.swift
//  reyan
//
//  Created by meghdad on 12/10/20.
//

import Foundation
import nativeShared

class JuzListStateCollector : ObservableObject {
   
    @Published var uiState: JuzListState
    
    func bindState(presenter : JuzListPresenter) {
        
        IntropExtensionsKt.consume(presenter) { viewState in
            print("JuzListStateCollector : states : value(as uiState) = \(String(describing: viewState))")
            
            self.uiState = viewState as! JuzListState
        }
    }
    
    func errorHandler(ku : KotlinUnit?, error : Error?) -> Void {
        print("JuzListStateCollector : KotlinUnit = \(String(describing: ku))")
        print("JuzListStateCollector : error = \(String(describing: error))")
    }
    
    init() {
        let idleState = JuzListState.Companion.idle(JuzListState.Companion.init())()
        uiState = idleState
    }
}

