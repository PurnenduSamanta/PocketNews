package com.purnendu.PocketNews.Ui.Activities


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.switchmaterial.SwitchMaterial
import com.purnendu.PocketNews.PocketNewsApplication
import com.purnendu.PocketNews.R
import com.purnendu.PocketNews.Ui.ViewModel.SettingViewModel
import com.purnendu.PocketNews.Ui.ViewModel.ViewModelFactory.SettingViewModelFactory
import com.purnendu.PocketNews.Utility
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SettingActivity : AppCompatActivity() {
    private lateinit var spinner: Spinner
    private lateinit var cb: Button
    private lateinit var cd: Button
    private lateinit var sb: SwitchMaterial
    private lateinit var javascriptSwitch: SwitchMaterial
    private lateinit var viewModel: SettingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        //Initializing ViewModel
        val repository = (this.application as PocketNewsApplication).repository
        viewModel =
            ViewModelProvider(this, SettingViewModelFactory(repository))[SettingViewModel::class.java]

        initializeView()

        //Spinner Adapter
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            R.layout.support_simple_spinner_dropdown_item,
            viewModel.country
        )
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        spinner.adapter = adapter

        //On Item Selected in Spinner
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                if (!viewModel.isSpinnerFirstCall) {
                    lifecycleScope.launch(Dispatchers.Main) { setCountryCode(position) }
                }
                viewModel.changeSpinnerStatus()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        //Clearing Bookmarks
        cb.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) { clearBookmarks() }

        }
        //Clearing  Database
        cd.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) { clearAllNews() }
        }

        //Night Mode Switch
        val isNightMode = Utility.getNightModeState(this@SettingActivity)
        sb.isChecked = isNightMode

        sb.setOnCheckedChangeListener { _, isChecked ->
            if (viewModel.setNightModeState(isChecked)) {
                val intent = Intent(this@SettingActivity, SplashActivity::class.java)
                intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            } else
                Toast.makeText(this@SettingActivity, "Something wrong happened", Toast.LENGTH_SHORT)
                    .show()
        }

        //For enabling javascript on WebView
        val isJavaScriptEnabled = Utility.getJavaScriptModeState(this@SettingActivity)
        javascriptSwitch.isChecked = isJavaScriptEnabled

        javascriptSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (viewModel.setJavaScriptEnablingState(true))
                    Toast.makeText(this@SettingActivity, "JavaScript Enabled", Toast.LENGTH_SHORT)
                        .show()

            } else {
                if (viewModel.setJavaScriptEnablingState(false))
                    Toast.makeText(this@SettingActivity, "JavaScript Disabled", Toast.LENGTH_SHORT)
                        .show()
            }
        }
    }

    private fun initializeView() {
        spinner = findViewById(R.id.spinner)
        cb = findViewById(R.id.cbb)
        cd = findViewById(R.id.cdb)
        sb = findViewById(R.id.sb)
        javascriptSwitch = findViewById(R.id.javascriptSwitch)
    }

    private suspend fun clearBookmarks() {
        lifecycleScope.launch(Dispatchers.IO) { viewModel.clearBookmarks() }.join()
        Toast.makeText(this@SettingActivity, "Clear Bookmarks", Toast.LENGTH_SHORT).show()
    }

    private suspend fun clearAllNews() {
        lifecycleScope.launch(Dispatchers.IO) { viewModel.clearNews() }.join()
        Toast.makeText(this@SettingActivity, "Clear Database", Toast.LENGTH_SHORT).show()
    }

    private suspend fun setCountryCode(position: Int) {
        if (isSetCountryCode(position))
            Toast.makeText(
                this@SettingActivity,
                viewModel.country[position] + " selected",
                Toast.LENGTH_SHORT
            ).show()
        else
            Toast.makeText(this@SettingActivity, "Something went wrong", Toast.LENGTH_SHORT).show()
    }

    private suspend fun isSetCountryCode(position: Int): Boolean {
        var isSet = false
        lifecycleScope.launch(Dispatchers.IO) {
            isSet = viewModel.setCountryCode(position)
        }.join()
        return isSet
    }
}