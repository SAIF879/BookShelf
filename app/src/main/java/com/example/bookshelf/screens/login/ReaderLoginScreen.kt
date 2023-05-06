package com.example.bookshelf.screens.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bookshelf.components.ReaderLogo
import com.example.bookshelf.components.emailInput
import com.example.bookshelf.components.passwordInput

@Composable
fun ReaderLoginScreen(navController: NavController) {
Surface(Modifier.fillMaxSize()) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {
        ReaderLogo()
        UserForm(false ,false){email , password->
            Log.d("Form", "ReaderLoginScreen: $email $password ")
        }
    }
    
}
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserForm(
    loading : Boolean = false ,
    isCreateAccount : Boolean = false ,
    onDone : (String , String) -> Unit ={email ,pwd->}
){

    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val passwordVisibility = rememberSaveable { mutableStateOf(false) }
    val passwordFocusRequest = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(email.value, password.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }

    val modifier = Modifier
        .height(250.dp)
        .background(MaterialTheme.colors.background)
        .verticalScroll(rememberScrollState())

    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        emailInput(modifier = Modifier , emailState = email , enabled = !loading , onAction = KeyboardActions{passwordFocusRequest.requestFocus()} )
    }

    passwordInput(
        modifier = Modifier.focusRequester(passwordFocusRequest),
        passwordState = password,
        labelId = "password",
        enabled = !loading,
        passwordVisibility = passwordVisibility,
        onAction = KeyboardActions { if (!valid) return@KeyboardActions onDone(email.value.trim(), password.value.trim()) },
    )
    Spacer(modifier = Modifier.size(5.dp))

    SubmitButton(textId = if(isCreateAccount) "Create Account" else "Login" , loading = loading , validInputs = valid , ){
        onDone(email.value.trim(), password.value.trim())
    }
}

@Composable
fun SubmitButton(textId: String, loading: Boolean, validInputs: Boolean , onClick : () -> Unit) {

    Button(onClick = {onClick} , modifier = Modifier
        .padding(3.dp)
        .fillMaxWidth() , enabled = !loading && validInputs , shape = CircleShape ) {
        if (loading) CircularProgressIndicator(modifier = Modifier.size(25.dp))  else Text(text = textId , modifier = Modifier.padding(5.dp))
    }
}

