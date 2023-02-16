package com.example.pilottestmandirieko

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import com.example.pilottestmandirieko.databinding.ActivityMainBinding
import com.example.pilottestmandirieko.helper.viewBinding
import com.example.pilottestmandirieko.view.GenreFragment
import com.example.pilottestmandirieko.view.base.BaseActivity

class MainActivity : BaseActivity() {

    private val binding by viewBinding<ActivityMainBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addFragment(GenreFragment.newInstance())

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (supportFragmentManager.backStackEntryCount > 0) {
                    supportFragmentManager.popBackStack()
                } else {
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }
}