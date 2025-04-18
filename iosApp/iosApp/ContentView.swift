import SwiftUI
import shared
import Firebase
import GoogleSignIn

struct ContentView: View {
    @State private var email = ""
    @State private var password = ""
    @State private var errorMessage = ""
    @State private var isLoading = false
    
    var body: some View {
        VStack(spacing: 20) {
            Text("Reflect App")
                .font(.largeTitle)
                .padding(.bottom, 30)
            
            TextField("Email", text: $email)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .autocapitalization(.none)
                .keyboardType(.emailAddress)
                .padding(.horizontal)
            
            SecureField("Password", text: $password)
                .textFieldStyle(RoundedBorderTextFieldStyle())
                .padding(.horizontal)
            
            if !errorMessage.isEmpty {
                Text(errorMessage)
                    .foregroundColor(.red)
                    .padding(.top, 8)
            }
            
            Button(action: {
                loginWithEmail()
            }) {
                if isLoading {
                    ProgressView()
                        .progressViewStyle(CircularProgressViewStyle())
                } else {
                    Text("Login")
                        .frame(maxWidth: .infinity)
                }
            }
            .frame(height: 50)
            .background(Color.blue)
            .foregroundColor(.white)
            .cornerRadius(10)
            .padding(.horizontal)
            
            Button(action: {
                loginWithGoogle()
            }) {
                Text("Sign in with Google")
                    .frame(maxWidth: .infinity)
            }
            .frame(height: 50)
            .background(Color.white)
            .foregroundColor(.black)
            .cornerRadius(10)
            .overlay(
                RoundedRectangle(cornerRadius: 10)
                    .stroke(Color.gray, lineWidth: 1)
            )
            .padding(.horizontal)
        }
        .padding()
    }
    
    private func loginWithEmail() {
        isLoading = true
        errorMessage = ""
        
        Auth.auth().signIn(withEmail: email, password: password) { authResult, error in
            isLoading = false
            
            if let error = error {
                errorMessage = error.localizedDescription
                return
            }
            
            // Success! You can now access the user
            if let user = authResult?.user {
                print("Logged in successfully: \(user.email ?? "")")
                // Here you would update your app state or navigate to next screen
            }
        }
    }
    
    private func loginWithGoogle() {
        guard let clientID = FirebaseApp.app()?.options.clientID else { return }
        
        let config = GIDConfiguration(clientID: clientID)
        
        GIDSignIn.sharedInstance.configuration = config
        
        guard let windowScene = UIApplication.shared.connectedScenes.first as? UIWindowScene,
              let rootViewController = windowScene.windows.first?.rootViewController else {
            return
        }
        
        GIDSignIn.sharedInstance.signIn(withPresenting: rootViewController) { result, error in
            if let error = error {
                errorMessage = error.localizedDescription
                return
            }
            
            guard let user = result?.user,
                  let idToken = user.idToken?.tokenString else {
                errorMessage = "Failed to get ID token"
                return
            }
            
            let credential = GoogleAuthProvider.credential(withIDToken: idToken,
                                                           accessToken: user.accessToken.tokenString)
            
            Auth.auth().signIn(with: credential) { authResult, error in
                if let error = error {
                    errorMessage = error.localizedDescription
                    return
                }
                
                // Success! You can now access the user
                if let user = authResult?.user {
                    print("Logged in with Google successfully: \(user.email ?? "")")
                    // Here you would update your app state or navigate to next screen
                }
            }
        }
    }
}
