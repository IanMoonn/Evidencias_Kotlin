package com.example.login_cba.pages.login

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.login_cba.R
import com.example.login_cba.components.CreateChannelNotification
import com.example.login_cba.pages.home.SplashScreen
import kotlinx.coroutines.delay
import com.example.login_cba.components.notificacionSencilla
import com.example.login_cba.components.notificacionImagen


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LoginScreen() {
    val showLoginForm = rememberSaveable { mutableStateOf(true) }
    val showSplashScreen = remember { mutableStateOf(true) }

    LaunchedEffect(key1 = showSplashScreen.value) {
        delay(2000) // Puedes ajustar el tiempo de espera según tus necesidades
        showSplashScreen.value = false
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        if (showSplashScreen.value) {
            SplashScreen()
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (showLoginForm.value) {
                    Text(text = "Inicia Sesión")
                    UserForm(isCreateAccount = false) { email, password ->
                        Log.d("TiendaApp", "Inicio sesion con $email y $password")
                    }
                } else {
                    Text(text = "Crear Cuenta Nueva")
                    UserForm(isCreateAccount = true) { email, password ->
                        Log.d("TiendaApp", "Creando cuenta con $email y $password")
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val text1 = if (showLoginForm.value) "¿No tienes cuenta?" else "¿Ya tienes cuenta?"
                    val text2 = if (showLoginForm.value) "Registrate" else "Inicia Sesión"
                    Text(text = text1)
                    Text(
                        text = text2,
                        modifier = Modifier.clickable { showLoginForm.value = !showLoginForm.value }
                            .padding(start = 5.dp),
                        color = Color.Blue
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserForm(
    isCreateAccount: Boolean,
    onDone: (String, String) -> Unit = { _, _ ->}
) {
    val idNotification = 0
    val context: Context = LocalContext.current
    val idChannel: String = stringResource(R.string.CanalTienda)
    val name = stringResource(R.string.CanalTienda)
    val descriptionText = stringResource(R.string.Canal_de_Notificaciones_Tienda_CBA)
    val textShort = "Usted acaba de iniciar sesión en la Tienda CBA"
    val textLong = "Saludos! Usted acaba de registrarse en la tienda CBA " +
            "al usted registrarse podra tener acceso a muchos beneficios. " +
            "Puede hablar con una persona a cargo para poder saber de ellos." +
            "Gracias y hasta otro momento"

    val iconoBig = BitmapFactory.decodeResource(
        context.resources,
        R.drawable.ic_contact_mail_black_48dp
    )

    val imagenBig = BitmapFactory.decodeResource(
        context.resources,
        R.drawable.bg_tienda_cba
    )
    val email = rememberSaveable{
        mutableStateOf("")
    }
    val password = rememberSaveable{
        mutableStateOf("")
    }
    val passwordVisible = rememberSaveable{
        mutableStateOf(false)
    }
    val validState = remember(email.value, password.value){
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit){
        CreateChannelNotification(
            idChannel,
            context,
            name,
            descriptionText
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailImput(
            emailState = email
        )
        PasswordInput(
            passwordState = password,
            labelId = "Password",
            passwordVisible = passwordVisible
        )
        Spacer(
            modifier = Modifier
                .height(15.dp)
        )
        SubmitButtom(
            textId = if (isCreateAccount) "Crear Cuenta" else "Iniciar Sesión",
            inputValid = validState
        ) {
            onDone(email.value.trim(), password.value.trim())
            keyboardController?.hide()

            if (isCreateAccount) {
                notificacionImagen(
                    context,
                    idChannel,
                    idNotification + 2,
                    "Notificación con Imagen",
                    textLong,
                    iconoBig,
                    imagenBig
                )
            } else {
                notificacionSencilla(
                    context,
                    idChannel,
                    idNotification,
                    "Notificación Sencilla",
                    textShort
                )
            }
        }
    }
}

@Composable
fun SubmitButtom(
    textId: String,
    inputValid: Boolean,
    onClic: (isCreateAccount: Boolean)->Unit
){
    Button(
        onClick = { onClic(textId == "Crear Cuenta") },
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        shape = CircleShape,
        enabled = inputValid
    ) {
        Text(
            text = textId,
            modifier = Modifier
                .padding(5.dp)
        )
    }
}

@Composable
fun PasswordInput(
    passwordState: MutableState<String>,
    labelId: String,
    passwordVisible: MutableState<Boolean>
){
    val visualTransformation =
        if (passwordVisible.value)
            VisualTransformation.None
        else
            PasswordVisualTransformation()

    OutlinedTextField(
        value = passwordState.value,
        onValueChange = {passwordState.value = it},
        label = { Text(text = labelId)},
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        modifier = Modifier
            .padding(
                bottom = 10.dp,
                start = 10.dp,
                end = 10.dp
            )
            .fillMaxWidth(),
        visualTransformation = visualTransformation,
        trailingIcon = {
            if (passwordState.value.isNotBlank())
                PasswordVisibleIcon(passwordVisible)
            else null
        }
    )
}


@Composable
fun PasswordVisibleIcon(
    passwordVisible: MutableState<Boolean>
) {
    val image =
        if (passwordVisible.value)
            Icons.Default.VisibilityOff
        else
            Icons.Default.Visibility
    IconButton(onClick = {
        passwordVisible.value = !passwordVisible.value
    }) {
        Icon(
            imageVector = image,
            contentDescription = ""
        )
    }
}

@Composable
fun EmailImput(
    emailState: MutableState<String>,
    labelId: String = "Email"
) {
    InputField(
        valueState = emailState,
        labelId = labelId,
        keyboardType = KeyboardType.Email
    )
}

@Composable
fun InputField(
    valueState: MutableState<String>,
    labelId: String,
    keyboardType: KeyboardType,
    isSingleLine: Boolean = true
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = {valueState.value = it},
        label = { Text(text = labelId)},
        singleLine = isSingleLine,
        modifier = Modifier
            .padding(
                bottom = 10.dp,
                start = 10.dp,
                end = 10.dp
            )
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        )
    )
}
