package com.example.bookshelf.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginScreenViewModal : ViewModel() {
  //  val loadingState = MutableStateFlow(LoadingState.IDLE)
    private val auth : FirebaseAuth = Firebase.auth

    private val _loading = MutableLiveData(false)
    val loading : LiveData<Boolean> = _loading


    fun createUserWithEmailAndPassWord(){

    }

    fun signInWithEmailAndPassWord(email : String , password : String , home : () -> Unit) = viewModelScope.launch{
        try {
            auth.signInWithEmailAndPassword(email , password).addOnCompleteListener{
                if (it.isSuccessful) {
//                    Log.d("ask", "signInWithEmailAndPassWord: DONEEEE ${it.result}")
                    home()
//           take them home
                }
                else{
//                    Log.d("ask", "signInWithEmailAndPassWord: ${it.result}")
                }
            }

        }catch (ex : Exception) {
//            Log.d("ask", "signInWithEmailAndPassWord: ${ex.message}")
        }
    }

    private fun createUser(displayName: String?) {
      val userId = auth.currentUser?.uid
        val user = mutableMapOf<String , Any>()
        user["user_id"] = userId.toString()
        user["display_name"] = displayName.toString()

        FirebaseFirestore.getInstance().collection("users").add(user)
    }


    fun createUsersWithEmailAndPassWord(email: String , password: String , home: () -> Unit){
        if (_loading.value==false){
            _loading.value == true
        auth.createUserWithEmailAndPassword(email , password).addOnCompleteListener {
            val displayName = it.result?.user?.email?.split("@")?.get(0)
            if (it.isSuccessful){
                createUser(displayName)
                home()
            }
            else {
                Log.d("FB", "createUsersWithEmailAndPassWord: ${it.result} ")
            }
            _loading.value == false

        }
        }

    }
}
