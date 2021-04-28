//
//  SurahHeaderView.swift
//  reyan
//
//  Created by meghdad on 1/1/21.
//

import SwiftUI

struct SurahHeaderView : View {
    
    let id : String
    let order : Int
    let name : String
    let originalName : String
    let origin : String
    let verses : Int32
    let showismillah : Bool
    
    
    var body: some View {
        
        let ayaString =
            Text( NSLocalizedString("Aya", comment: ""))
            .font(.custom("Vazir", size: 11.0))

        let ayaCountText =
            Text("\(verses)")
            .font(.custom("Vazir", size: 11.0))
        
        
        VStack {
            
            ZStack {
                Image.background_surah_header
                
                VStack {
                    
                    ZStack {
                        
                        Image.ic_surah_green
                            .resizable()
                            .renderingMode(.template)
                            .foregroundColor(.white)
                            .frame(width: 42, height: 42, alignment: .center)
                        
                        Text("\(order)")
                            .foregroundColor(.white)
                            .fontWeight(.bold)
                            .multilineTextAlignment(.center)
                            .lineLimit(1)
                            .padding(.trailing, 1.0)
                            .font(.custom("Vazir", size: 12.0))
                    }
                    .padding(.top, 12)
                    
                    Text(name)
                        .foregroundColor(.white)
                        .fontWeight(.bold)
                        .multilineTextAlignment(.center)
                        .lineLimit(1)
                        .padding(.horizontal, 10.0)
                        .font(.custom("Vazir", size: 20.0))
                    
                    Text(originalName)
                        .foregroundColor(.white)
                        .fontWeight(.bold)
                        .multilineTextAlignment(.center)
                        .lineLimit(1)
                        .padding(.horizontal, 10.0)
                        .font(.custom("Vazir", size: 18.0))
                    
                    Spacer()
                    
                    HStack {
                    Text(NSLocalizedString(origin, comment: ""))
                        .multilineTextAlignment(.center)
                        .lineLimit(1)
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
                            ayaCountText
                        }else{
                            ayaCountText
                            Spacer()
                                .frame(width:2)
                            ayaString
                        }
                    }
                    .foregroundColor(.white)
                    .padding(.bottom, 20)

                }
                .padding()
            }
            .fixedSize()

            if showismillah {
                Image.bismillah
                    .resizable()
                    .frame(width: 150, height: 23, alignment: .center)
                    .padding(.bottom, 15)
            }
        }
        .environment(\.locale, Locale(identifier:  currentLanguage.rawValue))

    }
}
//
//struct SurahHeaderView_Preview : PreviewProvider {
//    
//    static var previews: some View {
//        
//        SurahHeaderView(id: "1", order: "114", name: "Ar_Rahman", originalName: "الرحمن", origin: "Medinan", verses: "256", showismillah: true)
//    }    
//}
