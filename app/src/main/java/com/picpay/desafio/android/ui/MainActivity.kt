package com.picpay.desafio.android.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.ui.adapters.UserListAdapter
import com.picpay.desafio.android.ui.viewModels.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.swipeRefreshLayout
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: UserListAdapter
    private val viewModel by viewModel<MainActivityViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        selecionarLayout()

        inicializarVariaveis()

        configRecyclerView()

        configListenerBtnLimparBDLocal()

        configListenerSwipeRefresh()

    }


    override fun onResume() {
        super.onResume()
        mostrarProgresCarregando()

        observarSucessoRequisição()

        fazerRequisição()
    }



    private fun observarSucessoRequisição(){
        viewModel.successGetUsers.observe(this){ listaDeUsuarios ->
            adapter.users = listaDeUsuarios
            progressBar.visibility = View.GONE
        }

    }

    private fun configListenerSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            fazerRequisição()
            removerProgressApos1s()
        }
    }

    private fun fazerRequisição() {
        viewModel.getUsers(this)
    }

    fun configListenerBtnLimparBDLocal() {
        binding.btnLimparBDLocal.setOnClickListener {
            DialogDelete(adapter).show(supportFragmentManager, "dialogDelete")
        }
    }

    fun removerProgressApos1s() {
        Handler(Looper.getMainLooper()).postDelayed({
            swipeRefreshLayout.isRefreshing = false
            Log.i(
                "MainActivity",
                "Na função REMOVER -> O swipeRefresh está atualizando?  ${binding.swipeRefreshLayout.isRefreshing}  "
            )
        }, 1200)
    }


    private fun selecionarLayout() {
        binding = ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) }
    }

    private fun inicializarVariaveis() {
        recyclerView = binding.recyclerView
        progressBar = binding.userListProgressBar
        adapter = UserListAdapter()
    }

    private fun configRecyclerView() {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    fun mostrarProgresCarregando() {            //TESTE FEITO
        progressBar.visibility = View.VISIBLE
    }

}
