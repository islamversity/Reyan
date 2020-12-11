
//  Created by meghdad on 11/15/20.

import nativeShared
import Resolver
import UIKit


struct DatabaseFillerUsecase : Resolving {
    
    func fillDB() {
        
        print("fill DB starts ...\(Date())")

        let config : DatabaseFileConfig = DatabaseFileConfiger()
        
        let useCase = DatabaseFillerUseCaseImpl(db: resolver.resolve(), config: config)
        FlowUtilsKt.wrap(useCase.status()).watch { (status: AnyObject?) in
            print("database status update= \(String(describing: status))")
        }
        
        useCase.needsFilling { (kbool, error) in
            if let doseNeed = kbool {
                if doseNeed as! Bool {
                    print("need to fill DB")
                    useCase.fill { (kunuit, error) in
                        print("fill DB finished ... \(Date())")
                        print(error ?? "")
                    }
                }else{
                    print("DB already filled")
                }
            }
        }
    }
}

class UUIDGenerator {
    
    func generateRandomUUID() -> String {
        UUID().uuidString
    }
}

class DatabaseFileConfiger : DatabaseFileConfig {
    
    func generateRandomUUID() -> String {
        UUID().uuidString
    }
    
    var assetVersion: Int32 = 1

    func arabicTextCalligraphy() -> Calligraphy_ {
        return Calligraphy_(
            rowIndex: 0,
            id: generateRandomUUID(),
            languageCode: "ar",
            name: "simple",
            friendlyName: "عربی-بسیط", code: Calligraphy__(languageCode: "ar", calligraphyName: "simple")
        )
    }
    func englishTextCalligraphy() -> Calligraphy_ {
        return Calligraphy_(
            rowIndex: 0,
            id: generateRandomUUID(),
            languageCode: "en",
            name: "Abdullah Yusuf Ali",
            friendlyName: "English-Yusuf Ali", code: Calligraphy__(languageCode: "en", calligraphyName: "Abdullah Yusuf Ali")
        )
    }
    func farsiTextCalligraphy() -> Calligraphy_ {
        return Calligraphy_(
            rowIndex: 0,
            id: generateRandomUUID(),
            languageCode: "fa",
            name: "Makarem Shirazi",
            friendlyName: "فارسی-مکرام شیرازی", code: Calligraphy__(languageCode: "fa", calligraphyName: "Makarem Shirazi")
        )
    }
    

    init() {
        
        let generator = UUIDGenerator()
    }
    
    func getQuranArabicText() -> KotlinByteArray {
        return convertTextFilesToKotlinByteArray(fileName: "quran-simple-one-line", fileType: "json")
    }
    
    func getQuranEnglishText() -> KotlinByteArray {
        return convertTextFilesToKotlinByteArray(fileName: "quran-en-Abdullah-Yusuf-Ali", fileType: "json")
    }
    
    func getQuranExtraData() -> KotlinByteArray {
        return convertTextFilesToKotlinByteArray(fileName: "quran-data", fileType: "json")
    }
    
    func getQuranFarsiText() -> KotlinByteArray {
        return convertTextFilesToKotlinByteArray(fileName: "quran-farsi-makarem", fileType: "json")
    }
    
    func convertTextFilesToKotlinByteArray(fileName : String, fileType: String) -> KotlinByteArray {

        let bundle = Bundle.main
        let path = bundle.path(forResource: fileName, ofType: fileType)
        
//        let content = try? String(contentsOfFile: path!, encoding: String.Encoding.utf8)
//        print("\(fileName) content = \(String(describing: content))")
        
        let data = try? Data(contentsOf: URL(fileURLWithPath: path!))  // Data(contentsOf: <#T##URL#>, options: .mappedIfSafe)
        
        let swiftByteArray = data!.bytes
        let intArray : [Int8] = swiftByteArray.map { Int8(bitPattern: $0) }
        let kotlinByteArray: KotlinByteArray = KotlinByteArray.init(size: Int32(swiftByteArray.count))
        for (index, value) in intArray.enumerated() {
            kotlinByteArray.set(index: Int32(index), value: value)
        }
        
        return kotlinByteArray
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
