package com.picpay.desafio.android.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.picpay.desafio.android.api.models.User

@Dao
interface UserDao {

    //colocar aqui as funções tipo service
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserirListaDeUser( listaDeUsers : List<User>)

    @Query("SELECT * FROM tabela_de_usuarios LIMIT 10")
    fun listar10PrimeirosUsuarios(): List<User>

    @Query("SELECT * FROM tabela_de_usuarios")
     fun listarTodosUsuarios(): List<User>

}