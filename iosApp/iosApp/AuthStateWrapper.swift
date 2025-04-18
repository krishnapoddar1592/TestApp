//
//  AuthStateWrapper.swift
//  iosApp
//
//  Created by Krishna Poddar on 17/04/25.
//  Copyright © 2025 orgName. All rights reserved.
//


import SwiftUI
import shared
import AuthenticationServices

enum AuthStateWrapper {
    case initial
    case loading
    case authenticated(user: User)
    case error(message: String)
}

class AuthViewModelWrapper: NSObject, ObservableObject, ASAuthorizationControllerDelegate, ASAuthorizationControllerPresentationContextProviding {
    private let viewModel: AuthViewModel
    
    @Published var authState: AuthStateWrapper = .initial
    var currentNonce: String?
    
    var isLoading: Bool {
        if case .loading = authState {
            return true
        }
        return false
    }
    
    override init() {
        self.viewModel = KoinHelper().getAuthViewModel()
        super.init()
        
        observeAuthState()
    }
    
    private func observeAuthState() {
        viewModel.authState.collect(collector: { state in
            DispatchQueue.main.async {
                if let state = state as? AuthState {
                    switch state {
                    case is AuthState.Initial:
                        self.authState = .initial
                    case is AuthState.Loading:
                        self.authState = .loading
                    case let authenticated as AuthState.Authenticated:
                        self.authState = .authenticated(user: authenticated.user)
                    case let error as AuthState.Error:
                        self.authState = .error(message: error.message)
                    default:
                        break
                    }
                }
            }
        }, completionHandler: { error in
            print("Error collecting auth state: \(error?.localizedDescription ?? "unknown error")")
        })
    }
    
    func loginWithEmail(email: String, password: String) {
        viewModel.loginWithEmail(email: email, password: password)
    }
    
    func loginWithGoogle(idToken: String) {
        viewModel.loginWithGoogle(idToken: idToken)
    }
    
    func loginWithApple(idToken: String, nonce: String?) {
        viewModel.loginWithApple(idToken: idToken, nonce: nonce)
    }
    
    // MARK: - ASAuthorizationControllerDelegate
    
    func authorizationController(controller: ASAuthorizationController, didCompleteWithAuthorization authorization: ASAuthorization) {
        if let appleIDCredential = authorization.credential as? ASAuthorizationAppleIDCredential {
            guard let nonce = currentNonce else {
                print("Invalid state: A login callback was received, but no login request was sent.")
                return
            }
            
            guard let appleIDToken = appleIDCredential.identityToken,
                  let idTokenString = String(data: appleIDToken, encoding: .utf8) else {
                print("Unable to fetch identity token")
                return
            }
            
            // Pass the token to your shared ViewModel
            loginWithApple(idToken: idTokenString, nonce: nonce)
        }
    }
    
    func authorizationController(controller: ASAuthorizationController, didCompleteWithError error: Error) {
        print("Apple Sign-In Error: \(error.localizedDescription)")
        self.authState = .error(message: "Apple Sign-In failed: \(error.localizedDescription)")
    }
    
    // MARK: - ASAuthorizationControllerPresentationContextProviding
    
    func presentationAnchor(for controller: ASAuthorizationController) -> ASPresentationAnchor {
        let scenes = UIApplication.shared.connectedScenes
        let windowScene = scenes.first as? UIWindowScene
        return windowScene!.windows.first!
    }
}
