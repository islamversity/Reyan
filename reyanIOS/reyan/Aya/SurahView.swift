//
//  SurahView.swift
//  reyan
//
//  Created by meghdad on 1/2/21.
//

//
//import SwiftUI
//import nativeShared
//
//struct SurahView: View {
//    
//    let header : SurahHeaderUIModel
//    let ayaList : [AyaUIModel]
//    
//    init(header : SurahHeaderUIModel, ayaList : [AyaUIModel]) {
//        self.header = header
//        self.ayaList = ayaList
//    }
//    
//    var body: some View {
//        
//        VStack {
//            
//            SurahHeaderView(
//                id: header.rowId,
//                order: header.number,
//                name: header.nameTranslated,
//                originalName: header.name,
//                origin: header.origin,
//                verses: String(header.verses)
//            )
//            
//            Image.bismillah
//                .resizable()
//                .frame(width: 150, height: 23, alignment: .center)
//            
//            
//            ScrollView {
//                
//                VStack {
//                    
//                    ForEach (ayaList, id: \.self) { item in
//                        
//                        AyaRowView(
//                            id: item.rowId,
//                            mainText: item.content,
//                            firstTranslatedText: item.translation1 ?? "",
//                            secondTranslatedText: item.translation2 ?? "",
//                            order : String(item.order)
//                        )
//                    }
//                }
//            }
//            .padding(.top, 20)
//            
//        }
//    }
//}
//
//struct SurahView_Previews: PreviewProvider {
//    static var previews: some View {
//        SurahView(ayaList: ["fdf ", "fd " , "ddd", "fd545"])
//            .previewDevice("iPhone 12 Pro Max")
//    }
//}

//
//AyaUIModel(rowId: "1", content: "dsdsd sd sd", translation1: "adasdsd sd ", translation2: " sds fer fr f", order: 20, toolbarVisible: true, hizb: 12, juz: 23, sajdah: SajdahTypeUIModel.VISIBLE.NONE.init() )
