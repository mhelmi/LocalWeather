package com.github.mhelmi.localweather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.github.mhelmi.localweather.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

  private lateinit var appBarConfiguration: AppBarConfiguration
  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityMainBinding.inflate(layoutInflater).apply {
      setContentView(root)
      setSupportActionBar(toolbar)
      val navController = findNavController(R.id.nav_host_fragment_activity_main)
      // Passing each menu ID as a set of Ids because each
      // menu should be considered as top level destinations.
      appBarConfiguration = AppBarConfiguration(
        setOf(
          R.id.homeFragment, R.id.searchFragment, R.id.favoriteLocationsFragment
        )
      )

      setupActionBarWithNavController(navController, appBarConfiguration)
      contentMain.bottomNav.setupWithNavController(navController)
    }
  }

  override fun onSupportNavigateUp(): Boolean {
    val navController = findNavController(R.id.nav_host_fragment_activity_main)
    return navController.navigateUp(appBarConfiguration)
      || super.onSupportNavigateUp()
  }
}