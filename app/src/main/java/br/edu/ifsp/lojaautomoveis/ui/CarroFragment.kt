package br.edu.ifsp.lojaautomoveis.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import br.edu.ifsp.lojaautomoveis.R
import br.edu.ifsp.lojaautomoveis.databinding.FragmentCadastroBinding
import br.edu.ifsp.lojaautomoveis.domain.Carro
import br.edu.ifsp.lojaautomoveis.viewmodel.CarroViewModel
import br.edu.ifsp.lojaautomoveis.viewmodel.CadastroState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class CarroFragment : Fragment() {
    private var _binding: FragmentCadastroBinding? = null
    private val binding get() = _binding!!

    val viewModel : CarroViewModel by viewModels { CarroViewModel.carroViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCadastroBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.stateCadastro.collect {
                when (it) {
                    CadastroState.InsertSuccess -> {
                        Snackbar.make(
                            binding.root,
                            "Veículo inserido com sucesso",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        findNavController().popBackStack()
                    }

                    CadastroState.ShowLoading -> {}
                }
            }
        }


        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.cadastro_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.action_salvarCarro -> {
                        val marca = binding.commonLayout.editTextMarca.text.toString()
                        val modelo = binding.commonLayout.editTextModelo.text.toString()
                        val placa = binding.commonLayout.editTextPlaca.text.toString()
                        val chassi = binding.commonLayout.editTextChassi.text.toString()
                        val tipo = binding.commonLayout.editTextTipo.text.toString()
                        val cor = binding.commonLayout.editTextCor.text.toString()
                        val documentos = binding.commonLayout.editTextDocumentos.text.toString()
                        val preco = binding.commonLayout.editTextPreco.text.toString()

                        val carro = Carro(marca = marca, modelo = modelo, placa = placa, chassi = chassi,
                            tipo = tipo, cor = cor, documentos = documentos, preco = preco )
                        viewModel.insert(carro)

                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }
}