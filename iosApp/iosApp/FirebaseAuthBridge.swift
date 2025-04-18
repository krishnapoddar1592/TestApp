//
//  FirebaseAuthBridge.swift
//  iosApp
//
//  Created by Krishna Poddar on 17/04/25.
//  Copyright © 2025 orgName. All rights reserved.
//


import Foundation
import Firebase
import shared
import FirebaseAuth
import GoogleSignIn

@objc class FirebaseAuthBridge: NSObject {
    
    // MARK: - Email Authentication
    
    @objc func signInWithEmail(
        email: String,
        password: String,
        completion: @escaping (String?, String?, String?, NSNumber?, String?) -> Void
    ) {
        Auth.auth().signIn(withEmail: email, password: password) { authResult, error in
            if let error = error {
                completion(nil, nil, nil, nil, error.localizedDescription)
                return
            }
            
            guard let user = authResult?.user else {
                completion(nil, nil, nil, nil, "Unknown error")
                return
            }
            
            completion(user.uid, user.email, user.displayName, NSNumber(value: user.isEmailVerified), nil)
        }
    }
    
    // MARK: - Google Authentication
    
    @objc func signInWithGoogle(
        _ idToken: String,
        completion: @escaping (String?, String?, String?, NSNumber?, String?) -> Void
    ) {
        let credential = GoogleAuthProvider.credential(withIDToken: idToken, accessToken: "")
        
        Auth.auth().signIn(with: credential) { authResult, error in
            if let error = error {
                completion(nil, nil, nil, nil, error.localizedDescription)
                return
            }
            
            guard let user = authResult?.user else {
                completion(nil, nil, nil, nil, "Unknown error")
                return
            }
            
            completion(user.uid, user.email, user.displayName, NSNumber(value: user.isEmailVerified), nil)
        }
    }
    
    // MARK: - Apple Authentication
    
    @objc func signInWithApple(
        idToken: String,
        nonce: String?,
        completion: @escaping (String?, String?, String?, NSNumber?, String?) -> Void
    ) {
        let credential = OAuthProvider.credential(
            withProviderID: "apple.com",
            idToken: idToken,
            rawNonce: nonce
        )
        
        Auth.auth().signIn(with: credential) { authResult, error in
            if let error = error {
                completion(nil, nil, nil, nil, error.localizedDescription)
                return
            }
            
            guard let user = authResult?.user else {
                completion(nil, nil, nil, nil, "Unknown error")
                return
            }
            
            completion(user.uid, user.email, user.displayName, NSNumber(value: user.isEmailVerified), nil)
        }
    }
    
    // MARK: - User Registration
    
    @objc func createUser(
        email: String,
        password: String,
        displayName: String?,
        completion: @escaping (String?, String?, String?, NSNumber?, String?) -> Void
    ) {
        Auth.auth().createUser(withEmail: email, password: password) { authResult, error in
            if let error = error {
                completion(nil, nil, nil, nil, error.localizedDescription)
                return
            }
            
            guard let user = authResult?.user else {
                completion(nil, nil, nil, nil, "Unknown error")
                return
            }
            
            // Set display name if provided
            if let displayName = displayName, !displayName.isEmpty {
                let changeRequest = user.createProfileChangeRequest()
                changeRequest.displayName = displayName
                
                changeRequest.commitChanges { error in
                    if let error = error {
                        print("Error updating display name: \(error.localizedDescription)")
                    }
                    // Return user data even if display name update fails
                    completion(user.uid, user.email, displayName, NSNumber(value: user.isEmailVerified), nil)
                }
            } else {
                completion(user.uid, user.email, user.displayName, NSNumber(value: user.isEmailVerified), nil)
            }
        }
    }
    
    // MARK: - User Management
    
    @objc func signOut() {
        do {
            try Auth.auth().signOut()
        } catch {
            print("Error signing out: \(error.localizedDescription)")
        }
    }
    
    @objc func getCurrentUserId() -> String? {
        return Auth.auth().currentUser?.uid
    }
    
    @objc func getCurrentUserEmail() -> String? {
        return Auth.auth().currentUser?.email
    }
    
    @objc func getCurrentUserDisplayName() -> String? {
        return Auth.auth().currentUser?.displayName
    }
    
    @objc func isCurrentUserEmailVerified() -> NSNumber? {
        if let isVerified = Auth.auth().currentUser?.isEmailVerified {
            return NSNumber(value: isVerified)
        }
        return nil
    }
    
    @objc func deleteAccount(completion: @escaping (Bool) -> Void) {
        Auth.auth().currentUser?.delete { error in
            completion(error == nil)
        }
    }
    
    @objc func sendPasswordResetEmail(
        _ email: String,
        completion: @escaping (Bool) -> Void
    ) {
        Auth.auth().sendPasswordReset(withEmail: email) { error in
            completion(error == nil)
        }
    }
}
