package com.example.captcha

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.captcha.service.CaptchaService
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var editText: EditText
    private lateinit var textView: TextView
    private var captchaService: CaptchaService? = null
    private var isBound = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.editText)
        textView = findViewById(R.id.textView)

        Intent(this, CaptchaService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.captcha_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.check_menu -> {
                if (isBound) {
                    val string = editText.text.toString().trim().toLowerCase(Locale.ROOT)
                    if (captchaService!!.checkWord(string)) {
                        Toast.makeText(this, R.string.correct_toast, Toast.LENGTH_SHORT).show()
                        textView.text = captchaService!!.nextWord()
                    } else {
                        Toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show()
                    }
                    editText.text.clear()
                }
                true
            }
            R.id.new_word_menu -> {
                if (isBound) {
                    textView.text = captchaService?.nextWord()
                    editText.text.clear()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onStop() {
        super.onStop()
        if (isBound) {
            unbindService(connection)
            isBound = false
        }
    }

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as CaptchaService.CaptchaServiceBinder
            captchaService = binder.service
            isBound = true
            textView.text = captchaService?.current
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            isBound = false
        }
    }
}