//
//  RemoveSeperateLinesInList.swift
//  reyan
//
//  Created by meghdad on 11/29/20.
//

import SwiftUI

extension View {
    func listRow() -> some View {
        self.frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .leading)
            .listRowInsets(EdgeInsets(top: -1, leading: -1, bottom: -1, trailing: -1))
            .background(Color.clear)
    }
}
