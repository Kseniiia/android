package com.example.captcha.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import kotlin.random.Random

class CaptchaService : Service() {
    private val words = arrayListOf(
        "progress",
        "truth",
        "zoom meeting",
        "quarantine",
        "hand sanitizer",
        "work at home",
        "difference",
        "transport"
    )
    var current: String = words[Random.nextInt(0, words.size - 1)]

    companion object {
        private var serviceInstance: CaptchaService? = null
        fun getServiceInstance(): CaptchaService {
            if (serviceInstance == null) {
                serviceInstance = CaptchaService()
            }
            return serviceInstance!!
        }

    }

    inner class CaptchaServiceBinder : Binder() {
        val service: CaptchaService
            get() = getServiceInstance()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return CaptchaServiceBinder()
    }

    fun checkWord(entered: String): Boolean {
        return entered == current
    }

    fun nextWord(): String {
        var next: String = ""
        do {
            next = words[Random.nextInt(0, words.size - 1)]
            Log.d("TAG", next)
        } while (next == current)
        current = next
        return current
    }
}