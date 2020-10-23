package com.example.task1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_second.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10 && resultCode == Activity.RESULT_OK) {
            val number = data?.getIntExtra("number", 0);

            textView.text = number.toString();
        }
    }

    fun onBtnClick(view: View) {
        val number1 = Integer.parseInt(textView.text.toString());
        val number2 = Integer.parseInt(editTextNumber.text.toString());

        val intent = Intent(this, SecondActivity::class.java);

        intent.putExtra("number", number1 + number2);

        startActivityForResult(intent, 10);
    }
}