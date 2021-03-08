//
//  JuzLineView.swift
//  reyan
//
//  Created by meghdad on 12/10/20.
//


import SwiftUI
import nativeShared

struct JuzLineView : View{
        
    var body: some View {
     
        
//        VStack {
//
//            HStack {
//
//                Button(action: {
//                    print("settings click")
//                    //                presenter.processIntents(intents: QuranHomeIntent.SettingsClicked.init())
//                })
//                {
//                    Image("ic_settings")
//                        .resizable()
//                        .frame(width: 24, height: 24, alignment: .center)
//                }
//
//                Spacer()
//            }
//            .padding(.top, 40)
//            .fixedSize(horizontal: false, vertical: true)
//
//            ZStack {
//                Color.white
//
//                Button(action: {
//
//                    //            presenter.processIntents(intents: QuranHomeIntent.SearchClicked.init())
//
//                })
//                {
//                    HStack {
//
//                        Text("Type your search")
//                            .foregroundColor(.gray)
//                            .overlay(RoundedRectangle(cornerRadius: 10).stroke(Color.white, lineWidth: 0))
//                            .padding(.vertical, 10.0)
//                            .padding(.horizontal, 20.0)
//                            .font(.custom("Vazir", size: 14))
//
//                        Spacer()
//
//                        Image("ic_search")
//                            .resizable()
//                            .frame(width: 24, height: 24, alignment: .center)
//                            .padding(.trailing, 20)
//                    }
//                }
//            }
//            .padding(.top, 50)
//            .cornerRadius(15.0)
//            .fixedSize(horizontal: false, vertical: true)
//        }
//        .padding(.horizontal, 20.0)
//        .edgesIgnoringSafeArea(.all)

       
            HStack {
                Image("ic_circle_light_green")
                    .resizable()
                    .frame(width: 8, height: 8, alignment: .center)
                Spacer()
                    .frame(width: .infinity, height: 1, alignment: .center)
                    .background(
                        LinearGradient(
                            gradient: Gradient(
                                colors: [
                                    Color.init(red: 0.79, green: 0.93, blue: 0.91),
                                    Color.init(red: 0.19, green: 0.71, blue: 0.67)
                                ]
                            ),
                            startPoint: .leading,
                            endPoint: .trailing
                        )
                    )
                Image("ic_circle_dark_green")
                    .resizable()
                    .frame(width: 8, height: 8, alignment: .center)
            }
           
    }
}

struct JuzLineView_Preview : PreviewProvider {
    static var  previews: some View{
        JuzLineView()
    }
}
