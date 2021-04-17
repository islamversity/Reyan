//
//  SuraStateCollector.swift
//  reyan
//
//  Created by meghdad on 12/30/20.
//


import Foundation
import nativeShared

//  states : value(as uiState) = SurahState(base=BaseState(stable=true, error=com.islamversity.core.mvi.BaseState.ErrorState.Disabled@14bb7c8, showLoading=false, showKeyboard=false, confirm=false), showBismillah=false, bismillah=, mainAyaFontSize=20, translationFontSize=40, items=[SurahHeaderUIModel(rowId=D65733FE-0253-47FC-9593-8A68DB0CB5D0, number=1, name=الفاتحة, nameTranslated=الفاتحة, origin=meccan, verses=7, fontSize=20, showBismillah=false), AyaUIModel(rowId=968622B0-2D29-45AB-A737-D4572337F1BF, content=بِسْمِ اللَّهِ الرَّحْمَنِ الرَّحِيمِ, translation1=null, translation2=null, order=1, toolbarVisible=false, hizb=null, juz=1, sajdah=com.islamversity.surah.model.SajdahTypeUIModel.NONE@1750548), AyaUIModel(rowId=4D83F1D4-7A4A-4AE6-8758-ADD1E344844F, content=الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ, translation1=null, translation2=null, order=2, toolbarVisible=false, hizb=null, juz=null, sajdah=com.islamversity.surah.model.SajdahTypeUIModel.NONE@1750548), AyaUIModel(rowId=20544D7F-0D2B-44C8-B0EF-42B3BAF9EC8D, content=الرَّحْمَنِ الرَّحِيمِ, translation1=null, translation2=null, order=3, toolbarVisible=false, hizb=null, juz=null, sajdah=com.islamversity.surah.model.SajdahTypeUIModel.NONE@1750548), AyaUIModel(rowId=506976EE-36BB-4EC0-A018-6B3CF5AF8188, content=مَالِكِ يَوْمِ الدِّينِ, translation1=null, translation2=null, order=4, toolbarVisible=false, hizb=null, juz=null, sajdah=com.islamversity.surah.model.SajdahTypeUIModel.NONE@1750548), AyaUIModel(rowId=C5DBAD76-C8F4-4986-828B-00398F28E5D7, content=إِيَّاكَ نَعْبُدُ وَإِيَّاكَ نَسْتَعِينُ, translation1=null, translation2=null, order=5, toolbarVisible=false, hizb=null, juz=null, sajdah=com.islamversity.surah.model.SajdahTypeUIModel.NONE@1750548), AyaUIModel(rowId=92B3FAAE-066D-4395-8CFC-6C1C554121DD, content=اهْدِنَا الصِّرَاطَ الْمُسْتَقِيمَ, translation1=null, translation2=null, order=6, toolbarVisible=false, hizb=null, juz=null, sajdah=com.islamversity.surah.model.SajdahTypeUIModel.NONE@1750548), AyaUIModel(rowId=CD62AC49-16A0-4DFD-91B5-24A93904642F, content=صِرَاطَ الَّذِينَ أَنْعَمْتَ عَلَيْهِمْ غَيْرِ الْمَغْضُوبِ عَلَيْهِمْ وَلَا الضَّالِّينَ, translation1=null, translation2=null, order=7, toolbarVisible=false, hizb=null, juz=null, sajdah=com.islamversity.surah.model.SajdahTypeUIModel.NONE@1750548)], startFrom=0, closeScreen=false, scrollToAya=null, settingsState=SurahSettingsState(ayaCalligraphies=[CalligraphyUIModel(id=, name=), CalligraphyUIModel(id=0D3382EC-B521-41F4-AC6D-CF1E668B3AB9, name=عربی-بسیط), CalligraphyUIModel(id=127262E9-1768-41DA-A01E-5C56260B4DFD, name=English-Yusuf Ali), CalligraphyUIModel(id=7D2C364C-CF0B-41CF-AEBA-DE752594A0B2, name=فارسی-مکرام شیرازی)], surahNameCalligraphies=[], selectedFirstTranslationAyaCalligraphy=null, selectedSecondTranslationAyaCalligraphy=null, quranTextFontSize=20, translateTextFontSize=40, ayaToolbarVisible=false))

class SurahStateCollector : ObservableObject {
   
    @Published var uiState: SurahState
    
    func bindState(presenter : SurahPresenter) {
        
        IntropExtensionsKt.consume(presenter) { viewState in
//            print("SurahStateCollector : states : value(as uiState) = \(String(describing: viewState))")
            
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
