package com.vision.betting

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.vision.betting.models.SignUpModel
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUpActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private lateinit var signUpModel: SignUpModel
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        progressDialog = ProgressDialog(this)

        mAuth = FirebaseAuth.getInstance()
        signUpModel = SignUpModel()


        btnSignUp.setOnClickListener {
            if (edtName.text.isEmpty()) {
                Utils.showOkAlert(this, resources.getString(R.string.please_fill_name))
            } else if (edtMobileNo.text.isEmpty()) {
                Utils.showOkAlert(this, resources.getString(R.string.please_fill_mobileNo))
            } else if (edtEmail.text.isEmpty()) {
                Utils.showOkAlert(this, resources.getString(R.string.please_fill_email))
            } else if (edtPassword.text.isEmpty()) {
                Utils.showOkAlert(this, resources.getString(R.string.please_fill_password))
            } else if (edtConfirmPassword.text.isEmpty()) {
                Utils.showOkAlert(this, resources.getString(R.string.please_fill_confirmPassword))
            } else if (edtPassword.text.toString() != edtConfirmPassword.text.toString()) {
                Utils.showOkAlert(
                    this,
                    resources.getString(R.string.password_and_confirm_password_not_match)
                )
            } else {
                progressDialog.setMessage(resources.getString(R.string.please_wait))
                progressDialog.show()
                progressDialog.setCancelable(false)
                authentication(edtEmail.text.toString(), edtPassword.text.toString())
            }
        }
    }

    private fun authentication(email: String, password: String) {
        mAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = mAuth!!.currentUser
                    user?.uid?.let { saveDataInDatabase(it) }
                } else {
                    // If sign in fails, display a message to the user.
                    progressDialog.dismiss()
                    Utils.showOkAlert(this, resources.getString(R.string.authentication_failed))
                }

            }
    }

    private fun saveDataInDatabase(userID: String) {

        signUpModel.userName = edtName.text.toString()
        signUpModel.phoneNumber = edtMobileNo.text.toString()
        signUpModel.userEmail = edtEmail.text.toString()

        val db = FirebaseFirestore.getInstance()


        db.collection("Users").document(userID).set(signUpModel)
            .addOnSuccessListener { documentReference ->
                progressDialog.dismiss()
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP + Intent.FLAG_ACTIVITY_CLEAR_TASK + Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener {
                progressDialog.dismiss()
                Utils.showOkAlert(this,resources.getString(R.string.data_not_save))
            }

    }
}
