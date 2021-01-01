//
//  AyaRowView.swift
//  reyan
//
//  Created by meghdad on 1/1/21.
//

import SwiftUI

struct AyaRowView : View {
    
    let ayaText : String
    let ayaFirstTranslatedText: String
    let ayaSecondTranslatedText: String
    
    
    var  body: some View {
        
        HStack(alignment : .top) {
                        
            VStack(alignment : .center) {
                
                ZStack {
                    Circle()
                        .frame(width: 32, height: 32, alignment: .center)
                        .foregroundColor(Color.green_600)
                    
                    Text("256")
                        .foregroundColor(.white)
                        .fontWeight(.bold)
                        .multilineTextAlignment(.center)
                        .lineLimit(1)
                        .font(.custom("Vazir", size: 14.0))
                }
                
                Text("part")
                    .foregroundColor(.gold_dark)
                    .multilineTextAlignment(.center)
                    .lineLimit(1)
                    .font(.custom("Vazir", size: 8.0))
                
                ZStack {
                    Image.ic_surah_gold
                        .resizable()
                        .frame(width: 32, height: 32, alignment: .center)
                    
                    Text("30")
                        .foregroundColor(.gold_dark)
                        .fontWeight(.bold)
                        .multilineTextAlignment(.center)
                        .lineLimit(1)
                        .font(.custom("Vazir", size: 12.0))
                    
                    
                }
                .padding(.top,-7)
                
                Text("hizb")
                    .foregroundColor(.gold_dark)
                    .multilineTextAlignment(.center)
                    .lineLimit(1)
                    .font(.custom("Vazir", size: 8.0))
                
                ZStack {
                    Image.ic_hizb_gold
                        .resizable()
                        .frame(width: 32, height: 26, alignment: .center)
                    
                    VStack {
                        
                        
                        Text("3/4")
                            .foregroundColor(.gold_dark)
                            .fontWeight(.bold)
                            .multilineTextAlignment(.center)
                            .lineLimit(1)
                            .font(.custom("Vazir", size: 6.0))
                            .padding(.top, 2)
                        
                        Text("60")
                            .foregroundColor(.gold_dark)
                            .fontWeight(.bold)
                            .multilineTextAlignment(.center)
                            .lineLimit(1)
                            .font(.custom("Vazir", size: 11.0))
                    }
                    
                }
                .padding(.top, -8)
                
                Spacer()
                
            }
            .padding(.trailing, 5)
            
            
            VStack(alignment: .center, spacing: 10) {
                
                AyaToolbarView(intents: AyaToolbarIntents())
                
                Text(ayaText)
                    .foregroundColor(.green_800)
                    .fontWeight(.bold)
                    .multilineTextAlignment(.trailing)
                    .font(.custom("Vazir", size: 20.0))
                
                Text(ayaFirstTranslatedText)
                    .foregroundColor(.gray_800)
                    .fontWeight(.bold)
                    .multilineTextAlignment(.leading)
                    .font(.custom("Vazir", size: 14.0))
                
                Text(ayaSecondTranslatedText)
                    .foregroundColor(.gray_800)
                    .fontWeight(.bold)
                    .multilineTextAlignment(.leading)
                    .font(.custom("Vazir", size: 14.0))
                
                Spacer()
            }
            
        }
        .padding(.horizontal)
        
    }
}

struct AyaRowView_Preview : PreviewProvider {
    
    static var previews: some View {
        AyaRowView (
            ayaText: "متن عربی آیه بسم الله الرحمن الرحیم و به نستعین . المحدالله رب .ب  سیمب نتیسبن تسیب منسیتب نستیبم نستیب العالمین . الرحمن الرحیم. مالک یوم الدین.",
            ayaFirstTranslatedText: "The merciful The merciful The merciful The merciful The merciful The mercifulmercifulmercifulmerciful mercifulmercifulmercifulmercifulmercifulmerciful  The merciful The merciful The merciful The merciful",
            ayaSecondTranslatedText: "ترجمه آیه به زبان فارسی ترجمه آیه به زبان فارسی  ترجمه آیه به زبان فارسی آیه به زبان فارسی ترجمه آیه به زبان فارسی  ترجمه آیه به زبان آیه به زبان فارسی ترجمه آیه به زبان فارسی  ترجمه آیه به زبان"
        )
            .previewDevice("iPhone 12 Pro Max")
    }
}
