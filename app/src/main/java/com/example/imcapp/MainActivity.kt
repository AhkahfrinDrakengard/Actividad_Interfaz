package com.example.imcapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IMCCalculatorApp()
        }
    }
}

@Composable
fun IMCCalculatorApp() {
    // Variables de estado para las entradas, el resultado y la imagen a mostrar
    var pesoInput by remember { mutableStateOf("") }
    var alturaInput by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("Resultado") }
    var imageResource by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = pesoInput,
            onValueChange = { pesoInput = it },
            label = { Text("Peso (kg)") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = alturaInput,
            onValueChange = { alturaInput = it },
            label = { Text("Altura (m)") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (pesoInput.isNotEmpty() && alturaInput.isNotEmpty()) {
                    val peso = pesoInput.toDoubleOrNull()
                    val altura = alturaInput.toDoubleOrNull()
                    if (peso != null && altura != null && altura > 0) {
                        val imc = peso / (altura * altura)
                        Log.d("IMCApp", "IMC calculado: $imc")
                        resultado = "Tu IMC es %.2f".format(imc)
                        // Asignamos la imagen según el valor del IMC
                        imageResource = if (imc < 18.5) {
                            R.drawable.insuficiente
                        } else if (imc < 24.0) {
                            R.drawable.saludable
                        } else {
                            R.drawable.sobrepeso
                        }
                    } else {
                        resultado = "Por favor, ingresa valores numéricos válidos."
                        imageResource = null
                        Log.d("IMCApp", "Error de conversión: peso=$peso, altura=$altura")
                    }
                } else {
                    resultado = "Por favor, ingresa ambos valores."
                    imageResource = null
                    Log.d("IMCApp", "Campos vacíos.")
                }
            }
        ) {
            Text("Calcular")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = resultado,
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        // Se muestra la imagen solo si imageResource no es nulo
        if (imageResource != null) {
            Image(
                painter = painterResource(id = imageResource!!),
                contentDescription = "Imagen de resultado",
                modifier = Modifier.size(150.dp)
            )
        }
    }
}


//@Preview(showBackground = true)
//@Composable
/*fun GreetingPreview() {
    IMCappTheme {
        Greeting("Android")
    }
}*/