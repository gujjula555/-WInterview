package com.example.wiprointerview.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.wiprointerview.R
import com.example.wiprointerview.ui.fragments.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction()
                .add(R.id.container, MainFragment.newInstance()).commitAllowingStateLoss()
    }
}
