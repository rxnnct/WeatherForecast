package ru.rxnnct.weatherforecast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.rxnnct.weatherforecast.R
import ru.rxnnct.weatherforecast.view.MainFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.mainLayout, MainFragment.newInstance()).commit()
    }
}