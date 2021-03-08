//
//  OnBoardingStateCollector.swift
//  reyan
//
//  Created by meghdad on 1/15/21.
//


import Foundation
import nativeShared


class OnBoardingStateCollector : ObservableObject {
   
    @Published var uiState: OnBoardingState
    @Published var loadingPercent: Int = 0

    init() {
        let idleState = OnBoardingState.Companion.idle(OnBoardingState.Companion.init())()
        uiState = idleState
    }
    
    func bindState(presenter : OnBoardingPresenter) {
        
        IntropExtensionsKt.consume(presenter) { viewState in
            self.uiState = viewState as! OnBoardingState
        }
    }
    
    func errorHandler(ku : KotlinUnit?, error : Error?) -> Void {
        print("OnBoardingStateCollector : KotlinUnit = \(String(describing: ku))")
        print("OnBoardingStateCollector : error = \(String(describing: error))")
    }
    
    func bindLoadingPercent(iOSDatabaseFiller : IOSDatabaseFiller) {
        
        FlowUtilsKt.wrap(iOSDatabaseFiller.fillerUseCase.status()).watch { (status: AnyObject?) in
            if let stat = status  {
                let fs = stat as! FillingStatus
                print("database filling Status= \(fs)")
                
                if let fsf = stat as? FillingStatus.Filling.FillingFilled {
//                    loadingText = "\(fsf.total)"
                    print("loadingText= \(fsf.total)")
                    
                    self.loadingPercent = Int(fsf.total)
                }

                if fs == FillingStatus.Done.init() {
                    print("fill DB finished ... \(Date())")
                    iOSNavigator.goTo(screen: Screens.Home())
                }
            }

        }
    }
}
