//
//  SettingsStateCollector.swift
//  reyan
//
//  Created by meghdad on 4/23/21.
//


import Foundation
import nativeShared
import SwiftUI


//                state.base

//                state.secondSurahNameCalligraphies -> لیست -> رسم الخط  سوره //state.selectedSecondSurahNameCalligraphy
//                state.firstTranslationCalligraphies - > // state.selectedFirstTranslationCalligraphy
//                state.secondTranslationCalligraphies -> // state.selectedSecondTranslationCalligraphy
//                state.quranTextFontSize
//                state.translateTextFontSize



class SettingsStateCollector : ObservableObject {
   
    @Published var uiState: SettingsState
    @Published var surahCaligraphyButtonList : [ActionSheet.Button] = []
    @Published var firstTranslationCaligraphyButtonList : [ActionSheet.Button] = []
    @Published var secondTranslationCaligraphyButtonList : [ActionSheet.Button] = []

    func bindState(presenter : SettingsPresenter) {
        
        IntropExtensionsKt.consume(presenter) { viewState in
            print("SettingsStateCollector : states : value(as uiState) = \(String(describing: viewState))")
            
            self.uiState = viewState as! SettingsState
            
            self.makeSurahCaligraphyButtons(presenter : presenter)
            self.makeFirstTranslationCaligraphyButtons(presenter : presenter)
            self.makeSecondTranslationCaligraphyButtons(presenter : presenter)
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
    
    fileprivate func makeSurahCaligraphyButtons(presenter : SettingsPresenter) {

        var surahCaligraphyButtons : [ActionSheet.Button] = []
        var txtView = Text("")

        for ssnc in self.uiState.secondSurahNameCalligraphies {
            if ssnc.name.isEmpty {
                txtView = Text(NSLocalizedString("no_option", comment: ""))
            }else{
                txtView = Text(ssnc.name)
            }
           
            surahCaligraphyButtons.append(
                .default(txtView){
                    presenter.processIntents(intents: SettingsIntent.NewSecondSurahNameCalligraphySelected.init(calligraphy: ssnc))
                }
            )
        }
        
        surahCaligraphyButtons.append(.cancel(Text(NSLocalizedString("Cancel", comment: ""))))
        self.surahCaligraphyButtonList = surahCaligraphyButtons
    }
    
    fileprivate func makeFirstTranslationCaligraphyButtons(presenter : SettingsPresenter) {

        var firstTranslationCaligraphyButtons : [ActionSheet.Button] = []
        var txtView = Text("")

        for ftc in self.uiState.firstTranslationCalligraphies {
            if ftc.name.isEmpty {
                txtView = Text(NSLocalizedString("no_option", comment: ""))
            }else{
                txtView = Text(ftc.name)
            }
            
            firstTranslationCaligraphyButtons.append(
                .default(txtView){
                    presenter.processIntents(intents: SettingsIntent.NewFirstTranslation.init(language: ftc))
                }
            )
        }
        firstTranslationCaligraphyButtons.append(.cancel(Text(NSLocalizedString("Cancel", comment: ""))))
        self.firstTranslationCaligraphyButtonList = firstTranslationCaligraphyButtons
    }
    
    fileprivate func makeSecondTranslationCaligraphyButtons(presenter : SettingsPresenter) {

        var secondTranslationCaligraphyButtons : [ActionSheet.Button] = []
        var txtView = Text("")

        for stc in self.uiState.secondTranslationCalligraphies {
            if stc.name.isEmpty {
                txtView = Text(NSLocalizedString("no_option", comment: ""))
            }else{
                txtView = Text(stc.name)
            }
            secondTranslationCaligraphyButtons.append(
                .default(txtView){
                    presenter.processIntents(intents: SettingsIntent.NewSecondTranslation.init(language: stc))
                }
            )
        }
        
        secondTranslationCaligraphyButtons.append(.cancel(Text(NSLocalizedString("Cancel", comment: ""))))
        self.secondTranslationCaligraphyButtonList = secondTranslationCaligraphyButtons
    }
    
    
}
