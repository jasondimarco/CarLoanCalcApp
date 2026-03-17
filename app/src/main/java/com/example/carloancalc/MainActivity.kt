package com.example.carloancalc

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.carloancalc.ui.theme.CarLoanCalcTheme
import kotlin.math.pow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CarLoanCalcTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CarLoanScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun LoanPortrait(modifier: Modifier = Modifier, loanViewModel: LoanViewModel) {
    Column(
        modifier = modifier.fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            "Car Loan Calculator",
            fontSize = 50.sp,
            lineHeight = 50.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
        Image(
            painter = painterResource(id = R.drawable.windstar),
            contentDescription = "hotRod",
            modifier = Modifier.fillMaxWidth().size(300.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                fontSize = 30.sp,
                text = "Purchase Price: $"
            )
            TextField(
                value = loanViewModel.p,
                onValueChange = { loanViewModel.p = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                fontSize = 30.sp,
                text = "Down Payment: $"
            )
            TextField(
                value = loanViewModel.dp,
                onValueChange = { loanViewModel.dp = it },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
        }
        Text(
            "Annual Interest Rate",
            fontSize = 24.sp,
        )
        Slider(
            value = loanViewModel.ar.toFloat(),
            onValueChange = { loanViewModel.ar = it },
            valueRange = 0f..20f,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Text(
            text = String.format("%.1f%%", loanViewModel.ar),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Column {
            val options = listOf(3, 4, 5, 6)
            options.forEach { years ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    RadioButton(
                        selected = loanViewModel.ll == years,
                        onClick = { loanViewModel.ll = years }
                    )
                    Text(
                        text = "$years Years",
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
        Button(
            onClick = {
                loanViewModel.mp = Calculate(loanViewModel.p, loanViewModel.dp, loanViewModel.ar, loanViewModel.ll)
            },
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Text("Calculate")
        }
        Text(
            text = "Monthly Payment: $${loanViewModel.mp}"
        )
    }
}

@Composable
fun CarLoanScreen(modifier: Modifier = Modifier, loanViewModel: LoanViewModel = viewModel()) {
    if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) {
        LoanPortrait(modifier, loanViewModel)
    } else {
        LoanLandscape(modifier, loanViewModel)
    }
}


@Composable
fun LoanLandscape(modifier: Modifier = Modifier, loanViewModel: LoanViewModel) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Landscape View", fontSize = 24.sp)
        Text("Interest Rate: ${String.format("%.1f%%", loanViewModel.ar)}", fontSize = 20.sp)
    }
}



fun Calculate(p: Int, dp: Int, ar: Double, ll: Int): Double {
    val l = p - dp
    if (l <= 0) return 0.0
    val n = ll * 12
    val mr = (ar/100)/12
    if (mr == 0.0) return l / n.toDouble()
    return mr * l / (1 - (1 + mr).pow(-n.toDouble()))
}