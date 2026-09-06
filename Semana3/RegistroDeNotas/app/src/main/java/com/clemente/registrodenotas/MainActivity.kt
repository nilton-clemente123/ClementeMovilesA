package com.clemente.registrodenotas


import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.clemente.registrodenotas.ui.theme.RegistroDeNotasTheme
import java.util.Locale
import kotlin.math.roundToInt


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            RegistroDeNotasTheme {

                pantallaPrincipal()

            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun pantallaPrincipal() {

    var notaFundamentos by remember {
        mutableFloatStateOf(0f)
    }
    var notaPoo by remember {
        mutableFloatStateOf(0f)
    }
    var notaMoviles by remember {
        mutableFloatStateOf(0f)
    }
    var notaBaseDatos by remember {
        mutableFloatStateOf(0f)
    }


    var redondearPromedio by remember {
        mutableStateOf(false)
    }

    var confirmarNotas by remember {
        mutableStateOf(false)
    }

    var promedioCalculado by remember {
        mutableStateOf(false)
    }

    val promedioPonderado =
        notaFundamentos * 0.20 +
                notaPoo * 0.25 +
                notaMoviles * 0.30 +
                notaBaseDatos * 0.25


    val promedioFinal: Double =
        if (redondearPromedio) {
            promedioPonderado.roundToInt().toDouble()
        } else {
            promedioPonderado
        }

    val observacion = when {
        promedioFinal >= 17 -> "EXCELENTE"
        promedioFinal >= 13 -> "APROBADO"
        promedioFinal >= 10 -> "EN RECUPERACIÓN"
        else -> "DESAPROBADO"
    }






    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Registro de Notas")
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {

            Text(
                text = "Notas del ciclo",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            cursoSlider(
                texto = "Fundamentos de Programación",
                peso = 20,
                nota = notaFundamentos,
                onNotaChange = {
                    notaFundamentos = it
                }
            )

            cursoSlider(
                texto = "Programación Orientada a Objetos",
                peso = 25,
                nota = notaPoo,
                onNotaChange = {
                    notaPoo = it
                }
            )

            cursoSlider(
                texto = "Programación en Móviles",
                peso = 30,
                nota = notaMoviles,
                onNotaChange = {
                    notaMoviles = it
                }
            )

            cursoSlider(
                texto = "Base de Datos",
                peso = 25,
                nota = notaBaseDatos,
                onNotaChange = {
                    notaBaseDatos = it
                }
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text("Redondear promedio final")

                Switch(
                    checked = redondearPromedio,
                    onCheckedChange = {
                        redondearPromedio = it
                    }
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Checkbox(
                    checked = confirmarNotas,
                    onCheckedChange = {
                        confirmarNotas = it
                    }
                )

                Text("Confirmo que las notas son correctas")
            }


            Button(
                onClick = {
                    promedioCalculado = true
                },
                enabled = confirmarNotas,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("CALCULAR PROMEDIO")
            }

            if (promedioCalculado) {

                resultados(
                    promedioPonderado = promedioPonderado,
                    promedioFinal = promedioFinal,
                    redondeado = redondearPromedio,
                    observacion = observacion
                )
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun PantallaPreview() {
    RegistroDeNotasTheme {
        pantallaPrincipal()
    }
}


@Composable
fun cursoSlider(
    texto: String,
    peso: Int,
    nota: Float,
    onNotaChange: (Float) -> Unit
) {

    Column(
        modifier = Modifier.padding(vertical = 7.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = "$texto ($peso%)",
                fontWeight = FontWeight.Medium
            )

            Text(
                text = nota.toInt().toString(),
                fontWeight = FontWeight.Bold
            )
        }

        Slider(
            value = nota,
            onValueChange = onNotaChange,
            valueRange = 0f..20f,
            steps = 19,
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Composable
fun resultados(
    promedioPonderado: Double,
    promedioFinal: Double,
    redondeado: Boolean,
    observacion: String
) {

    Column {

        Text(
            text = "Promedio ponderado: ${
                String.format(
                    Locale.US,
                    "%.2f",
                    promedioPonderado
                )
            }"
        )

        Text(
            text = "Promedio final: ${
                if (redondeado) {
                    promedioFinal.toInt()
                } else {
                    String.format(
                        Locale.US,
                        "%.2f",
                        promedioFinal
                    )
                }
            }"
        )

        Text(
            text = observacion
        )
    }
}
