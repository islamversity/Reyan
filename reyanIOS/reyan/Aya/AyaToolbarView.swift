
//  Created by meghdad on 1/1/21.


import SwiftUI

struct AyaToolbarView : View {
    
    var intents : AyaToolbarIntents
    
    init(intents : AyaToolbarIntents) {
        self.intents = intents
    }
    
    
    var body : some View {
        
        HStack{
            
            Spacer()
            
            Button(action: {
                intents.bookmarkClick = true
            })
            {
            Image.ic_play
                .resizable()
                .frame(width: 10.5, height: 15, alignment: .center)
                .padding(10)
            }
            .buttonStyle(BorderlessButtonStyle())
            
            Button(action: {
                
            })
            {
            Image.ic_share
                .resizable()
                .frame(width: 12, height: 15, alignment: .center)
                .padding(10)
            }
            .buttonStyle(BorderlessButtonStyle())
            
            Button(action: {
                
            })
            {
            Image.ic_bookmark
                .resizable()
                .frame(width: 10.5, height: 15, alignment: .center)
                .padding(10)
            }
            .buttonStyle(BorderlessButtonStyle())
            
        }
        .background(Color.green_50)
        .cornerRadius(20)
        .frame(width: .infinity, height: 28, alignment: .center)
    }
    
}

struct AyaToolbarView_Preview : PreviewProvider {

    
    static var previews : some View {

        AyaToolbarView(intents: AyaToolbarIntents())

    }
}
