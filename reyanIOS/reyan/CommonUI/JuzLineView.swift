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
