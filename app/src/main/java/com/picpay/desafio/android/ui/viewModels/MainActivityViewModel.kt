package com.picpay.desafio.android.ui.viewModels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.api.models.User
import com.picpay.desafio.android.repository.Repository
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val repository: Repository
) : ViewModel() {

//    val repository = Repository()

    val error = MutableLiveData<String>()
    var successGetUsers = MutableLiveData<List<User>>()


    fun getUsers(context: Context) {
        viewModelScope.launch {
            val usersList = repository.getUsers(context)
            successGetUsers.postValue(usersList)
        }


//        val mutableLiveData = MutableLiveData(retorno.toList())


//        enqueue(object : Callback<List<User>> {
//            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
//                if (response.isSuccessful) {
//                    successGetUsers.postValue(response.body())
//                    Log.i("ViewModel", "onResponse: A resposta foi isSuccessfull")
//                } else {
//                    try {
//                        val errorBody = response.errorBody()?.string()
//                        val erroGetUsersLimpo = limparMensagemError(errorBody.toString())
//                        Log.e(
//                            "ErrorGetUsers",
//                            "O erro  do servidor foi ${erroGetUsersLimpo ?: "erro desconhecido"} "
//                        )
//                        mostrarToast(" $erroGetUsersLimpo ", context)
//                    } catch (e: IOException) {
//                        Log.e("IOException", "Erro de leitura do response ->>", e)
//
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<List<User>>, t: Throwable) {
//                error.postValue(t.message)
//                Log.e("MainActivityViewModel", "Caímos no OnFailure:  O erro foi $t")
//            }
//
//
//        })
    }


}