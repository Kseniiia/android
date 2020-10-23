package com.example.task1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        showNumber();
    }

    private fun showNumber() {
        val number = intent.getIntExtra("number", 0);

        textView2.text = number.toString();
    }

    fun onBtnClick(view: View) {
        val number1 = Integer.parseInt(textView2.text.toString());
        val number2 = Integer.parseInt(editTextNumber2.text.toString());

        val intent = Intent();

        intent.putExtra("number", number1 + number2);

        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}