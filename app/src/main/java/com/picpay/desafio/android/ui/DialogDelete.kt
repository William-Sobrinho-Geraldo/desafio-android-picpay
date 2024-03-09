package com.picpay.desafio.android.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import com.picpay.desafio.android.R
import com.picpay.desafio.android.cache.AppDatabase
import com.picpay.desafio.android.databinding.FragmentDialogDeleteBinding
import com.picpay.desafio.android.ui.adapters.UserListAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DialogDelete (val adapter : UserListAdapter): DialogFragment() {
    private var _binding: FragmentDialogDeleteBinding? = null
    private val binding: FragmentDialogDeleteBinding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = FragmentDialogDeleteBinding.inflate(
        inflater, container, false
    ).apply {
        _binding = this
    }.root

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()


    }

    private fun setupViews() {
        binding.btnDeletar.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                AppDatabase.getDatabase(requireContext()).userDao().deletarBancoDeDados()
            }
            adapter.users = emptyList()
            mostrarSnackBar("Os dados locais foram apagados!")
            dismiss()
        }

        binding.btnFechar.setOnClickListener { dismiss() }
    }

    private fun mostrarSnackBar(menssagem: String) {
        Snackbar.make(
            binding.root,
            menssagem,
            Snackbar.LENGTH_LONG
        ).setBackgroundTint(
            ContextCompat.getColor(
                requireContext(),
                android.R.color.holo_red_dark
            )
        )
            .show()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
