//
//  KotlinInteropTest.swift
//  iosApp
//
//  Created by Krishna Poddar on 18/04/25.
//  Copyright © 2025 orgName. All rights reserved.
//


// File: iosApp/iosApp/KotlinInteropTest.swift
import Foundation
import shared

struct KotlinInteropTest {
    func testVisibility() {
        print("Testing Kotlin interop...")


//        // For classes in the root package
        let test = SwiftVisibilityTest()
//
//        // For classes in packages, use the full name
        let user = User(
            id: "1",
            email: "test@example.com",
            displayName: nil,
            isEmailVerified: false,
            subscriptionType: com.reflect.app.domain.model.SubscriptionType.FREE,
            createdAt: 0,
            lastLoginAt: 0
        )
    }
}
