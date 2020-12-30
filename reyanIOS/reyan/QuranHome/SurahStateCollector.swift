//
//  SuraStateCollector.swift
//  reyan
//
//  Created by meghdad on 12/30/20.
//


import Foundation
import nativeShared


class SurahStateCollector : ObservableObject {
   
    @Published var uiState: SurahState
    
    func bindState(presenter : SurahPresenter) {
        
        IntropExtensionsKt.consume(presenter) { viewState in
            print("SurahStateCollector : states : value(as uiState) = \(String(describing: viewState))")
            
            self.uiState = viewState as! SurahState
        }
    }
    
    func errorHandler(ku : KotlinUnit?, error : Error?) -> Void {
        print("QuranHomeStateCollector : KotlinUnit = \(String(describing: ku))")
        print("QuranHomeStateCollector : error = \(String(describing: error))")
    }
    
    init() {
        let idleState = SurahState.Companion.idle(SurahState.Companion.init())()
        uiState = idleState
    }
}
