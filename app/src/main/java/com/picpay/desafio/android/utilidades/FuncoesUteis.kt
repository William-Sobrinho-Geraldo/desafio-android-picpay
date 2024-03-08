package com.picpay.desafio.android.utilidades

import android.content.Context
import android.widget.Toast
import java.util.regex.Matcher
import java.util.regex.Pattern


fun mostrarToast(message: String, context: Context) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}


fun limparMensagemError(texto: String): String? {
    // Utilizando expressão regular para extrair a mensagem entre as aspas
    val pattern: Pattern = Pattern.compile("\"error\":\"([^\"]*)\"")
    val matcher: Matcher = pattern.matcher(texto)

    // Verificando se houve um match na expressão regular
    return if (matcher.find()) {
        // Retornando o conteúdo capturado entre as aspas
        matcher.group(1)
//        matcher.group(2)
    } else {
        // Caso não seja encontrado, retornando o texto original
        texto
    }
}
