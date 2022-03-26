package com.izo.apigithubuserapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.CompoundButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.switchmaterial.SwitchMaterial
import com.izo.apigithubuserapp.R
import com.izo.apigithubuserapp.databinding.ActivitySettingThemeBinding
import com.izo.apigithubuserapp.viewmodel.MainViewModel
import com.izo.apigithubuserapp.viewmodel.SettingThemeViewModel
import com.izo.apigithubuserapp.viewmodel.ViewModelFactory
import kotlinx.coroutines.flow.collect

class SettingThemeActivity : AppCompatActivity() {

    private lateinit var settingThemeBinding: ActivitySettingThemeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingThemeBinding = ActivitySettingThemeBinding.inflate(layoutInflater)
        setContentView(settingThemeBinding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val settingThemeViewModel: SettingThemeViewModel by viewModels {
            factory
        }

        val switchTheme = settingThemeBinding.switchTheme

        settingThemeViewModel.getThemeSetting().observe(this) {isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            settingThemeViewModel.saveThemeSetting(isChecked)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}