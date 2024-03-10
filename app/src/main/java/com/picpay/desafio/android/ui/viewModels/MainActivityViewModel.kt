package com.picpay.desafio.android.ui.viewModels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.api.models.User
import com.picpay.desafio.android.repository.Repository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivityViewModel(private val repository: Repository) : ViewModel() {

    private val _successGetUsers = MutableLiveData<List<User>>()
    var successGetUsers = _successGetUsers

    fun getUsers(context: Context) {
        viewModelScope.launch {
            repository.getUsers(context).collect { listaDeUsuarios ->
                _successGetUsers.postValue(listaDeUsuarios)
            }
        }
    }
}