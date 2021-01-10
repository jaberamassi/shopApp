package com.jaber.shopapp.activites


import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.jaber.shopapp.R

open class BaseActivity : AppCompatActivity() {


    fun showSnakeBar(message: String, errorMessage: Boolean) {
        val snackbar: Snackbar =
                Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)

        if (errorMessage) {
            snackbar.view.setBackgroundColor(
                    ContextCompat.getColor(
                            this,
                            R.color.colorSnackebarError
                    )
            )
        } else {
            snackbar.view.setBackgroundColor(
                    ContextCompat.getColor(
                            this,
                            R.color.coloSnackbarSuccess
                    )
            )
        }

        snackbar.show()
    }

}