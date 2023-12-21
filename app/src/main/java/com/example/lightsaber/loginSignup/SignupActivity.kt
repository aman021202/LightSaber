package com.example.lightsaber.loginSignup


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lightsaber.UserData
import com.example.lightsaber.databinding.ActivitySignupBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("User")

        binding.signupButton.setOnClickListener{
            val signup = binding.signupUsername.text.toString()
            val signupPassword = binding.signupPassword.text.toString()
            if (signup.isNotEmpty() && signupPassword.isNotEmpty()){
                signupUser(signup, signupPassword)
            }else{
                Toast.makeText(this@SignupActivity, "All fields are mandatory",Toast.LENGTH_SHORT).show()

            }
        }

        binding.loginRedirect.setOnClickListener {
            startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
            finish()
        }

    }
    private fun signupUser(username: String, Password: String){
        databaseReference.orderByChild("Username").equalTo(username).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(datasnapshot: DataSnapshot) {
                if (!datasnapshot.exists()){
                    val id = databaseReference.push().key
                    val userData = UserData(id, username, Password)
                    databaseReference.child(id!!).setValue(userData)
                    Toast.makeText(this@SignupActivity, "SignUp Successfull",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
                    finish()
                }else{
                    Toast.makeText(this@SignupActivity, "User already exits",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(
                    this@SignupActivity,
                    "Database Error: ${databaseError.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
            })

        }

}





