package com.clemente.lab03registroproducto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat.enableEdgeToEdge
import com.clemente.lab03registroproducto.ui.theme.Lab03RegistroProductoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab03RegistroProductoTheme {
                PantallaRegistro()
            }
        }
    }
}

@Preview
@Composable
fun PantallaRegistro(modifier: Modifier = Modifier) {
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }

    var mostrarResumen by remember { mutableStateOf(false) }

    var errorNombre by remember { mutableStateOf(false) }
    var errorPrecio by remember { mutableStateOf(false) }
    var errorCantidad by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Surface(
                modifier = Modifier.fillMaxWidth().height(80.dp),
                color = Color(0xFF52638F)
            ) {
                Text(
                    text = "Registro de Producto",
                    modifier = Modifier.padding(16.dp).padding(

                        top = 20.dp
                    ),
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    ) { innerPadding ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(innerPadding)
        ) {

            Text(
                text = "Nuevo producto",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Completa los datos y presiona Agregar",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline
            )
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre del producto") },
                modifier = Modifier.fillMaxWidth(),

                isError = errorNombre,

                supportingText = {
                    if (errorNombre) {
                        Text(
                            text = "El nombre no puede quedar vacio",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = precio,
                    onValueChange = { precio = it },
                    label = { Text("Precio(S/)") },
                    modifier = Modifier.weight(1f),

                    isError = errorPrecio,

                    supportingText = {
                        if (errorPrecio) {
                            Text(
                                text = "El precio no puede quedar vacio",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                )
                Spacer(modifier = Modifier.width(16.dp))
                OutlinedTextField(
                    value = cantidad,
                    onValueChange = { cantidad = it },
                    label = { Text("Cantidad(S/)") },
                    modifier = Modifier.weight(1f),

                    isError = errorCantidad,

                    supportingText = {
                        if (errorCantidad) {
                            Text(
                                text = "La cantidad no puede quedar vacia",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        errorNombre = nombre.isBlank()
                        errorPrecio = precio.isBlank()
                        errorCantidad = cantidad.isBlank()

                        if (!errorNombre && !errorPrecio && !errorCantidad) {
                            mostrarResumen = true
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text="AGREGAR PRODUCTO",
                        textAlign = TextAlign.Center)
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {
                        nombre = ""
                        precio = ""
                        cantidad = ""

                        errorNombre = false
                        errorPrecio = false
                        errorCantidad = false

                        mostrarResumen = false
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("LIMPIAR")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            if (mostrarResumen) {
                val precioNum = precio.toDoubleOrNull() ?: 0.0
                val cantidadNum = cantidad.toIntOrNull() ?: 0
                val importe = precioNum*cantidadNum
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(nombre, style = MaterialTheme.typography.titleLarge)
                        Text("Precio: S/ " + String.format("%.2f", precioNum))
                        Text("Cantidad: $cantidadNum")

                        Spacer(modifier=Modifier.height(5.dp))
                        Text(
                            text = "Importe: S/ " + String.format("%.2f", importe),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                    }
                }
                Spacer(modifier=Modifier.height(5.dp))
                Text(text="✓ Producto registrado correctamente",
                    color =Color(0xFF2E7D32))

            }

            else{
                Text(text="Aun no has registrado ningun producto",
                    color=MaterialTheme.colorScheme.onSurfaceVariant)
            }

            Spacer(
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "Desarrollado por Clemente",
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center

            )



        }

    }




}


