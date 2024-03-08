package com.picpay.desafio.android.repository

import android.content.Context
import android.util.Log
import com.picpay.desafio.android.api.models.User
import com.picpay.desafio.android.api.service.PicPayService
import com.picpay.desafio.android.cache.UserDao
import com.picpay.desafio.android.utilidades.mostrarToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class Repository(private val service: PicPayService, private val dao: UserDao) {


    suspend fun getUsers(context: Context): List<User> {

        return withContext(Dispatchers.IO) {
            val listaDeUsers = mutableListOf<User>()
            val listaDeUsersLocais = dao.listar5PrimeirosUsuarios()

            if (listaDeUsersLocais.isNotEmpty()) {
                listaDeUsers.addAll(listaDeUsersLocais)
                withContext(Dispatchers.Main) { mostrarToast("Usando dados cacheados", context) }
                Log.i("Repository", "Usando dados cacheados:  ${listaDeUsersLocais[0]} ")
            } else {

                try {
                    val response = service.getUsers().execute()

                    if (response.isSuccessful) {
                        val listaDeUsersRemota = response.body()

                        // Insira a lista de usuários remota no banco de dados (se necessário)

                        listaDeUsersRemota?.let {
                            listaDeUsers.addAll(listaDeUsersRemota)
                            dao.inserirListaDeUser(listaDeUsersRemota)
                            withContext(Dispatchers.Main) { mostrarToast("Dados do Servidor", context) }
                            Log.i("Repository", "Usando dados do Servidor")

                        }
                    } else {
                        // Trate a falha da chamada (se necessário)
                    }
                } catch (e: IOException) {
                    // Trate erros de rede ou de execução (se necessário)
                }
            }

            listaDeUsers
        }


//        val retorno = mutableListOf(User("", "", 0, ""))
//        //verifiquei se tinha dados locais e não tinha
//
//        service.getUsers().enqueue(object : Callback<List<User>> {
//            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
//                if (response.isSuccessful) {
//                    // retorna essa resposta
//
//                    //Insere essa lista de resposta no banco de dados
//
//                    val listaDeUsersRemota = response.body()
//                    Log.i("Repository", "A resposta do servidor foi ${listaDeUsersRemota!![1]} ")
//
//                    listaDeUsersRemota.let {
//                        retorno.addAll(listaDeUsersRemota)
//                        dao.inserirListaDeUser(listaDeUsersRemota)
//                    }
//                } else {
//                    //deu ruim resolve depois
//                }
//            }
//
//            override fun onFailure(call: Call<List<User>>, t: Throwable) {
////                mostra o erro com toast
//            }
//        })
//
//        Log.i("Repository", "Estou retornando a variavel retorno como:  $retorno")
//        return retorno
    }

}