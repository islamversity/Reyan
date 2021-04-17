
//  Created by meghdad on 11/27/20.

import SwiftUI
import UIKit

struct SearchBarView : View {
    
    @Binding var searchText : String
    @Binding var isFocused : Bool

    var body: some View {
        ZStack {
            Color.white
            
//            HStack {
//
//                Button(action: {
//                    print("settings click")
////                    presenter.processIntents(intents: QuranHomeIntent.SettingsClicked.init())
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
            HStack {

                TextField("Type your search",text: $searchText, onEditingChanged: { isFocused in
                    print(isFocused ? "focused" : "unfocused")
                    self.isFocused = isFocused
                })
//                    .border(Color.blue, width: 0)
                    .overlay(RoundedRectangle(cornerRadius: 10).stroke(Color.white, lineWidth: 0))
                    .padding(.vertical, 10.0)
                    .padding(.horizontal, 20.0)
                    .font(.custom("Vazir", size: 16))

                Image.ic_search
                    .resizable()
                    .frame(width: 24, height: 24, alignment: .center)
                    .padding(.trailing, 20)

            }
        }
        .cornerRadius(15.0)
        .fixedSize(horizontal: false, vertical: true)
    }
}

struct SearchBarView_Previews: PreviewProvider {
    static var previews: some View {
        SearchBarView(searchText: .constant(""), isFocused: .constant(false))
    }
}


