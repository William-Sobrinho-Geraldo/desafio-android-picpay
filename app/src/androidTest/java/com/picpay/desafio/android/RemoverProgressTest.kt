package com.picpay.desafio.android

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.picpay.desafio.android.ui.MainActivity
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class RemoverProgressTest {


    @Test
    fun deveRemoverProgressApos1s() {
        // Criar um mock para SwipeRefreshLayout
        val mockSwipeRefreshLayout = mockk<SwipeRefreshLayout>()
        val mockActivity = mockk<MainActivity>()
        val mockProgress =

        // Chamar o método a ser testado
        mockActivity.removerProgressApos1s()

        // Verificar se a propriedade isRefreshing é alterada para false após 1 segundo
        Thread.sleep(1201)
        verify(mockSwipeRefreshLayout).isRefreshing = false
    }

}