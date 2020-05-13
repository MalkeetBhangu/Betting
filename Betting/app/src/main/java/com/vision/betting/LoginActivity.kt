package com.vision.betting

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)

        tvSignUp.setOnClickListener {
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
           if (edtEmail.text.isEmpty()){
               Utils.showOkAlert(this,resources.getString(R.string.please_fill_email))
           }else if(edtPassword.text.isEmpty()){
               Utils.showOkAlert(this,resources.getString(R.string.please_fill_password))
           }else{
               progressDialog.setMessage(resources.getString(R.string.please_wait))
               progressDialog.show()
               progressDialog.setCancelable(false)
               login(edtEmail.text.toString(),edtPassword.text.toString())
           }
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth!!.currentUser
        if (currentUser != null){
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun login(email:String,password:String){
        mAuth?.signInWithEmailAndPassword(email, password)
            ?.addOnCompleteListener(this,
                OnCompleteListener<AuthResult?> { task ->
                    if (task.isSuccessful) {
                        progressDialog.dismiss()
                        finish()
                        // Sign in success, update UI with the signed-in user's information
                        val user: FirebaseUser? = mAuth?.getCurrentUser()


                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)

                    } else {
                        // If sign in fails, display a message to the user.
                        progressDialog.dismiss()
                        Utils.showOkAlert(this,resources.getString(R.string.email_password_incorrect))

                    }

                })
    }


}
