package app.capstone.bantuansosialdetector

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import app.capstone.bantuansosialdetector.databinding.ActivityMainBinding
import app.capstone.bantuansosialdetector.home.HomeFragmentArgs

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfig: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_home) as NavHostFragment
        navController = navHostFragment.navController
        navController.setGraph(R.navigation.home_navigation, HomeFragmentArgs(intent.extras.toString()).toBundle())
        binding.bottomNavigation.setupWithNavController(navController)
        appBarConfig = AppBarConfiguration.Builder(navController.graph).build()
        binding.toolbar.setupWithNavController(navController, appBarConfig)


    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfig) || super.onSupportNavigateUp()
    }

    companion object {
        const val USERNAME = "username"
    }
}