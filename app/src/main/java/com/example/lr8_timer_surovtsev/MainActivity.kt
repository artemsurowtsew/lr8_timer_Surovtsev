 package com.example.lr8_timer_surovtsev

 import android.os.Bundle
 import android.widget.Button
 import android.widget.EditText
 import android.widget.TextView
 import android.widget.Toast
 import androidx.activity.viewModels
 import androidx.appcompat.app.AppCompatActivity
 import androidx.lifecycle.Observer

 class MainActivity : AppCompatActivity() {

     private lateinit var editTextDuration: EditText
     private lateinit var textViewTimer: TextView
     private lateinit var buttonStart: Button
     private lateinit var buttonStop: Button

     private val timerViewModel: TimerViewModel by viewModels()

     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_main)

         editTextDuration = findViewById(R.id.editTextDuration)
         textViewTimer = findViewById(R.id.textViewTimer)
         buttonStart = findViewById(R.id.buttonStart)
         buttonStop = findViewById(R.id.buttonStop)

         buttonStart.setOnClickListener { startTimer() }
         buttonStop.setOnClickListener { stopTimer() }

         timerViewModel.remainingTime.observe(this, Observer { timeLeft ->
             if (timeLeft > 0) {
                 textViewTimer.text = "${timeLeft / 1000} сек."
             } else {
                 textViewTimer.text = "Час сплив!"
                 Toast.makeText(this, "Таймер завершено", Toast.LENGTH_SHORT).show()
             }
         })
     }

     private fun startTimer() {
         val duration = editTextDuration.text.toString().toLongOrNull() ?: 0
         if (duration > 0) {
             timerViewModel.startTimer(duration * 1000)
         } else {
             Toast.makeText(this, "Введіть час у секундах", Toast.LENGTH_SHORT).show()
         }
     }

     private fun stopTimer() {
         timerViewModel.stopTimer()
     }
 }
