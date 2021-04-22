//
//  SurahRowView.swift
//  reyan
//
//  Created by meghdad on 11/29/20.
//

import SwiftUI
import nativeShared

struct SurahRowView : View{
    
    @State var updateUI : Bool = false

    let surahUIItem : SurahUIModel
    
    var body: some View {
     
//        let _ = print("surahUIItem = \(surahUIItem)")
        
        if updateUI == true {
           // check updateUI just to refresh swiftui view on Appear
        }
        
        VStack {
            HStack {
                
                ZStack {
                    Image.ic_surah_green
                        .resizable()
                        .frame(width: 32, height: 32, alignment: .center)
                    
                    Text(String(surahUIItem.order))
                        .font(.custom("Vazir", size: 10.0))
                        .fontWeight(.bold)
                }
                
                VStack(alignment:.leading) {
                    Text(surahUIItem.mainName)
                        .font(.custom("Vazir", size: 14.0))

                    Spacer()
                        .frame(height: 2, alignment: .center)
                    
                    HStack {
                        Text(surahUIItem.revealedType.rawName)
                            .font(.custom("Vazir", size: 11.0))
                            
                        Spacer()
                            .frame(width:2)

                        Text("-")
                            .font(.custom("Vazir", size: 11.0))

                        Spacer()
                            .frame(width:2)
                        
                        Text("\(surahUIItem.ayaCount) \(NSLocalizedString("Aya", comment: ""))")
                            .font(.custom("Vazir", size: 11.0))

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
        .onAppear {
            updateUI = !updateUI
        }
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
