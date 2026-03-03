package com.example.carloancalc

import android.os.Bundle
import android.renderscript.Sampler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.carloancalc.ui.theme.CarLoanCalcTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CarLoanCalcTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoanCalculator(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun LoanCalculator(modifier: Modifier = Modifier) {
    var p by remember { mutableIntStateOf(0) }
    var mr by remember { mutableIntStateOf(0) }
    var ar by remember { mutableFloatStateOf(0f) }
    var l by remember { mutableIntStateOf(0) }
    var n by remember { mutableIntStateOf(0) }
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
        Slider(
            value = ar,
            onValueChange = { ar = it },
            modifier = Modifier.padding(30.dp)
        )
//        RadioButton(
//            onClick = { l = 0 },
//            modifier = modifier.
//        )
    }
}
