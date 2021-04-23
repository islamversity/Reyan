//
//  AyaRowView.swift
//  reyan
//
//  Created by meghdad on 1/1/21.
//

import SwiftUI
import nativeShared
import Combine

struct AyaRowView : View {
    
    let uiModel : AyaUIModel
    let rowIntents : AyaRowIntents
    let ayaToolbarIntents = AyaToolbarIntents()
    var cancellables = Set<AnyCancellable>()

    init(uiModel: AyaUIModel, rowIntents: AyaRowIntents) {
        self.uiModel = uiModel
        self.rowIntents = rowIntents
                
        ayaToolbarIntents.$shareClick
            .sink { isClick in
                if isClick {
                    rowIntents.action = .ShareClick(uiModel)
                }
            }
            .store(in: &cancellables)
    }

    
    var body: some View {
        
        HStack(alignment : .top) {
                        
            VStack(alignment : .center) {
                
                ZStack {
                    Circle()
                        .frame(width: 24, height: 24, alignment: .center)
                        .foregroundColor(Color.green_600)

                    Text("\(uiModel.order)")
                        .foregroundColor(.white)
                        .fontWeight(.bold)
                        .multilineTextAlignment(.center)
                        .lineLimit(1)
                        .font(.custom("Vazir", size: 12.0))
                }
                
                if uiModel.juz != nil {
                    
                    ZStack {
                        Image.ic_juz_hiz_gold
                            .resizable()
                            .frame(width: 30, height: 53, alignment: .center)
                        
                        VStack {
                            
                            Text(NSLocalizedString("part", comment: ""))
                                .foregroundColor(.gold_dark)
                                .multilineTextAlignment(.center)
                                .lineLimit(1)
                                .font(.custom("Vazir", size: 8.0))
                                .padding(.top, 2)
                            
                            Text("\(uiModel.juz! as! Int)")
                                .foregroundColor(.gold_dark)
                                .fontWeight(.bold)
                                .multilineTextAlignment(.center)
                                .lineLimit(1)
                                .font(.custom("Vazir", size: 10.0))
                            
                            Text(NSLocalizedString("hizb", comment: ""))
                                .foregroundColor(.gold_dark)
                                .multilineTextAlignment(.center)
                                .lineLimit(1)
                                .font(.system(size: 6.0))
                            
                            Text("\(uiModel.hizb?.hizb ?? 0)")
                                .foregroundColor(.gold_dark)
                                .fontWeight(.bold)
                                .multilineTextAlignment(.center)
                                .lineLimit(1)
                                .font(.custom("Vazir", size: 10.0))
                            
                        }
                    }
                    .padding(.top,-5)
                }
                else{
                    if uiModel.hizb != nil {
                        
                        ZStack {
                            Image.ic_hizb_gold
                                .resizable()
                                .frame(width: 30, height: 40, alignment: .center)
                            
                            VStack {
                                
                                Text(NSLocalizedString("hizb", comment: ""))
                                    .foregroundColor(.gold_dark)
                                    .multilineTextAlignment(.center)
                                    .lineLimit(1)
                                    .font(.system(size: 6.0))
                                
                                Text("\(uiModel.hizb?.hizb ?? 0)")
                                    .foregroundColor(.gold_dark)
                                    .fontWeight(.bold)
                                    .multilineTextAlignment(.center)
                                    .lineLimit(1)
                                    .font(.custom("Vazir", size: 10.0))
                                
                                
                                if uiModel.hizb is AyaUIModel.HizbProgressQuarter {
                                    Text("1/4")
                                        .foregroundColor(.gold_dark)
                                        .multilineTextAlignment(.center)
                                        .lineLimit(1)
                                        .font(.system(size: 6.0))
                                }
                                if uiModel.hizb is AyaUIModel.HizbProgressHalf {
                                    Text("1/2")
                                        .foregroundColor(.gold_dark)
                                        .multilineTextAlignment(.center)
                                        .lineLimit(1)
                                        .font(.system(size: 6.0))
                                }
                                if uiModel.hizb is AyaUIModel.HizbProgressThreeFourth {
                                    Text("3/4")
                                        .foregroundColor(.gold_dark)
                                        .multilineTextAlignment(.center)
                                        .lineLimit(1)
                                        .font(.system(size: 6.0))
                                }
                            }
                        }
                        .padding(.top,-5)
                    }
                }
                
                if uiModel.sajdah is SajdahTypeUIModel.VISIBLERECOMMENDED {
                    ZStack {
                        Image.ic_sajdah
                            .resizable()
                            .frame(width: 25, height: 40, alignment: .center)

                        Text(NSLocalizedString("recomended_abr", comment: ""))
                            .foregroundColor(.black)
                            .multilineTextAlignment(.center)
                            .lineLimit(1)
                            .font(.system(size: 6.0))
                            .rotationEffect(Angle.init(degrees: 90.0))
                        
                    }
                }
                if uiModel.sajdah is SajdahTypeUIModel.VISIBLEOBLIGATORY {
                    ZStack {
                        Image.ic_sajdah
                            .resizable()
                            .frame(width: 25, height: 40, alignment: .center)
                        
                        Text(NSLocalizedString("obligatory_abr", comment: ""))
                            .foregroundColor(.black)
                            .multilineTextAlignment(.center)
                            .lineLimit(1)
                            .font(.system(size: 6.0))
                            .rotationEffect(Angle.init(degrees: 90.0))
                        
                    }
                }
            }
            .fixedSize(horizontal: true, vertical: true)
//            .padding(.trailing, 5)
            
            Spacer(minLength: 10)
            
            VStack(alignment: .trailing, spacing: 10) {
                
                if uiModel.toolbarVisible {
                    AyaToolbarView(intents: ayaToolbarIntents)
                }
                
                Text(uiModel.content)
                    .foregroundColor(.green_800)
                    .fontWeight(.bold)
                    .multilineTextAlignment(.trailing)
                    .font(.system(size: 16))
                    .lineSpacing(10.0)

//                    .font(.custom("Vazir", size: 20.0))
                if !(uiModel.translation1?.isEmpty ?? true) {
                    Text(uiModel.translation1!)
                        .foregroundColor(.gray_800)
                        .multilineTextAlignment(.leading)
                        .font(.caption)
                        .font(.system(size: 14))

                }
                
//                    .font(.custom("Vazir", size: 14.0))
                if !(uiModel.translation2?.isEmpty ?? true) {

                    Text(uiModel.translation2!)
                    .foregroundColor(.gray_800)
                    .multilineTextAlignment(.leading)
                    .font(.system(size: 14))
                }
//                    .font(.custom("Vazir", size: 14.0))
                
//                Spacer()
            }
            .fixedSize(horizontal: false, vertical: true)
            
        }
        .padding(.horizontal)
        .padding(.top, 5)
        .environment(\.locale, Locale(identifier:  currentLanguage))

    }
}

struct AyaRowView_Preview : PreviewProvider {
    
    static var previews: some View {
        AyaRowView (
            uiModel: AyaUIModel(
                rowId: "fdfdf",
                content:  "متن عربی آیه بسم الله الرحمن الرحیم و به نستعین . المحدالله رب .ب  سیمب نتیسبن تسیب منسیتب نستیبم نستیب العالمین . الرحمن الرحیم. مالک یوم الدین.",
                translation1: "The merciful The merciful The merciful The merciful The merciful The mercifulmercifulmercifulmerciful mercifulmercifulmercifulmercifulmercifulmerciful  The merciful The merciful The merciful The merciful",
                translation2: "ترجمه آیه به زبان فارسی ترجمه آیه به زبان فارسی  ترجمه آیه به زبان فارسی آیه به زبان فارسی ترجمه آیه به زبان فارسی  ترجمه آیه به زبان آیه به زبان فارسی ترجمه آیه به زبان فارسی  ترجمه آیه به زبان",
                order: 196,
                toolbarVisible: true,
                hizb: nil,
                juz: nil,
                sajdah: SajdahTypeUIModel.NONE.init()
            ),
            rowIntents: AyaRowIntents()
        )
        .previewDevice("iPhone 12 Pro Max")
    }
}
