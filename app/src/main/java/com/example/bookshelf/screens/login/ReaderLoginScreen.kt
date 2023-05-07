package com.example.bookshelf.screens.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.bookshelf.R
import com.example.bookshelf.components.ReaderLogo
import com.example.bookshelf.components.emailInput
import com.example.bookshelf.components.passwordInput
import com.example.bookshelf.navigation.ReaderScreens
import java.io.StringReader

@Composable
fun ReaderLoginScreen(navController: NavController , viewModal: LoginScreenViewModal = viewModel()) {
    val showLoginForm = rememberSaveable {
        mutableStateOf(true)
    }
Surface(Modifier.fillMaxSize()) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {
        ReaderLogo()
        if (showLoginForm.value)
        UserForm(false ,false){email , password->
            viewModal.signInWithEmailAndPassWord(email,password){
                navController.navigate(ReaderScreens.ReaderHomeScreen.name)
            }

        }
        else {
            UserForm(loading = false , isCreateAccount = true ){email ,password ->
              viewModal.createUsersWithEmailAndPassWord(email , password){
                  navController.navigate(ReaderScreens.ReaderHomeScreen.name)
              }
            }}

    }
    Spacer(modifier = Modifier.height(5.dp))
    Row(modifier = Modifier.padding(15.dp) , horizontalArrangement = Arrangement.Center , verticalAlignment = Alignment.CenterVertically){
        val text = if (showLoginForm.value) "Sign up" else "Login"
        Text(text = "New User ? ")
        Text(text = text , modifier = Modifier
            .clickable { showLoginForm.value = !showLoginForm.value }
            .padding(5.dp) , fontWeight =  FontWeight.SemiBold , color = MaterialTheme.colors.secondaryVariant)
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
        if (!isCreateAccount) Text(text = stringResource(id = R.string.create_account) , modifier = Modifier.padding(4.dp)) else Text(text = "Welcome")
        Spacer(modifier = Modifier.size(5.dp))
        emailInput(
            modifier = Modifier,
            emailState = email,
            enabled = !loading,
            onAction = KeyboardActions { passwordFocusRequest.requestFocus() })
    }

    passwordInput(
        modifier = Modifier.focusRequester(passwordFocusRequest),
        passwordState = password,
        labelId = "password",
        enabled = !loading,
        passwordVisibility = passwordVisibility,
        onAction = KeyboardActions { if (!valid) return@KeyboardActions onDone(email.value.trim(), password.value.trim()) },
    )
    SubmitButton(textId = if(isCreateAccount) "Create Account" else "Login" , loading = loading , validInputs = valid , context = LocalContext.current ){
        onDone(email.value.trim(), password.value.trim())
        keyboardController?.hide()
    }
}

@Composable
fun SubmitButton(textId: String, loading: Boolean, validInputs: Boolean ,context : Context, onClick : () -> Unit) {

    Button(onClick = {onClick.invoke()
                     } , modifier = Modifier
        .padding(3.dp)
        .fillMaxWidth() , enabled = !loading && validInputs , shape = CircleShape ) {
        if (loading) CircularProgressIndicator(modifier = Modifier.size(25.dp))  else Text(text = textId , modifier = Modifier.padding(5.dp))
    }
}

