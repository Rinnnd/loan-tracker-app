package com.loantracker.loantracker
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.loantracker.loantracker.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var sharedPreferences: SharedPreferences
    companion object {
        const val PREF_NAME = "loan_prefs"
        const val KEY_CUTOFF_DAY = "cutoff_day"
        const val DEFAULT_CUTOFF_DAY = 1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_summary,
                R.id.navigation_loans,
                R.id.navigation_settings
            )
        )
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding.navView.setupWithNavController(navController)
        title = "贷款还款记账"
    }
    fun getCutoffDay(): Int {
        return sharedPreferences.getInt(KEY_CUTOFF_DAY, DEFAULT_CUTOFF_DAY)
    }
}