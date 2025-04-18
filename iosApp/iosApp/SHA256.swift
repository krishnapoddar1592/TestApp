//
//  SHA256.swift
//  iosApp
//
//  Created by Krishna Poddar on 17/04/25.
//  Copyright © 2025 orgName. All rights reserved.
//


import Foundation
import CommonCrypto

enum SHA256 {
    static func hash(data: Data) -> Data {
        var hash = [UInt8](repeating: 0, count: Int(CC_SHA256_DIGEST_LENGTH))
        data.withUnsafeBytes { buffer in
            _ = CC_SHA256(buffer.baseAddress, CC_LONG(buffer.count), &hash)
        }
        return Data(hash)
    }
}
