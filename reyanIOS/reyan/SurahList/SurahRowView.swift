//
//  SurahRowView.swift
//  reyan
//
//  Created by meghdad on 11/29/20.
//

import SwiftUI
import nativeShared

struct SurahRowView : View{
    
    let surahUIItem : SurahUIModel
    
    var body: some View {
            
        let ayaString =
            Text( NSLocalizedString("Aya", comment: ""))
            .font(.custom("Vazir", size: 11.0))

        let acText =
            Text("\(surahUIItem.ayaCount)")
            .font(.custom("Vazir", size: 11.0))
        
        
        VStack {
            HStack {
                
                ZStack {
                    Image.ic_surah_green
                        .resizable()
                        .frame(width: 32, height: 32, alignment: .center)
                    
                    Text("\(surahUIItem.order)")
                        .font(.custom("Vazir", size: 10.0))
                        .fontWeight(.bold)
                }
                
                VStack(alignment:.leading) {
                    Text(surahUIItem.mainName)
                        .font(.custom("Vazir", size: 14.0))

                    Spacer()
                        .frame(height: 2, alignment: .center)
                    
                    HStack {
                        Text(NSLocalizedString(surahUIItem.revealedType.rawName, comment: ""))
                            .font(.custom("Vazir", size: 11.0))
                            
                        Spacer()
                            .frame(width:2)

                        Text("-")
                            .font(.custom("Vazir", size: 11.0))

                        Spacer()
                            .frame(width:2)
                        
                        if UIApplication.isRTL() {
                            ayaString
                            Spacer()
                                .frame(width:2)
                            acText
                        }else{
                            acText
                            Spacer()
                                .frame(width:2)
                            ayaString
                        }
                    }
                    .foregroundColor(Color.init(red: 0.51, green: 0.51, blue: 0.51))
                }
                .padding(.leading)
                
                Spacer()
                
                Text(surahUIItem.arabicName)
                    .font(.custom("Vazir", size: 18.0))
                    .foregroundColor(Color.init(red: 0.11, green: 0.39, blue: 0.38))

            }
            
//            Divider()
//                .frame(width: .infinity, height: 2, alignment: .center)
//                .background(Color.green)

        }
        .padding(.vertical)
        .environment(\.locale, Locale(identifier:  currentLanguage.rawValue))

    }
    
    
}
//
//struct SurahRowView_preview : PreviewProvider {
//    
//    static var previews: some View {
//        
////        SurahRowView(
////            surahUIItem: SurahUIModel(id: SurahID(id: "1"), order: 1, arabicName: "المومنون", mainName:  "Al-Moemenoon", revealedType: RevealedType.meccan, ayaCount: 23)
////        )
//    }
//    
//}
