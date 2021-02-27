//
//  BView.swift
//  reyan
//
//  Created by meghdad on 2/26/21.
//
//
//import SwiftUI
//
//
//struct BView: View {
//    
//    var body: some View {
//        
//        ZStack {
//            
//            Image.background_main
//                .resizable()
//                .edgesIgnoringSafeArea(.all)
//            
//            VStack {
//                
//                Image.logo
//                    .resizable()
//                    .frame(width: 132, height: 71, alignment: .center)
//                    .padding(.top, 200)
//                    
//                Text(" Reyan")
//                    .tracking(20)
//                
//                Spacer(minLength: 30)
//                
//                VStack{
//                    HStack{
//                        Text("Loading Content ...")
//                            .foregroundColor(.green_600)
//                            .font(.custom("Vazir", size: 14))
//                    
//                        Spacer()
//                        
//                        Text("45 %")
//                            .foregroundColor(.green_600)
//                            .font(.custom("Vazir", size: 14))
//                        
//                    }
//                    ProgressBarView(value: 70, maxValue: 100)
//                }
//                .padding(.horizontal, 20.0)
//
//                
//                Spacer()
//                
////                Button(action: {
////                    
////                }, label: {
////                    Text("Get Started")
////                        .foregroundColor(.white)
////                        .font(.custom("Vazir", size: 14))
////                })
////                .frame(width: 200, height: 50, alignment: .center)
////                .background(Color.green_600)
////                .cornerRadius(25)
//
//            }
//            
//        }
//        .navigationBarHidden(true)
//        
//    }
//}
//
//struct BV_Preview  : PreviewProvider {
//    static var previews: some View {
//        BView()
//    }
//}
//
