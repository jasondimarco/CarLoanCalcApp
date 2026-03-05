package com.example.carloancalc

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoanViewModel : ViewModel() {
    var p by mutableIntStateOf(0)
    var ar by mutableFloatStateOf(0f)
    var l by mutableIntStateOf(0)

    val mr: Float
        get() = ar / 12 / 100

    var n by mutableIntStateOf(0)
}
