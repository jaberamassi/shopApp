package com.jaber.shopapp.activites

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jaber.shopapp.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity() {

    companion object {
        private const val TAG = "LoginActivity"
    }

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        super.onCreate(savedInstanceState)
        setContentView(view)

        //Remove Status Bar
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }


        auth = Firebase.auth
        val currentUser = auth.currentUser
        updateUI(currentUser)

        binding.btnLogin.setOnClickListener {
            signIn()
        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        binding.tvForgetPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        auth = Firebase.auth
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun signIn() {
        binding.btnLogin.isEnabled = true
        if (validateLoginDetails()){
            val email = binding.loginEmail.text.toString().trim()
            val password = binding.loginPassword.text.toString().trim()

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    binding.btnLogin.isEnabled = false
                    // Sign in success, update UI with the signed-in user's information
                    Log.i(TAG, "Authentication success")
                    showSnakeBar("Authentication success.", false)
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "Authentication failure ==> ${task.exception?.message}")
                    showSnakeBar("Authentication failed ${task.exception?.message.toString()}", true)
                }
            }
        }

    }

    private fun validateLoginDetails():Boolean {
        return  when{
            binding.loginEmail.text.toString().trim().isBlank() -> {
                showSnakeBar("Email Empty", true)
                false
            }
            binding.loginPassword.text.toString().trim().isBlank() -> {
                showSnakeBar("Password Empty", true)
                false
            }
            else ->{
//                showSnakeBar("Your details is valid", false)
                true
            }
        }

    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser == null) {
            return
        } else {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
}