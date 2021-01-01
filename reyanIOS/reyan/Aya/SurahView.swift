//
//  SurahView.swift
//  reyan
//
//  Created by meghdad on 1/2/21.
//


import SwiftUI
import nativeShared

struct SurahView: View {

    let ayaList : [String]
    
    init(ayaList : [String]) {
           
           self.ayaList = ayaList
           
       }

    
    var body: some View {
        
        VStack {
            
            SurahHeaderView(id: "1", order: 22, name: "Mohammad", originalName: "المحمد", origin: "Medinan", verses: 17)
            
            Image.bismillah
                .resizable()
                .frame(width: 150, height: 23, alignment: .center)
            
            ScrollView {
                
                VStack {
                    
                    ForEach (ayaList, id: \.self) { item in
                        
                        AyaRowView(ayaText: "متن عربی آیه بسم الله الرحمن الرحیم و به نستعین . المحدالله رب العالمین . الرحمن الرحیم. مالک یوم الدین. مالک مالک مالک مالک", ayaFirstTranslatedText: "The merciful The merciful The merciful The merciful The merciful The mercifulmercifulmercifulmerciful mercifulmercifulmercifulmercifulmercifulmerciful  The merciful The merciful The merciful The merciful", ayaSecondTranslatedText: "بتسیتنبم یسبان ستبناسنبتانسیتابن بتانسیتابن بتانسیتابن بتانسیتابن بتانسیتابن بتانسیتابن بتانسیتابن بتانسیتابن سیابنت تا ")
                    }
                }
            }
            .padding(.top, 20)

        }
    }
}

struct SurahView_Previews: PreviewProvider {
    static var previews: some View {
        SurahView(ayaList: ["fdf ", "fd " , "ddd", "fd545"])
            .previewDevice("iPhone 12 Pro Max")
    }
}

//
//AyaUIModel(rowId: "1", content: "dsdsd sd sd", translation1: "adasdsd sd ", translation2: " sds fer fr f", order: 20, toolbarVisible: true, hizb: 12, juz: 23, sajdah: SajdahTypeUIModel.VISIBLE.NONE.init() )
