package com.picpay.desafio.android.repository

import android.content.Context
import android.util.Log
import com.picpay.desafio.android.api.models.User
import com.picpay.desafio.android.api.service.PicPayService
import com.picpay.desafio.android.cache.UserDao
import com.picpay.desafio.android.ui.bancoDadosDeletado
import com.picpay.desafio.android.utilidades.mostrarToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class Repository(private val service: PicPayService, private val userDao: UserDao) {


    suspend fun getUsers(context: Context): List<User> {

        return withContext(Dispatchers.IO) {
            val listaDeUsers = mutableListOf<User>()
            val listaDeUsersLocais = userDao.listar5PrimeirosUsuarios()

            if (listaDeUsersLocais.isNotEmpty()) {
                listaDeUsers.addAll(listaDeUsersLocais)
                withContext(Dispatchers.Main) { mostrarToast("Usando dados cacheados", context) }
                Log.i("Repository", "Usando dados cacheados:  ${listaDeUsersLocais[0]} ")
            } else {
                try {
                    val response = service.getUsers().execute()

                    if (response.isSuccessful) {
                        val listaDeUsersRemota = response.body()


                        listaDeUsersRemota?.let {
                            listaDeUsers.addAll(listaDeUsersRemota)
                            userDao.inserirListaDeUser(listaDeUsersRemota)
                            bancoDadosDeletado = false
                            withContext(Dispatchers.Main) {
                                mostrarToast("Dados do Servidor", context)
                            }
                            Log.i("Repository", "Usando dados do Servidor")

                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            mostrarToast(
                                "Erro de comunicação com o servidor",
                                context
                            )
                        }
                    }
                } catch (e: IOException) {        // Trate erros de rede ou de execução
                    Log.e("RepositoryError", "O erro  do servidor foi $e ?: erro desconhecido")
                }
            }

            listaDeUsers         // Essa lista é o retorno da função
        }
    }
}