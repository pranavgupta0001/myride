package com.circle.prana.myride

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.parse.*

class LogIn : AppCompatActivity() {

    val mAuth = FirebaseAuth.getInstance()
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private val RC_SIGN_IN = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)


        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)



        if (ParseUser.getCurrentUser() == null) {

            ParseAnonymousUtils.logIn { user, e ->
                if (e == null) {

                    Log.i("Info", "Anonymous login successful")

                } else {

                    Log.i("Info", "Anonymous login failed")

                }
            }

        } else {

            if (ParseUser.getCurrentUser().get("riderOrDriver") != null) {


                Log.i("Info", "Redirecting as " + ParseUser.getCurrentUser().get("riderOrDriver"))


            }


        }


    }


    ///sign
    ///////with
    ///////////google


    fun googleSignInn(view: View) {
        val signInIntent = mGoogleSignInClient?.getSignInIntent()
        startActivityForResult(signInIntent, RC_SIGN_IN)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {

        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, "Error: Log in Failed." , Toast.LENGTH_SHORT).show()
                // ...
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information

                        val user = mAuth.currentUser
                        logIn()
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(this, "Log In FAILED.", Toast.LENGTH_SHORT).show()
                    }

                    // ...
                }
    }


    ////parse
    ///////log
    //////////in


    fun makeSwitch(view: View) {
        var textView2 = findViewById<TextView>(R.id.textView2)
        var textView3 = findViewById<TextView>(R.id.textView3)
        var textView4 = findViewById<TextView>(R.id.textView4)
        var textView5 = findViewById<TextView>(R.id.textView5)
        var userTypeSwitch = findViewById<Switch>(R.id.userTypeSwitch)
        textView2.visibility = View.GONE
        textView3.visibility = View.GONE
        textView4.visibility = View.GONE
        textView5.visibility = View.GONE
        userTypeSwitch.visibility = View.INVISIBLE
        var logoImage = findViewById<ImageView>(R.id.logImageView)
        var logTextView = findViewById<TextView>(R.id.logTextView)
        logTextView.visibility = View.VISIBLE
        logoImage.visibility = View.VISIBLE

        Log.i("Switch value", userTypeSwitch.isChecked.toString())

        var userType = "rider"

        if (userTypeSwitch.isChecked) {

            userType = "driver"
            Toast.makeText(this, "Redirecting as Driver", Toast.LENGTH_SHORT).show()


        }


        ParseUser.getCurrentUser().put("riderOrDriver", userType)

        ParseUser.getCurrentUser().saveInBackground { }


        Toast.makeText(this, "Log In To Continue..", Toast.LENGTH_SHORT).show()

    }


    fun logIn() {
        //Move to next Activity

        Toast.makeText(this, "Welcome " + mAuth.currentUser?.displayName, Toast.LENGTH_SHORT)
                .show()

        if (ParseUser.getCurrentUser().getString("riderOrDriver") == "rider") {

            val intent = Intent(applicationContext, RiderActivity::class.java)
            startActivity(intent)

        } else {

            val intent = Intent(applicationContext, DriverRequests::class.java)
            startActivity(intent)


        }

    }
    fun x(view: View){
        logIn()
    }


    override fun onBackPressed() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Exit Your Ride App?")
        alertDialogBuilder
                .setMessage("Click yes to exit!")
                .setCancelable(false)
                .setPositiveButton("Yes"
                ) { dialog, id ->
                    moveTaskToBack(true)
                    android.os.Process.killProcess(android.os.Process.myPid())
                    System.exit(1)
                }

                .setNegativeButton("No", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface, id: Int) {

                        dialog.cancel()
                    }
                })

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
        if (mAuth.currentUser != null) {
            mAuth.signOut()
        }
    }
}
