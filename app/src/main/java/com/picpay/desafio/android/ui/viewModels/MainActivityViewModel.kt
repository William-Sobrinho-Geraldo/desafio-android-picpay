package com.picpay.desafio.android.ui.viewModels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.picpay.desafio.android.api.models.User
import com.picpay.desafio.android.repository.Repository
import com.picpay.desafio.android.utilidades.limparMensagemError
import com.picpay.desafio.android.utilidades.mostrarToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MainActivityViewModel(
    private val repository: Repository
) : ViewModel() {

//    val repository = Repository()

    val error = MutableLiveData<String>()
    val successGetUsers = MutableLiveData<List<User>>()


    fun getUsers(context: Context) {
        repository.getUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    successGetUsers.postValue(response.body())
                    Log.i("ViewModel", "onResponse: A resposta foi isSuccessfull")
                } else {
                    try {
                        val errorBody = response.errorBody()?.string()
                        val erroGetUsersLimpo = limparMensagemError(errorBody.toString())
                        Log.e(
                            "ErrorGetUsers",
                            "O erro  do servidor foi ${erroGetUsersLimpo ?: "erro desconhecido"} "
                        )
                        mostrarToast(" $erroGetUsersLimpo ", context)
                    } catch (e: IOException) {
                        Log.e("IOException", "Erro de leitura do response ->>", e)

                    }
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                error.postValue(t.message)
                Log.e("MainActivityViewModel", "Caímos no OnFailure:  O erro foi $t")
            }


        })
    }


}