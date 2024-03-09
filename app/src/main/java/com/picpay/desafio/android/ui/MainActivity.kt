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
import com.picpay.desafio.android.api.models.User
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.ui.adapters.UserListAdapter
import com.picpay.desafio.android.ui.viewModels.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.swipeRefreshLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

var bancoDadosDeletado = false

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

        configListeners()

    }


    override fun onResume() {
        super.onResume()
        Log.i("MainActivity", "onResume: Acabei de entrar em onResume da MainActivity")
        mostrarProgresCarregando()

        observarSucessoDaRequisição()
        viewModel.getUsers(this)

    }

    private fun configListeners() {
        binding.btnLimparBDLocal.setOnClickListener {
            DialogDelete(adapter).show(supportFragmentManager, "dialogTermos")
        }


        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getUsers(this)
            removerProgressApos1s()
        }
    }

    private fun removerProgressApos1s() {
        Handler(Looper.getMainLooper()).postDelayed({
            swipeRefreshLayout.isRefreshing = false
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

    fun mostrarProgresCarregando() {
        progressBar.visibility = View.VISIBLE
    }

    private fun observarSucessoDaRequisição() {
        viewModel.successGetUsers.observe(this) { listUsers ->

            progressBar.visibility = View.GONE
            adapter.users = listUsers

        }
    }

}
