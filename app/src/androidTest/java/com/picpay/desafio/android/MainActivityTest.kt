package com.picpay.desafio.android

import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import com.picpay.desafio.android.ui.DialogDelete
import com.picpay.desafio.android.ui.MainActivity
import com.picpay.desafio.android.ui.adapters.UserListAdapter
import junit.framework.TestCase.assertEquals
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.*


class MainActivityTest {

    private val server = MockWebServer()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun shouldDisplayTitle() {
        launchActivity<MainActivity>().apply {
            val expectedTitle = context.getString(R.string.title)

            moveToState(Lifecycle.State.RESUMED)

            onView(withText(expectedTitle)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun shouldDisplayListItem() {
        server.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                return when (request.path) {
                    "/users" -> successResponse
                    else -> errorResponse
                }
            }
        }

        server.start(serverPort)

        launchActivity<MainActivity>().apply {
            // TODO("validate if list displays items returned by server")
        }

        server.close()
    }

    companion object {
        private const val serverPort = 8080

        private val successResponse by lazy {
            val body =
                "[{\"id\":1001,\"name\":\"Eduardo Santos\",\"img\":\"https://randomuser.me/api/portraits/men/9.jpg\",\"username\":\"@eduardo.santos\"}]"

            MockResponse()
                .setResponseCode(200)
                .setBody(body)
        }

        private val errorResponse by lazy { MockResponse().setResponseCode(404) }
    }

    @Rule
    @JvmField
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun deveMostrarProgressBar() {
        activityRule.scenario.onActivity { activity ->
            //Simula que está mostrando a progress
            activity.mostrarProgresCarregando()

            //Agora verifica se a progress está visível
            val progressBar = activity.findViewById<ProgressBar>(R.id.user_list_progress_bar)
            assertEquals(View.VISIBLE, progressBar.visibility)
        }
    }

    @Test
    fun deveRemoverProgressBarApos1s() {
        activityRule.scenario.onActivity { activity ->
            //Simula que está mostrando a progress
            activity.mostrarProgresCarregando()
            activity.removerProgressApos1s()

            Handler(Looper.getMainLooper()).postDelayed({
                // Verifica se a progress está invisível após 1 segundo
                val swipeRefreshLayout = activity.findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout)
                assertEquals(false, swipeRefreshLayout.isRefreshing)
            }, 100)

            //Agora verifica se a progress está visível
//            val swipeRefreshLayout = activity.findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout)
//            assertEquals(swipeRefreshLayout.isRefreshing, false)
        }
    }

    @Test
    fun deveMostrarDialogDeleteAoClicarNoBotãoDeleteBD() {

//        activityRule.scenario.onActivity { activity ->
//            activity.listenerBtnLimparBDLocal()

            val mockButton = mock(View::class.java)
//            val button = activity.findViewById<Button>(R.id.btn_limparBDLocal)
            mockButton.performClick()

//            val mockAdapter = UserListAdapter()
            val mockAdapter = mock(UserListAdapter::class.java)


//            val fragmentManager = activity.supportFragmentManager
            val mockFragmentManager = mock(FragmentManager::class.java)

//            val fragment = fragmentManager.findFragmentByTag("dialogDelete")
            `when`(mockButton.setOnClickListener(any())).thenAnswer { invocation ->
                val listener = invocation.arguments[0] as View.OnClickListener
                listener.onClick(mockButton)
                null
            }
            val mainActivity = MainActivity()
            mainActivity.configListenerBtnLimparBDLocal()


            val dialogDelete = DialogDelete(mockAdapter)
            verify(dialogDelete).show(mockFragmentManager,"dialoDelete")

//            assertNotNull(dialogDelete)
//            Log.i("testes", "deveMostrarDialogDeleteAoClicarNoBotãoDeleteBD: o valor de dialogDelete é ${dialogDelete}")
//            Log.i("testes", "deveMostrarDialogDeleteAoClicarNoBotãoDeleteBD: o Método dialogDelete.isVisible é  ${dialogDelete.isVisible}")
//            Log.i("testes", "deveMostrarDialogDeleteAoClicarNoBotãoDeleteBD: a variável count é ${activity.count}")
//
//            assertTrue(dialogDelete.isVisible)
//        }
    }


}