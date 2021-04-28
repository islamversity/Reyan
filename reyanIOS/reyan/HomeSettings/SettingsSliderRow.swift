//
//  SettingsSliderRow.swift
//  reyan
//
//  Created by meghdad on 4/28/21.
//


import SwiftUI

struct SettingsSliderRow : View {
    
    let title : String
    let mainText : String
    let minSize : Double
    let maxSize : Double
    let selectedSize : Double
        
    @Binding var mainTextSize : Double //= 20.0

    public init(
        title : String,
        mainText : String,
        minSize : Double,
        maxSize : Double,
        selectedSize : Double,
        mainTextSize: Binding<Double>
    ) {
        self.title = title
        self.mainText = mainText
        self.minSize = minSize
        self.maxSize = maxSize
        self.selectedSize = selectedSize
        _mainTextSize = mainTextSize
    }

    
    @State var isFirstLoad = true
    
    var body: some View {
        
        VStack {
            
            HStack {
                
                if UIApplication.isRTL() {
                    Spacer()
                }
                
                VStack{
                    Text(title)
                        .foregroundColor(Color.green_600)
                }
                .padding(.all, 8)
                
                if !UIApplication.isRTL() {
                    Spacer()
                }
            }
            
            Text(mainText)
                .foregroundColor(Color.gray_800)
                .multilineTextAlignment(.center)
                .font(.custom("Vazir",
                              size: isFirstLoad ? CGFloat(selectedSize) : CGFloat(mainTextSize)))
            
            Slider(
                value: isFirstLoad ? .constant(selectedSize) : $mainTextSize,
                in: minSize...maxSize,
                onEditingChanged: { (isEditing) in
                    isFirstLoad = false
                },
                minimumValueLabel: Text("\(Int(minSize))"),
                maximumValueLabel: Text("\(Int(maxSize))")
            ){}
            .foregroundColor(.green_800)
            .accentColor(.green_600)
            
            Divider()
                .padding(.horizontal, 20)
        }
        .padding(.horizontal, 10)

    }
}


struct SettingsSliderRow_Preview : PreviewProvider {
    static var previews: some View {
        SettingsSliderRow(
            title: "Main Text Size",
            mainText: "In the name of Allah, The Beneficent, The Merciful",
            minSize : 10.0,
            maxSize : 150.0,
            selectedSize : 35,
            mainTextSize: .constant(20.0)
        )
    }
}
