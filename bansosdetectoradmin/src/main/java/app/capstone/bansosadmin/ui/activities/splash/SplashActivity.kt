package app.capstone.bansosadmin.ui.activities.splash

import android.content.Intent
import android.os.Bundle
import app.capstone.bansosadmin.databinding.ActivitySplashBinding
import app.capstone.bansosadmin.ui.activities.BaseActivity
import app.capstone.bansosadmin.ui.activities.auth.MainActivity
import app.capstone.bansosadmin.ui.activities.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    private val auth: FirebaseAuth by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CoroutineScope(IO).launch {
            delay(1000)
            initCheck()
        }
    }

    private fun initCheck() {
        if (auth.currentUser != null) moveHome() else moveLogin()
    }

    private fun moveLogin() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun moveHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    override fun getBindings() = ActivitySplashBinding.inflate(layoutInflater)
}