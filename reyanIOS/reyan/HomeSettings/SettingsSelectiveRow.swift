//
//  SettingsSimpleRow.swift
//  reyan
//
//  Created by meghdad on 4/26/21.
//

import SwiftUI

struct SettingsSelectiveRow : View {
    
    let title : String
    let selectedOption : String
    
    var body: some View {
        
        VStack {
            
            HStack {
                
                if UIApplication.isRTL() {
                    Spacer()
                }
                
                
                VStack (alignment: .leading){
                    
                    Text(title)
                        .foregroundColor(Color.green_600)
                    
                    Text(selectedOption)
                        .foregroundColor(Color.gray_800)
                        .padding(.top, 1)
                    
                }
                .padding(.all, 8)
                
                if !UIApplication.isRTL() {
                    Spacer()
                }
            }
            
            Divider()
                .padding(.horizontal, 20)
        }
        .padding(.horizontal, 10)

    }
}


struct SettingsSimpleRow_Preview : PreviewProvider {
    static var previews: some View {
        SettingsSelectiveRow(title: "Language", selectedOption: "English")
    }
}
