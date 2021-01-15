//
//  TabView.swift
//  reyan
//
//  Created by meghdad on 12/21/20.
//

import SwiftUI

struct TabView : View {
    
    let text : String
    let isSelected : Bool
    
    var body: some View {
        
        VStack{
            
            Text(text)
                .foregroundColor(isSelected ? Color.gray_800 : Color.primary)
            
            Rectangle()
                .frame(width: 80, height: 3, alignment: .center)
                .foregroundColor(isSelected ? Color.black : Color.clear)
                .padding(.top, 3)

        }
        .frame(width: 100, height: 50, alignment: .center)

    }
}


struct TabView_Preview : PreviewProvider {
    static var previews: some View {
        TabView(text: "Surah", isSelected: true)
    }
}
