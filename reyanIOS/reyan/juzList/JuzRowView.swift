
//  Created by meghdad on 12/8/20.

import SwiftUI
import nativeShared

struct JuzRowView : View{
    
    let juzUIItem : JozUIModel
    
    var body: some View {
        
        VStack(alignment: .leading) {
            
            HStack {
                
                ZStack {
                    Image.ic_surah_green
                        .resizable()
                        .frame(width: 48, height: 48, alignment: .center)
                    
                    Text("\(juzUIItem.number)")
                        .font(.custom("Vazir", size: 16.0))
                        .fontWeight(.bold)
                        .foregroundColor(Color(red: 0.11, green: 0.4, blue: 0.38, opacity: 1.0))
                }
                
                if UIApplication.isRTL()  {
                    Spacer()
                }
                
                Text(getJuzTitleArray()[Int(juzUIItem.number) - 1])
                    .foregroundColor(Color(red: 0.24, green: 0.24, blue: 0.24, opacity: 1.0))
                    .font(.custom("Vazir", size: 18.0))
            }
            
            Spacer()
                .frame(height: 1)
            
            VStack {
                
                HStack {
                    JuzLineView()
                    Text(juzUIItem.startTitle.dropLast(2))
                        .foregroundColor(.clear)
                }
                Spacer()
                    .frame(height: 1)
                
                HStack {
                    Text("\(juzUIItem.startTitle)")
                        .foregroundColor(Color.init(red: 0.5, green: 0.5, blue: 0.5))
                        .font(.custom("Vazir", size: 14.0))

                    Spacer()
                    Text(juzUIItem.endTitle)
                        .foregroundColor(Color.init(red: 0.5, green: 0.5, blue: 0.5))
                        .font(.custom("Vazir", size: 14.0))

                }
            }
            .padding(.leading, 58)
            .padding(.trailing, 58)
            
            
            Divider()
                .frame(width: .infinity, height: 2, alignment: .center)
        }
        .padding(.top, 15)
        .environment(\.locale, Locale(identifier:  currentLanguage.rawValue))

    }
}
//
//struct JuzRowView_Preview : PreviewProvider{
//    static var previews: some View {
//        JuzRowView()
//    }
//}
//
//struct JuzStartEndView : View {
//
//    var body: some View {
//
//    }
//}



//
//        VStack {
//            HStack {
//
//                ZStack {
//                    Image("ic_surah")
//                        .resizable()
//                        .frame(width: 48, height: 48, alignment: .center)
//
//                    Text(String(surahUIItem.order))
//                        .font(.custom("Vazir", size: 14.0))
//                        .fontWeight(.bold)
//                }
//
//                VStack(alignment:.leading) {
//                    Text(surahUIItem.mainName)
//                        .font(.custom("Vazir", size: 18.0))
//
//                    HStack {
//                        Text(surahUIItem.revealedType.rawName)
//                            .font(.custom("Vazir", size: 14.0))
//
//                        Text("-")
//
//                        Text("\(surahUIItem.ayaCount) Aya")
//                            .font(.custom("Vazir", size: 14.0))
//
//                    }
//                    .foregroundColor(.gray)
//                }
//                .padding(.leading)
//
//                Spacer()
//
//                Text(surahUIItem.arabicName)
//                    .font(.custom("Vazir", size: 26.0))
//                    .foregroundColor(.green)
//
//            }
//
////            Divider()
////                .frame(width: .infinity, height: 2, alignment: .center)
////                .background(Color.green)
//
//        }
//        .padding(.vertical)
//    }
//
//
//}
