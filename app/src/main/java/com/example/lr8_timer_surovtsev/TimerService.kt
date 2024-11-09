package com.example.lr8_timer_surovtsev

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TimerViewModel : ViewModel() {
    private val _remainingTime = MutableLiveData<Long>()
    val remainingTime: LiveData<Long> = _remainingTime

    private var timerRunning = false
    private var remainingDuration: Long = 0

    fun startTimer(duration: Long) {
        if (timerRunning) return
        remainingDuration = duration
        timerRunning = true

        viewModelScope.launch {
            while (remainingDuration > 0 && timerRunning) {
                _remainingTime.postValue(remainingDuration)
                delay(1000)
                remainingDuration -= 1000
            }
            if (remainingDuration <= 0) {
                _remainingTime.postValue(0)
                timerRunning = false
            }
        }
    }

    fun stopTimer() {
        timerRunning = false
    }
}
