package net.codebot.mobile
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import net.codebot.mobile.model.Model

class AuthViewModel(private val model: Model) {

    private val firebaseAuthenticator: FirebaseAuth = FirebaseAuth.getInstance()

    private val _currentAuthState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _currentAuthState

    init {
        evaluateAuthStatus()
    }

    private fun evaluateAuthStatus() {
        _currentAuthState.value = if (firebaseAuthenticator.currentUser != null) {
            AuthState.Authenticated
        } else {
            AuthState.Unauthenticated
        }
    }

    fun login(userEmail: String, userPassword: String, switchTab: () -> Unit) {
        if (userEmail.isBlank() || userPassword.isBlank()) {
            _currentAuthState.value = AuthState.Error("Please provide both email and password")
            return
        }

        _currentAuthState.value = AuthState.Loading

        firebaseAuthenticator.signInWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener { task ->
                _currentAuthState.value = when {
                    task.isSuccessful -> AuthState.Authenticated
                    else -> AuthState.Error(task.exception?.message ?: "An error occurred")
                }

                if (task.isSuccessful) {
                    println(task.result)
                    switchTab()
                }
            }
    }

    fun signup(userEmail: String, userPassword: String, userName: String, addUser: (String) -> Unit, switchTab: () -> Unit) {
        if (userEmail.isBlank() || userPassword.isBlank() || userName.isBlank()) {
            _currentAuthState.value = AuthState.Error("All fields are required")
            return
        }

        val db = FirebaseFirestore.getInstance()

        _currentAuthState.value = AuthState.Loading
        println("state set to loading")

        firebaseAuthenticator.createUserWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener { task ->
                _currentAuthState.value = if (task.isSuccessful) {
                    println("set state to authenticated")
                    AuthState.Authenticated
                } else {
                    AuthState.Error(task.exception?.message ?: "Signup failed")
                }
                if (task.isSuccessful) {

                    val userId = firebaseAuthenticator.currentUser?.uid
                    if (userId != null) {
                        addUserToFirestore(db, userId, userName)
                        addUser(userName)
                    } else {
                        println("Error: User ID is null")
                    }
                    switchTab()
                }
            }
    }

    private fun addUserToFirestore(
        firestore: FirebaseFirestore,
        userId: String,
        username: String
    ) {
        val userData = mapOf(
            "userID" to userId,
            "username" to username,
            "rankings" to 0,
            "createdAt" to System.currentTimeMillis()
        )

        firestore.collection("users").document(userId).set(userData)
            .addOnSuccessListener {
                println("User document created in Firestore!")
            }
            .addOnFailureListener { e ->
                println("Error creating user document in Firestore: ${e.message}")
            }
    }

    fun logoutUser() {
        firebaseAuthenticator.signOut()
        _currentAuthState.value = AuthState.Unauthenticated
    }

    fun signedIn(): Boolean {
        return authState.value is AuthState.Authenticated
    }

    fun getUserId(): String {
        val user = firebaseAuthenticator.currentUser
        if (user != null) {
            return user.email ?: ""
        } else {
            return ""
        }
    }

    fun getUserIdd(): String {
        val user = firebaseAuthenticator.currentUser?.uid
        return user.toString()
    }
}

sealed class AuthState {
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val errorMessage: String) : AuthState()
}
