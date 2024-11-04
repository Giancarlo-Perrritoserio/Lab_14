package com.proyecto.lab_14

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.proyecto.lab_14.ui.theme.Lab_14Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            //VisibilityAnimationExample()
            //ColorChangeAnimationExample()
            //SizeAndPositionAnimationExample()
            //AnimatedContentExample()
            CombinedAnimationsScreen()

        }
    }
}

@Composable
fun VisibilityAnimationExample() {
    // Variable de estado para controlar la visibilidad
    var isVisible by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        // Botón que cambia la visibilidad al hacer clic
        Button(onClick = { isVisible = !isVisible }) {
            Text(text = if (isVisible) "Ocultar cuadro" else "Mostrar cuadro")
        }

        // Espacio animado que aparece/desaparece
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.Blue)
                    .padding(top = 16.dp)
            )
        }
    }
}


@Composable
fun ColorChangeAnimationExample() {
    // Variable de estado para controlar el color actual
    var isBlue by remember { mutableStateOf(true) }

    // Animación de cambio de color con animateColorAsState
    val backgroundColor by animateColorAsState(
        targetValue = if (isBlue) Color.Blue else Color.Green,
        animationSpec = tween(durationMillis = 1000) // Puedes experimentar con spring() o ajustar los parámetros
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Cuadro con color de fondo animado
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .background(backgroundColor)
        )

        // Botón que alterna el color de fondo al hacer clic
        Button(
            onClick = { isBlue = !isBlue },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "Cambiar color")
        }
    }
}


@Composable
fun SizeAndPositionAnimationExample() {
    // Variables de estado para controlar el tamaño y la posición
    var isExpanded by remember { mutableStateOf(false) }

    // Animación de tamaño
    val boxSize: Dp by animateDpAsState(
        targetValue = if (isExpanded) 200.dp else 100.dp,
        animationSpec = androidx.compose.animation.core.tween(durationMillis = 500)
    )

    // Animación de posición
    val boxOffset: Dp by animateDpAsState(
        targetValue = if (isExpanded) 100.dp else 0.dp,
        animationSpec = androidx.compose.animation.core.tween(durationMillis = 500)
    )

    Column {
        // Cuadro con animación de tamaño y posición
        Box(
            modifier = Modifier
                .offset(x = boxOffset, y = boxOffset) // Mover el cuadro
                .size(boxSize) // Cambiar el tamaño
                .background(Color.Red)
        )

        // Botón que alterna el estado de expansión
        Button(
            onClick = { isExpanded = !isExpanded },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = if (isExpanded) "Reducir y mover" else "Expandir y mover")
        }
    }
}

// Definir la clase enum fuera del Composable
enum class AppState { LOADING, CONTENT, ERROR }

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedContentExample() {
    // Variable de estado para controlar el estado actual
    var currentState by remember { mutableStateOf(AppState.LOADING) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        // Componente AnimatedContent para transiciones entre estados
        AnimatedContent(
            targetState = currentState,
            transitionSpec = {
                fadeIn(animationSpec = androidx.compose.animation.core.tween(500)) with
                        fadeOut(animationSpec = androidx.compose.animation.core.tween(500))
            }
        ) { state ->
            when (state) {
                AppState.LOADING -> Text(text = "Cargando...")
                AppState.CONTENT -> Text(text = "Contenido cargado con éxito.")
                AppState.ERROR -> Text(text = "Error al cargar el contenido.")
            }
        }

        // Botones para cambiar el estado actual
        Button(onClick = { currentState = AppState.LOADING }, modifier = Modifier.padding(top = 16.dp)) {
            Text(text = "Mostrar Cargando")
        }
        Button(onClick = { currentState = AppState.CONTENT }, modifier = Modifier.padding(top = 16.dp)) {
            Text(text = "Mostrar Contenido")
        }
        Button(onClick = { currentState = AppState.ERROR }, modifier = Modifier.padding(top = 16.dp)) {
            Text(text = "Mostrar Error")
        }
    }
}



@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CombinedAnimationsScreen() {
    var isExpanded by remember { mutableStateOf(false) }
    var isVisible by remember { mutableStateOf(true) }
    var isDarkMode by remember { mutableStateOf(false) }

    // Animación de color y tamaño
    val boxColor by animateColorAsState(
        targetValue = if (isExpanded) Color.Blue else Color.Green,
        animationSpec = tween(durationMillis = 1000)
    )

    val boxSize by animateDpAsState(
        targetValue = if (isExpanded) 200.dp else 100.dp,
        animationSpec = tween(durationMillis = 500)
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(if (isDarkMode) Color.Black else Color.White)
            .padding(16.dp)
    ) {
        // Cuadro animado con cambio de tamaño y color
        Box(
            modifier = Modifier
                .size(boxSize)
                .background(boxColor)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para cambiar tamaño y color
        Button(onClick = { isExpanded = !isExpanded }) {
            Text("Cambiar Tamaño y Color")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón que se desplaza y desaparece con AnimatedVisibility
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(animationSpec = tween(500)) + slideInVertically(),
            exit = fadeOut(animationSpec = tween(500)) + slideOutVertically()
        ) {
            Button(onClick = { isVisible = false }) {
                Text("Desplazar y Ocultar")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para alternar visibilidad del botón desplazable
        Button(onClick = { isVisible = !isVisible }) {
            Text("Alternar Visibilidad")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para alternar entre modo claro y oscuro
        Button(onClick = { isDarkMode = !isDarkMode }) {
            Text("Alternar Modo Claro/Oscuro")
        }
    }
}