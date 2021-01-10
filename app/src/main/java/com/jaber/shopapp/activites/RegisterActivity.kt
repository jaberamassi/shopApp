package com.jaber.shopapp.activites

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jaber.shopapp.R
import com.jaber.shopapp.databinding.ActivityRegisterBinding

class RegisterActivity : BaseActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    companion object {
        private const val TAG = "RegisterActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        super.onCreate(savedInstanceState)
        setContentView(view)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)

        binding.tvLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnRegster.setOnClickListener {
            createNewUser()
        }
    }


    private fun validateRegisterDetails(): Boolean {
        return when {
            binding.etFirstName.text.toString().trim().isEmpty() -> {
                showSnakeBar("First name is Empty", true)
                binding.etFirstName.requestFocus()
                binding.etFirstName.error = "First name is Empty"
                false
            }
            binding.etFirstName.text.toString().trim().length < 3 -> {
                showSnakeBar("First name Should be 3 Characters at least", true)
                binding.etFirstName.requestFocus()
                binding.etFirstName.error = "First name Should be 3 Characters at least"
                false
            }
            binding.etLastName.text.toString().trim().isEmpty() -> {
                showSnakeBar("Last name is Empty", true)
                binding.etLastName.requestFocus()
                binding.etLastName.error = "Last name is Empty"
                false
            }
            binding.etLastName.text.toString().trim().length < 3 -> {
                showSnakeBar("Last name  Should be 3 Characters at least", true)
                binding.etLastName.requestFocus()
                binding.etLastName.error = "Last name  Should be 3 Characters at least"
                false
            }
            binding.etEmail.text.toString().trim().isEmpty() -> {
                showSnakeBar("Email is Empty", true)
                binding.etEmail.requestFocus()
                binding.etEmail.error = "Email is Empty"
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString().trim()).matches() -> {
                showSnakeBar("Invalid Email Address", true)
                binding.etEmail.requestFocus()
                binding.etEmail.error = "Invalid Email Address"
                false
            }
            binding.etRegisterPassword.text.toString().trim().length < 6 -> {
                showSnakeBar("Password Should be 6 Characters at least", true)
                binding.etRegisterPassword.requestFocus()
                binding.etRegisterPassword.error = "Password Should be 6 Characters at least"
                false
            }


            binding.etConfirmPassword.text.toString().trim() != binding.etRegisterPassword.text.toString()
                .trim() -> {
                showSnakeBar("Password and confirm password mismatch, please try again", true)
                binding.etConfirmPassword.requestFocus()
                binding.etConfirmPassword.error =
                    "Password and confirm password mismatch, please try again"
                false
            }
            !binding.checkBox.isChecked -> {
                showSnakeBar("You don't agree Terms & Conditions", true)
                false
            }
            else -> {
//                showSnakeBar("Your details are valid",false)
                true
            }
        }
    }

    private fun createNewUser() {
        if (validateRegisterDetails()) {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etRegisterPassword.text.toString().trim()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "create user with Email:success")
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "create user with Email:failure", task.exception)
                        showSnakeBar("create user with Email failed ${task.exception?.message}", true)
                        updateUI(null)
                    }
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                val intent = Intent(this,LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)

            }
        }

        return super.onOptionsItemSelected(item)
    }
}