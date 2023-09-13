package com.github.flextasker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class AppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
    }
}