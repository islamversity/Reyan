
//  Created by meghdad on 11/27/20.

import SwiftUI
import UIKit

struct SearchBarView : View {
    
    @State private var searchText = ""
    
    var body: some View {
        ZStack {
            Color.white
            
            HStack {
                
                TextField("Type your search",text: $searchText)
//                    .border(Color.blue, width: 0)
                    .overlay(RoundedRectangle(cornerRadius: 10).stroke(Color.white, lineWidth: 0))
                    .padding()
                    .font(.custom("Vazir", size: 20))
                
                Image("ic_search")
                    .resizable()
                    .frame(width: 32, height: 32, alignment: .center)
                    .padding(.trailing, 20)

            }
        }
        .cornerRadius(15.0)
        .fixedSize(horizontal: false, vertical: true)
    }
}

struct SearchBarView_Previews: PreviewProvider {
    static var previews: some View {
        SearchBarView()
    }
}


