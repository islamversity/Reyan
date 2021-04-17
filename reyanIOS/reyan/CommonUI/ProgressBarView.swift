//
//  ProgressBarView.swift
//  reyan
//
//  Created by meghdad on 2/26/21.

import SwiftUI

struct ProgressBarView: View {
    
    private let value: Double
    private let maxValue: Double
    private let backgroundEnabled: Bool
    private let backgroundColor: Color
    private let foregroundColor: Color
    
    private let height : CGFloat = 5
    
    init(value: Double,
         maxValue: Double,
         backgroundEnabled: Bool = true,
         backgroundColor: Color = Color.gray_500,
         foregroundColor: Color = Color.green_600
    ) {
        self.value = value
        self.maxValue = maxValue
        self.backgroundEnabled = backgroundEnabled
        self.backgroundColor = backgroundColor
        self.foregroundColor = foregroundColor
    }
    private func progress(value: Double,
                          maxValue: Double,
                          width: CGFloat) -> CGFloat {
        let percentage = value / maxValue
        return width *  CGFloat(percentage)
    }
    var body: some View {
        // 1
        ZStack {
            // 2
            GeometryReader { geometryReader in
                // 3
                if self.backgroundEnabled {
                    Capsule()
                        .foregroundColor(self.backgroundColor)
                }
                    
                Capsule()
                    .frame(width:
                            self.progress(
                                value: self.value,
                                maxValue: self.maxValue,
                                width: geometryReader.size.width
                            )
                    )
                    .foregroundColor(self.foregroundColor)
                    .animation(.easeIn)
            }
        }
        .frame(height: height, alignment: .center)

    }
}

struct ProgressConfig {
    static func backgroundColor() -> Color {
        return Color(UIColor(red: 245/255,
                             green: 245/255,
                             blue: 245/255,
                             alpha: 1.0))
    }
    
    static func foregroundColor() -> Color {
        return Color.black
    }
}

struct ProgressBarView_Prreview : PreviewProvider {
    
    static var previews: some View {
        ProgressBarView(value: 30, maxValue: 100)
    }
}
