// File: iosApp/iosApp/iosApp.swift
import SwiftUI
import Firebase

@main
struct iosApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
