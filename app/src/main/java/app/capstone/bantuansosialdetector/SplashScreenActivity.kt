package app.capstone.bantuansosialdetector

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseUser

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

//        if (auth.currentUser != null) {
//            val user = auth.currentUser
//            userLogged(user)
//        } else {
//            userLogin()
//        }

        userLogin()

    }

    private fun userLogged(currentUser: FirebaseUser?) {
        val nameUser = currentUser?.displayName ?: currentUser?.email!!
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra(MainActivity.USERNAME, nameUser)
        startActivity(intent)
        finish()
    }

    private fun userLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}