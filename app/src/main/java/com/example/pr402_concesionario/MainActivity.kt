package com.example.pr402_concesionario

import Coche
import Furgoneta
import Moto
import Patinete
import Trailer
import Vehiculo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pr402_concesionario.ui.theme.Brown80
import com.example.pr402_concesionario.ui.theme.Cream80
import com.example.pr402_concesionario.ui.theme.LightBrown80
import com.example.pr402_concesionario.ui.theme.PR402_ConcesionarioTheme

/**
 * Esta es la actividad principal de la aplicación.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PR402_ConcesionarioTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App()
                }
            }
        }
    }
}
/**
 * Función que representa la pantalla principal de la aplicación.
 *
 * @param modifier Modificador para personalizar el diseño de la pantalla.
 */
@Composable
fun App(modifier: Modifier = Modifier) {
    // Caja que contiene toda la pantalla
    Box(
        modifier = modifier
            .background(color = Cream80)
            .fillMaxSize(),
    ) {
        Column {
            // Primer apartado
            Box(
                modifier = modifier
                    .background(color = Brown80)
                    .fillMaxWidth()
                    .height(70.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Imagen del logo
                    Image(
                        painter = painterResource(id = R.drawable.pmdm),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(100.dp)
                            .padding(5.dp)
                    )
                    // Texto con el código del proyecto
                    Text(
                        text = "PR402 - Concesionario",
                        fontSize = 22.sp,
                        style = MaterialTheme.typography.titleLarge.copy(
                            shadow = Shadow(
                                offset = Offset(10f, 10f),
                                blurRadius = 10f,
                                color = Color.Black.copy(alpha = 0.3f)
                            )
                        ),
                        modifier = Modifier.padding(10.dp)
                    )
                }
            } // Termina primer apartado

            // Sección 2
            Box(
                modifier = Modifier
                    .padding(top = 9.dp, bottom = 9.dp, start = 9.dp, end = 9.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(color = LightBrown80)
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .align(Alignment.Center),
                ) {
                    val vehiculos = remember { mutableStateListOf<Vehiculo>() }
                    var dialogoCrearVehiculo by remember { mutableStateOf(false) }
                    var dialogoConsultarVehiculos by remember { mutableStateOf(false) }
                    var dialogoListarVehiculos by remember { mutableStateOf(false) }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,

                    ) {
                        Button(onClick = { dialogoCrearVehiculo = true }) {
                            Text("Crear Vehículo")
                        }
                        Button(onClick = { dialogoConsultarVehiculos = true }) {
                            Text("Consultar Vehículos")
                        }
                        Button(onClick = { dialogoListarVehiculos = true }) {
                            Text("Listado por Nombre")
                        }
                    }

                    if (dialogoCrearVehiculo) {
                        DialogoCrearVehiculo(
                            vehiculos,
                            onDismiss = { dialogoCrearVehiculo = false })
                    }
                    if (dialogoConsultarVehiculos) {
                        DialogoConsultarVehiculos(
                            vehiculos,
                            onDismiss = { dialogoConsultarVehiculos = false })
                    }
                    if (dialogoListarVehiculos) {
                        DialogoListarVehiculos(
                            vehiculos,
                            onDismiss = { dialogoListarVehiculos = false })
                    }
                }


            }
        }
    }
}



/**
 * Función que muestra un diálogo para crear un vehículo.
 *
 * @param vehiculos Lista de vehículos.
 * @param onDismiss Callback cuando se cierra el diálogo.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogoCrearVehiculo(vehiculos: MutableList<Vehiculo>, onDismiss: () -> Unit) {
    val tiposVehiculo = listOf("Coche", "Moto", "Patinete", "Furgoneta", "Trailer")
    var tipoVehiculo by remember { mutableStateOf(tiposVehiculo.first()) }
    var ruedas by remember { mutableStateOf("") }
    var motor by remember { mutableStateOf("") }
    var numAsientos by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("") }
    var modelo by remember { mutableStateOf("") }
    var cargaMaxima by remember { mutableStateOf("") }
    var errorMensaje by remember { mutableStateOf<String?>(null) }

    fun crearVehiculo() {
        try {
            val ruedasInt = ruedas.toIntOrNull() ?: throw IllegalArgumentException("Número de ruedas no válido")
            val numAsientosInt = if (tipoVehiculo != "Patinete") numAsientos.toIntOrNull() ?: throw IllegalArgumentException("Número de asientos no válido") else 0
            val cargaMaximaInt = if (tipoVehiculo == "Furgoneta" || tipoVehiculo == "Trailer") cargaMaxima.toIntOrNull() ?: throw IllegalArgumentException("Carga máxima no válida") else 0

            val vehiculo = when (tipoVehiculo) {
                "Coche" -> Coche(ruedasInt, motor, numAsientosInt, color, modelo)
                "Moto" -> Moto(ruedasInt, motor, numAsientosInt, color, modelo)
                "Patinete" -> Patinete(ruedasInt, motor, color, modelo)
                "Furgoneta" -> Furgoneta(ruedasInt, motor, cargaMaximaInt, color, modelo)
                "Trailer" -> Trailer(ruedasInt, motor, cargaMaximaInt, color, modelo)
                else -> throw IllegalArgumentException("Tipo de vehículo no válido")
            }

            vehiculos.add(vehiculo)
            onDismiss()
        } catch (e: Exception) {
            errorMensaje = e.message ?: "Error desconocido"
        }
    }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Crear Vehículo") },
        text = {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                tiposVehiculo.chunked(2).forEach { rowItems ->
                    Row {
                        rowItems.forEach { item ->
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    RadioButton(
                                        selected = tipoVehiculo == item,
                                        onClick = { tipoVehiculo = item }
                                    )
                                    Text(text = item)
                                }
                            }
                        }
                    }
                }
                TextField(value = ruedas, onValueChange = { ruedas = it }, label = { Text("Número de ruedas") })
                TextField(value = motor, onValueChange = { motor = it }, label = { Text("Tipo de motor") })
                if (tipoVehiculo != "Patinete") {
                    TextField(value = numAsientos, onValueChange = { numAsientos = it }, label = { Text("Número de asientos") })
                }
                TextField(value = color, onValueChange = { color = it }, label = { Text("Color") })
                TextField(value = modelo, onValueChange = { modelo = it }, label = { Text("Modelo") })
                if (tipoVehiculo == "Furgoneta" || tipoVehiculo == "Trailer") {
                    TextField(value = cargaMaxima, onValueChange = { cargaMaxima = it }, label = { Text("Carga máxima") })
                }
                errorMensaje?.let { Text(it, color = Color.Red) }
            }
        },
        confirmButton = {
            Button(onClick = { crearVehiculo() }) {
                Text("Crear")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text("Cancelar")
            }
        }
    )
}


/**
 * Función que muestra un diálogo para consultar la cantidad de vehículos por tipo.
 *
 * @param vehiculos Lista de vehículos.
 * @param onDismiss Callback cuando se cierra el diálogo.
 */
@Composable
fun DialogoConsultarVehiculos(vehiculos: MutableList<Vehiculo>, onDismiss: () -> Unit) {
    val tiposVehiculo = listOf("Coche", "Moto", "Patinete", "Furgoneta", "Trailer")
    var confirmDialog = false // Estado para controlar el diálogo de confirmación

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Consultar Vehículos") },
        text = {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                for (tipo in tiposVehiculo) {
                    val cantidad = vehiculos.count { vehiculo ->
                        when (tipo) {
                            "Coche" -> vehiculo is Coche
                            "Moto" -> vehiculo is Moto
                            "Patinete" -> vehiculo is Patinete
                            "Furgoneta" -> vehiculo is Furgoneta
                            "Trailer" -> vehiculo is Trailer
                            else -> false
                        }
                    }
                    Text(text = "$tipo: $cantidad")
                }
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss()
                }
            ) {
                Text("Cerrar")
            }
        },
        confirmButton = {
            if (confirmDialog) {
                Button(
                    onClick = {
                        // Agregar lógica para la confirmación aquí
                        confirmDialog = false
                    }
                ) {
                    Text("Aceptar") // Puedes personalizar el texto del botón de confirmación aquí
                }
            }
        }
    )
}

/**
 * Función que muestra un diálogo para listar los vehículos.
 *
 * @param vehiculos Lista de vehículos.
 * @param onDismiss Callback cuando se cierra el diálogo.
 */
@Composable
fun DialogoListarVehiculos(vehiculos: MutableList<Vehiculo>, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Listado de Vehículos") },
        text = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(16.dp)
            ) {
                items(vehiculos) { vehiculo ->
                    val tipoVehiculo = obtenerTipoVehiculo(vehiculo)
                    Text(text = "Tipo: $tipoVehiculo")
                    Text(text = "Número de Ruedas: ${vehiculo.ruedas}")
                    Text(text = "Motor: ${vehiculo.motor}")
                    Text(text = "Color: ${vehiculo.color}")
                    Text(text = "Modelo: ${vehiculo.modelo}")

                    // Verificar si el vehículo es un Coche o una Moto para mostrar el número de asientos
                    if (vehiculo is Coche || vehiculo is Moto) {
                        Text(text = "Número de Asientos: ${vehiculo.numAsientos}")
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss()
                }
            ) {
                Text("Cerrar")
            }
        },
        confirmButton = {
        }
    )
}


/**
 * Función para obtener el tipo de vehículo como cadena.
 *
 * @param vehiculo Vehículo del que se desea obtener el tipo.
 * @return Cadena que representa el tipo de vehículo.
 */
// Función para obtener el tipo de vehículo como cadena
private fun obtenerTipoVehiculo(vehiculo: Vehiculo): String {
    return when (vehiculo) {
        is Coche -> "Coche"
        is Moto -> "Moto"
        is Patinete -> "Patinete"
        is Furgoneta -> "Furgoneta"
        is Trailer -> "Trailer"
        else -> "Desconocido"
    }
}
