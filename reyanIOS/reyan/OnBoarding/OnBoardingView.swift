
//  Created by meghdad on 1/15/21.


import SwiftUI
import nativeShared

struct OnBoardingView: View {
    
    @ObservedObject public var flowCollector: OnBoardingStateCollector = OnBoardingStateCollector()
    var presenter : OnBoardingPresenter
        
    init(presenter : OnBoardingPresenter, iOSDatabaseFiller : IOSDatabaseFiller) {
        self.presenter = presenter
        
        iOSDatabaseFiller.fillDB()

        flowCollector.bindState(presenter: presenter)
        flowCollector.bindLoadingPercent(iOSDatabaseFiller: iOSDatabaseFiller)
    }
    
    var body: some View {
        
        ZStack {
            
            Image.background_main
                .resizable()
                .edgesIgnoringSafeArea(.all)
            
            VStack {
                
                Image.logo
                    .resizable()
                    .frame(width: 132, height: 71, alignment: .center)
                    .padding(.top, 200)
                    
                Text(" Reyan")
                    .tracking(20)
                
                Spacer(minLength: 30)
                
                VStack{
                    HStack{
                        Text("Loading Content ...")
                            .foregroundColor(.green_600)
                            .font(.custom("Vazir", size: 14))
                    
                        Spacer()
                        
                        Text("\(flowCollector.loadingPercent) %")
                            .foregroundColor(.green_600)
                            .font(.custom("Vazir", size: 14))
                        
                    }
                    ProgressBarView(value: Double(flowCollector.loadingPercent), maxValue: 100)
                }
                .padding(.horizontal, 20.0)

                
                Spacer()
                
//                Button(action: {
//                    
//                }, label: {
//                    Text("Get Started")
//                        .foregroundColor(.white)
//                        .font(.custom("Vazir", size: 14))
//                })
//                .frame(width: 200, height: 50, alignment: .center)
//                .background(Color.green_600)
//                .cornerRadius(25)

            }
        }
        .navigationBarHidden(true)
        
    }
}
