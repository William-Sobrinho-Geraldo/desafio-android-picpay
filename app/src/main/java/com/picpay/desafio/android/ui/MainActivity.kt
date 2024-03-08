package com.picpay.desafio.android.ui

import android.R
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.picpay.desafio.android.cache.AppDatabase
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.ui.adapters.UserListAdapter
import com.picpay.desafio.android.ui.viewModels.MainActivityViewModel
import com.picpay.desafio.android.utilidades.mostrarToast
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

    }


    override fun onResume() {
        super.onResume()

        mostrarProgresCarregando()


        observarSucessoDaRequisição()
        Log.i("MainActivity", "onResume: Chamei getUsers()")
        viewModel.getUsers(this)

        binding.btnLimparBDLocal.setOnClickListener {
            AppDatabase.getDatabase(this).userDao().deletarBancoDeDados()
            mostrarSnackBar("Os dados locais foram apagados!")
        }


        binding.swipeRefreshLayout.setOnRefreshListener {

//            mostrarToast("Atualizando", this)
            viewModel.getUsers(this)

            Handler(Looper.getMainLooper()).postDelayed({
                // Atualiza o conteúdo aqui

                // Indica ao SwipeRefreshLayout que a operação de atualização foi concluída
                swipeRefreshLayout.isRefreshing = false
            }, 1500) // Tempo de simulação em milissegundos (aqui: 2 segundos)

        }

    }


    private fun mostrarSnackBar(menssagem: String) {
        Snackbar.make(
            binding.root,
            menssagem,
            Snackbar.LENGTH_LONG
        ).setBackgroundTint(
            ContextCompat.getColor(
                this@MainActivity,
                R.color.holo_red_dark
            )
        )
            .show()
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
