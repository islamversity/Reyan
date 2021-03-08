
//  Created by meghdad on 11/15/20.

import nativeShared
import Resolver
import UIKit


struct IOSDatabaseFiller {
    
    public init(fillerUseCase: DatabaseFillerUseCase) {
        self.fillerUseCase = fillerUseCase
    }
    
    let fillerUseCase : DatabaseFillerUseCase // DatabaseFillerUseCaseImpl(db: resolver.resolve(), config: config)

    func fillDB() {
        print("fill DB starts ...\(Date())")

        fillerUseCase.fill { (kunuit, error) in
            print(error ?? "")
        }
    }
}

extension Data {
    var bytes : [UInt8]{
        return [UInt8](self)
    }
}

//
//extension Data {
//
//    init<T>(fromArray values: [T]) {
//        var values = values
//        self.init(buffer: UnsafeBufferPointer(start: &values, count: values.count))
//    }
//
//    func toArray<T>(type: T.Type) -> [T] {
//        let value = self.withUnsafeBytes {
//            $0.baseAddress?.assumingMemoryBound(to: T.self)
//        }
//        return [T](UnsafeBufferPointer(start: value, count: self.count / MemoryLayout<T>.stride))
//    }
//
//}
