package com.purnendu.PocketNews.Activities


import androidx.appcompat.app.AppCompatActivity
import com.purnendu.PocketNews.ViewModel.SingleNewsViewModel
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.os.Bundle
import com.purnendu.PocketNews.R
import androidx.lifecycle.ViewModelProvider
import com.purnendu.PocketNews.ViewModel.ViewModelFactory.SingleNewsViewModelFactory
import android.webkit.WebSettings
import android.os.Build
import com.monstertechno.adblocker.AdBlockerWebView.init
import android.widget.Toast
import android.content.Intent
import android.content.res.Configuration
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebViewClient
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.widget.ProgressBar
import androidx.lifecycle.lifecycleScope
import com.monstertechno.adblocker.AdBlockerWebView
import com.monstertechno.adblocker.util.AdBlocker
import com.purnendu.PocketNews.PocketNewsApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*

class SingleNewsActivity : AppCompatActivity() {
    private lateinit var webview: WebView
    private lateinit var url: String
    private lateinit var headline: String
    private lateinit var progressBar: ProgressBar
    private lateinit var viewModel: SingleNewsViewModel

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        //Initializing ViewModel
        val repository = (applicationContext as PocketNewsApplication).repository
        viewModel = ViewModelProvider(this, SingleNewsViewModelFactory(repository))[SingleNewsViewModel::class.java]

        webview = findViewById(R.id.webview)
        progressBar = findViewById(R.id.progressBar)

        Objects.requireNonNull(supportActionBar)?.setDisplayHomeAsUpEnabled(true)

        //Configured WebView
        val nightModeFlags = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
            val webSettings = webview.settings
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                webSettings.forceDark = WebSettings.FORCE_DARK_ON
            }
        }
        url = intent.getStringExtra("url")!!
        headline = intent.getStringExtra("headline")!!

        try {
            webview.settings.cacheMode = WebSettings.LOAD_NO_CACHE
            webview.setLayerType(View.LAYER_TYPE_HARDWARE, null)
            webview.webViewClient = BrowserHome()

            init(this).initializeWebView(webview)
            webview.loadUrl(url)

            //Enabling JavaScript in WebView
            val sharedPreferences = getSharedPreferences("javaScriptSwitch", MODE_PRIVATE)
            webview.settings.javaScriptEnabled = sharedPreferences.getBoolean("js", false)

        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share1 -> {
                try {
                    val intent = Intent()
                    intent.action = Intent.ACTION_SEND
                    intent.putExtra(Intent.EXTRA_TEXT, url)
                    intent.type = "text/plain/"
                    startActivity(intent)
                }
                catch (e :ActivityNotFoundException)
                {
                    Toast.makeText(this@SingleNewsActivity,"Don't find any Application to perform this operation",Toast.LENGTH_SHORT).show()
                }

            }
            R.id.bookmark -> {
                lifecycleScope.launch(Dispatchers.Main) { insertInBookmark() }
            }
            else -> {
                onBackPressed()
            }
        }
        return true
    }

    private suspend fun insertInBookmark() {
        if (isInsertedInBookmark() == 1)
            Toast.makeText(this, "Bookmarked", Toast.LENGTH_SHORT).show()
        else if (isInsertedInBookmark() == -1)
            Toast.makeText(this, "Already Bookmarked", Toast.LENGTH_SHORT).show()
    }

    private suspend fun isInsertedInBookmark(): Int {
        var isInserted = -1
        lifecycleScope.launch(Dispatchers.IO) {
            isInserted = viewModel.insertBookmark(headline, url)
        }.join()
        return isInserted
    }


    internal inner class BrowserHome : WebViewClient() {
        override fun shouldInterceptRequest(view: WebView, url: String): WebResourceResponse? {
            return if (AdBlockerWebView.blockAds(
                    view,
                    url
                )
            ) AdBlocker.createEmptyResource() else super.shouldInterceptRequest(view, url)
        }

        override fun onPageFinished(view: WebView, url: String) {

            //After loading url progressbar will gone and webView will appear
            progressBar.visibility = View.GONE
            webview.visibility = View.VISIBLE
        }
    }

}