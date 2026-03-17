package com.example.carloancalc

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoanViewModel : ViewModel() {
    var mp by mutableDoubleStateOf(0.0)
    var ar by mutableDoubleStateOf(0.0)
    var ll by mutableIntStateOf(0)

    var dp by mutableIntStateOf(0)

    var mr by mutableDoubleStateOf(0.0)

    var n by mutableIntStateOf(0)

    var p by mutableIntStateOf(0)
}
