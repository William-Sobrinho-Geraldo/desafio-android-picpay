package com.picpay.desafio.android.ui

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.ui.adapters.UserListAdapter
import com.picpay.desafio.android.ui.viewModels.MainActivityViewModel
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

    }


    override fun onResume() {
        super.onResume()

        mostrarProgresCarregando()

        viewModel.getUsers(this)
        observarSucessoDaRequisição()

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

    private fun mostrarProgresCarregando() {
        progressBar.visibility = View.VISIBLE
    }

    private fun observarSucessoDaRequisição() {
        viewModel.successGetUsers.observe(this) { listUsers ->

            progressBar.visibility = View.GONE
            adapter.users = listUsers

        }
    }

}
