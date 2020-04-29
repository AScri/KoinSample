package com.ascri.koinsample.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ascri.koinsample.R
import com.ascri.koinsample.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container,
                        MainFragment()
                    )
                    .commitNow()
        }
    }
}
