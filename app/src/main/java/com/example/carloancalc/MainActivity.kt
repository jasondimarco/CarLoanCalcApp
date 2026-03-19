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
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.carloancalc.ui.theme.CarLoanCalcTheme
import java.util.Locale
import kotlin.math.pow
import kotlin.math.roundToInt

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
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text(
                "Car Loan Calculator",
                fontSize = 40.sp,
                lineHeight = 40.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            Image(
                painter = painterResource(id = R.drawable.windstar),
                contentDescription = "hotRod",
                modifier = Modifier.size(200.dp)
            )
        }
        Row(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                fontSize = 20.sp,
                text = "Purchase Price:          $"
            )
            TextField(
                value = loanViewModel.p.toString(),
                onValueChange = { loanViewModel.p = it.toIntOrNull() ?: 0 },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
        }
        Row(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                fontSize = 20.sp,
                text = "Down Payment:          $"
            )
            TextField(
                value = loanViewModel.dp.toString(),
                onValueChange = { loanViewModel.dp = it.toIntOrNull() ?: 0 },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
        }
        Text(
            "Annual Interest Rate",
            fontSize = 24.sp,
            textDecoration = TextDecoration.Underline
        )
        Slider(
            value = loanViewModel.ar.toFloat(),
            onValueChange = { loanViewModel.ar = it.toDouble() },
            valueRange = 0f..20f,
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        Text(
            text = String.format(Locale.getDefault(), "%.2f%%", loanViewModel.ar),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text("Loan Length", fontSize = 24.sp,
            textDecoration = TextDecoration.Underline
        )
        Row {
            val options = listOf(3, 4)
            options.forEach { years ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 40.dp)
                ) {
                    RadioButton(
                        selected = loanViewModel.ll == years,
                        onClick = { loanViewModel.ll = years }
                    )
                    Text(
                        text = "$years Years",
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }
        Row {
            val options = listOf(5, 6)
            options.forEach { years ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 40.dp)
                ) {
                    RadioButton(
                        selected = loanViewModel.ll == years,
                        onClick = { loanViewModel.ll = years }
                    )
                    Text(
                        text = "$years Years",
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }
        Text(
            text = "Monthly Payment: $${String.format(Locale.getDefault(), "%.2f", loanViewModel.mp)}",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textDecoration = TextDecoration.Underline
        )
        Button(
            onClick = {
                loanViewModel.mp = calculateMonthlyPayment(loanViewModel.p, loanViewModel.dp, loanViewModel.ar, loanViewModel.ll)
            },
            modifier = Modifier.padding(vertical = 6.dp)
        ) {
            Text("Calculate")
        }
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
    Row(
        modifier = modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly

        ) {
            Text(
                "Car Loan Calculator",
                fontSize = 40.sp,
                lineHeight = 40.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    fontSize = 20.sp,
                    text = "Purchase Price:  $"
                )
                TextField(
                    value = loanViewModel.p.toString(),
                    onValueChange = { loanViewModel.p = it.toIntOrNull() ?: 0 },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )
            }
            Row() {
                Text(
                    fontSize = 20.sp,
                    text = "Down Payment:   $"
                )
                TextField(
                    value = loanViewModel.dp.toString(),
                    onValueChange = { loanViewModel.dp = it.toIntOrNull() ?: 0 },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )
            }
            Text("Loan Length", fontSize = 24.sp,
                textDecoration = TextDecoration.Underline
            )
            Row {
                val options = listOf(3, 4)
                options.forEach { years ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 40.dp)
                    ) {
                        RadioButton(
                            selected = loanViewModel.ll == years,
                            onClick = { loanViewModel.ll = years }
                        )
                        Text(
                            text = "$years Years",
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }
            Row {
                val options = listOf(5, 6)
                options.forEach { years ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 40.dp)
                    ) {
                        RadioButton(
                            selected = loanViewModel.ll == years,
                            onClick = { loanViewModel.ll = years }
                        )
                        Text(
                            text = "$years Years",
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }
        }
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.windstar),
                contentDescription = "hotRod",
                modifier = Modifier.size(150.dp)
            )
            Text(
                " Annual Interest Rate: ${
                    String.format(
                        Locale.getDefault(),
                        "%.2f%%",
                        loanViewModel.ar
                    )
                }",
                fontSize = 20.sp
            )
            Slider(
                value = loanViewModel.ar.toFloat(),
                onValueChange = { loanViewModel.ar = it.toDouble() },
                valueRange = 0f..20f,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
            Text(
                text = "Monthly Payment: $${
                    String.format(
                        Locale.getDefault(),
                        "%.2f",
                        loanViewModel.mp
                    )
                }",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            )
            Button(
                onClick = {
                    loanViewModel.mp = calculateMonthlyPayment(
                        loanViewModel.p,
                        loanViewModel.dp,
                        loanViewModel.ar,
                        loanViewModel.ll
                    )
                },
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Text("Calculate")
            }
        }
    }
}

fun calculateMonthlyPayment(p: Int, dp: Int, ar: Double, ll: Int): Double {
    val roundedar = (ar*100).roundToInt()
    val l = p - dp
    if (l <= 0) return 0.00
    val n = ll * 12
    if (n <= 0) return 0.00
    val mr = (((roundedar.toDouble())/100) / 100) / 12
    if (mr == 0.00) return l / n.toDouble()
    return (mr * l) / (1 - (1 + mr).pow(-n.toDouble()))
}
