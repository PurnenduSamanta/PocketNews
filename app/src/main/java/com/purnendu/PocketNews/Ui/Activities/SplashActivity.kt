package com.purnendu.PocketNews.Ui.Activities

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.LottieAnimationView
import com.purnendu.PocketNews.PocketNewsApplication
import com.purnendu.PocketNews.R
import com.purnendu.PocketNews.Retrofit.API_KEYS
import com.purnendu.PocketNews.Ui.ViewModel.SplashViewModel
import com.purnendu.PocketNews.Ui.ViewModel.ViewModelFactory.SplashViewModelFactory
import com.purnendu.PocketNews.Utility
import com.trncic.library.DottedProgressBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    private lateinit var viewModel: SplashViewModel
    private lateinit var newsLogo: ImageView
    private lateinit var pocketNews: TextView
    private lateinit var dotProgress:DottedProgressBar
    private lateinit var lottie:LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {

        //Getting repository and country code
        val repository = (this.applicationContext as PocketNewsApplication).repository
        val countryCode = Utility.getSelectedCountryCode(this)

        //Initializing viewModel
        viewModel = ViewModelProvider(
            this,
            SplashViewModelFactory(repository, countryCode, "general", API_KEYS.firstKey))[SplashViewModel::class.java]

        //Checking NightMode is on or not
        val isNightMode = Utility.getNightModeState(this)
        if (isNightMode)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
         else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)



        //Initializing by findViewById
        findingView()

        //Setting Up Animation
        val animationDown = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.move_down
        )
        val animationUp = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.move_up
        )

        //Starting animation

            dotProgress.visibility = View.VISIBLE
            dotProgress.startProgress()
            newsLogo.startAnimation(animationDown)
            pocketNews.startAnimation(animationUp)
            lottie.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {

                //Updating Database
                CoroutineScope(Dispatchers.IO).launch {
                    upToDateNews()
                }
                newsLogo.clearAnimation()
                pocketNews.clearAnimation()
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })
    }

    private fun findingView() {
        newsLogo = findViewById(R.id.newsLogo)
        pocketNews = findViewById(R.id.pocketNews)
        dotProgress=findViewById(R.id.dotProgress)
        lottie = findViewById(R.id.lottieAnimation)
    }

    private suspend fun upToDateNews() {
        lifecycleScope.launch((Dispatchers.IO)) {
            viewModel.getUpToDateNews()
        }.join()
        dotProgress.stopProgress()
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
    }
}