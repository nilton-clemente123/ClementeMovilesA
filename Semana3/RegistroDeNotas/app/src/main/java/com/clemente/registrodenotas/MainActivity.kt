package com.clemente.registrodenotas

import android.os.Bundle
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

    val colorObservacion = when {
        promedioFinal >= 17 -> Color(0xFF2F5734)
        promedioFinal >= 13 -> Color(0xFFA5D6A7)
        promedioFinal >= 10 -> Color(0xFFFFE0B2)
        else -> Color(0xFFFFCDD2)
    }

    val colorTextoObservacion = when {
        promedioFinal >= 17 -> Color(0xFFFFFFFF)
        promedioFinal >= 13 -> Color(0xFF2E7D32)
        promedioFinal >= 10 -> Color(0xFFE67700)
        else -> Color(0xFFB71C1C)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Registro de Notas")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        },

        bottomBar = {
            Text(
                text = "Desarrollado por: Javier Clemente Guzmán",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall
            )
        }

    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFD7D0EF),
                            Color.White
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(
                        rememberScrollState()
                    )
            ) {

                Text(
                    text = "Notas del ciclo",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Deslizar para asignar cada nota (0 a 20)",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF7E7E7E)
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                cursoSlider(
                    texto = "Fundamentos de Programación",
                    peso = 20,
                    nota = notaFundamentos,
                    onNotaChange = {
                        notaFundamentos = it
                        promedioCalculado = false
                    }
                )

                cursoSlider(
                    texto = "Programación Orientada a Objetos",
                    peso = 25,
                    nota = notaPoo,
                    onNotaChange = {
                        notaPoo = it
                        promedioCalculado = false
                    }
                )

                cursoSlider(
                    texto = "Programación en Móviles",
                    peso = 30,
                    nota = notaMoviles,
                    onNotaChange = {
                        notaMoviles = it
                        promedioCalculado = false
                    }
                )

                cursoSlider(
                    texto = "Base de Datos",
                    peso = 25,
                    nota = notaBaseDatos,
                    onNotaChange = {
                        notaBaseDatos = it
                        promedioCalculado = false
                    }
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = "Redondear promedio final"
                    )

                    Switch(
                        checked = redondearPromedio,
                        onCheckedChange = {
                            redondearPromedio = it
                            promedioCalculado = false
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

                    Text(
                        text = "Confirmo que las notas son correctas"
                    )
                }

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Button(
                    onClick = {
                        promedioCalculado = true
                    },
                    enabled = confirmarNotas,
                    modifier = Modifier.fillMaxWidth()

                ) {
                    Text(
                        text = "CALCULAR PROMEDIO"
                    )
                }

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                if (promedioCalculado) {

                    resultados(
                        promedioPonderado = promedioPonderado,
                        promedioFinal = promedioFinal,
                        redondeado = redondearPromedio,
                        observacion = observacion,
                        colorObservacion = colorObservacion,
                        colorTextoObservacion = colorTextoObservacion
                    )

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    Text(
                        text = "✓ Promedio calculado correctamente",
                        color = Color(0xFF2E7D32),
                        fontWeight = FontWeight.Bold
                    )

                } else {

                    Text(
                        text = "Asigna las notas y confirma para calcular",
                        color = Color.Gray
                    )
                }

                Spacer(
                    modifier = Modifier.height(30.dp)
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun cursoSlider(
    texto: String,
    peso: Int,
    nota: Float,
    onNotaChange: (Float) -> Unit
) {

    Column(
        modifier = Modifier.padding(
            vertical = 7.dp
        )
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
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }

        Slider(
            value = nota,
            onValueChange = {
                onNotaChange(it)
            },
            valueRange = 0f..20f,
            steps = 19,
            modifier = Modifier.fillMaxWidth(),

            track = { sliderState ->
                SliderDefaults.Track(
                    sliderState = sliderState,
                    drawTick = { _, _ ->

                    }
                )
            },

            thumb = {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(
                            color = Color(0xFF655399),
                            shape = CircleShape
                        )
                )
            }
        )
    }
}


@Composable
fun resultados(

    promedioPonderado: Double,
    promedioFinal: Double,
    redondeado: Boolean,
    observacion: String,
    colorObservacion: Color,
    colorTextoObservacion: Color

) {

    Card(
        modifier = Modifier.fillMaxWidth(),

        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),

        border = BorderStroke(
            width = 2.dp,
            color = Color(0xFFD4B5E5)
        )

    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = "Promedio ponderado: ${
                    String.format(
                        Locale.US,
                        "%.2f",
                        promedioPonderado
                    )
                }",

                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = buildAnnotatedString {

                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    ) {

                        append(
                            if (redondeado) {
                                "Promedio final: ${promedioFinal.toInt()} "
                            } else {
                                "Promedio final: " +
                                        String.format(
                                            Locale.US,
                                            "%.2f",
                                            promedioFinal
                                        )
                            }
                        )
                    }

                    if (redondeado) {

                        withStyle(
                            style = SpanStyle(
                                color = Color(0xFF818181),
                                fontSize = 15.sp
                            )
                        ) {
                            append("(redondeado)")
                        }
                    }
                },

                fontSize = 20.sp
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            AssistChip(

                onClick = {},

                label = {
                    Text(
                        text = observacion,
                        color = colorTextoObservacion
                    )
                },

                colors = AssistChipDefaults.assistChipColors(
                    containerColor = colorObservacion
                )
            )
        }
    }

    Spacer(
        Modifier.height(5.dp)
    )

    Text(
        text = " Promedio calculado correctamente",
        color = Color(0xFF0F8C1B),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.fillMaxWidth()
    )
}


@Preview(showBackground = true)
@Composable
fun PantallaPreview() {

    RegistroDeNotasTheme {

        pantallaPrincipal()
    }
}