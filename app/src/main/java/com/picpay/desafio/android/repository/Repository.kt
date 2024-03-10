package com.picpay.desafio.android.repository

import android.content.Context
import android.util.Log
import com.picpay.desafio.android.api.models.User
import com.picpay.desafio.android.api.service.PicPayService
import com.picpay.desafio.android.cache.UserDao
import com.picpay.desafio.android.utilidades.mostrarToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.io.IOException

class Repository(private val service: PicPayService, private val userDao: UserDao) {

    fun getUsers(context: Context): Flow<List<User>> {

        val listaDeUsers: MutableList<User> = mutableListOf()

        return flow {
            withContext(Dispatchers.IO) {
                val listaDeUsersLocais = userDao.listar5PrimeirosUsuarios()
                if (!listaDeUsersLocais.isNullOrEmpty()) {
                    listaDeUsers.addAll(listaDeUsersLocais)
                    mostrarToast("Usando dados cacheados", context)
                    Log.i("Repository", "Usando dados cacheados:  ${listaDeUsersLocais[0]} ")


                } else {
                    try {
                        val response = service.getUsers().execute()

                        if (response.isSuccessful) {
                            val listaDeUsersRemota = response.body()

                            listaDeUsersRemota?.let {
                                listaDeUsers.addAll(listaDeUsersRemota)
                                userDao.inserirListaDeUser(listaDeUsersRemota)
                                mostrarToast("Dados do Servidor", context)
                                Log.i("Repository", "Usando dados do Servidor")
                            }


                            if (listaDeUsersRemota.isNullOrEmpty()) {
                                mostrarToast("Problemas de conexão com o servidor. Tente mais tarde", context)
                            } else null


                        } else {
                            mostrarToast("Erro de comunicação com o servidor", context)
                        }
                    } catch (e: IOException) {
                        Log.e("RepositoryError", "O erro  do servidor foi $e ?: erro desconhecido")
                        mostrarToast("Erro de comunicação com o servidor", context)

                    }
                }
            }


            emit(listaDeUsers)           // Este é o emissor da lista de usuários
        } // flow
    }
}