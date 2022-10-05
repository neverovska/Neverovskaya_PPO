package com.example.lr1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

private const val TEXTVIEW_STATE_KEY = "TEXTVIEW_STATE_KEY"

class MainActivity : AppCompatActivity(), OnSelectedButtonListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onButtonSelected(buttonIndex: Int) {
        // подключаем FragmentManager
        val fragmentManager = supportFragmentManager

        // Получаем ссылку на второй фрагмент по ID
        val fragmentValue = fragmentManager.findFragmentById(R.id.value_fragment) as ValuesFragment?
        fragmentValue?.setFromText(buttonIndex)
    }


}

