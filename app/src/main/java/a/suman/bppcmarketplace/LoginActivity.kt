package a.suman.bppcmarketplace

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody

class LoginActivity : AppCompatActivity() {
    lateinit var signInButton: SignInButton
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var token: String
    lateinit var compositeDisposable: CompositeDisposable

    private val RC_SIGN_IN = 1
    val TAG: String = "Login Activity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signInButton = findViewById<SignInButton>(R.id.googleSignInButton)

        // Configure Google Sign In
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))//Add backend Auth id here
                .requestEmail()
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        signInButton.setOnClickListener { signIn() }

    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    @SuppressLint("CheckResult")
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    token = account.idToken.toString()
                    val observable = RetrofitClient.instance!!.api.authWithBackend(token)
                    compositeDisposable.add(observable.subscribeOn(Schedulers.io()).observeOn(
                        AndroidSchedulers.mainThread()
                    )
                        .subscribeWith(object : DisposableObserver<ResponseBody>() {
                            override fun onComplete() {
                                Log.i(TAG, "Successful")
                                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                            }

                            override fun onNext(t: ResponseBody) {
                                Log.i(
                                    TAG,
                                    t.string()
                                )//Print the response to know its structure then alter LoginResponse class accordingly.

                                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

                            }

                            override fun onError(e: Throwable) {
                                Log.i(TAG, e.message.toString())
                                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                            }

                        })
                    )

                }
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                // ...
            }
        }
    }


}
