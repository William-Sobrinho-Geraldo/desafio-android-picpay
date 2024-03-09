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


    var successGetUsers = MutableLiveData<List<User>>()


    fun getUsers(context: Context) {
        viewModelScope.launch {
            val usersList = repository.getUsers(context)
            successGetUsers.postValue(usersList)
        }

    }


}