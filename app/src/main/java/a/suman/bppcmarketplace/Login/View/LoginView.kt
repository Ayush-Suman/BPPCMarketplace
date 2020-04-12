package a.suman.bppcmarketplace.Login.View

import a.suman.bppcmarketplace.R
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Login: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)
        val sign_in_button = findViewById<Button>(R.id.sign_in_button)
    }
}