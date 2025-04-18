//
//  AppDelegate.swift
//  iosApp
//
//  Created by Krishna Poddar on 17/04/25.
//  Copyright © 2025 orgName. All rights reserved.
//


import UIKit
import Firebase
import GoogleSignIn
import shared

class AppDelegate: NSObject, UIApplicationDelegate {
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]? = nil) -> Bool {
        // Configure Firebase
        FirebaseApp.configure()
        
        // Initialize Koin
//        KoinHelperKt.doInitKoin()
        
        return true
    }
    
    func application(_ app: UIApplication, open url: URL, options: [UIApplication.OpenURLOptionsKey: Any] = [:]) -> Bool {
        return GIDSignIn.sharedInstance.handle(url)
    }
}
