package br.edu.ifsp.lojaautomoveis.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import br.edu.ifsp.lojaautomoveis.R
import br.edu.ifsp.lojaautomoveis.databinding.FragmentDetalheBinding
import br.edu.ifsp.lojaautomoveis.domain.Carro
import br.edu.ifsp.lojaautomoveis.viewmodel.CarroViewModel
import br.edu.ifsp.lojaautomoveis.viewmodel.DetalheState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class DetalheFragment : Fragment() {
    private var _binding: FragmentDetalheBinding? = null
    private val binding get() = _binding!!

    lateinit var carro: Carro
    lateinit var marcaEditText: EditText
    lateinit var modeloEditText: EditText
    lateinit var placaEditText: EditText
    lateinit var chassiEditText: EditText
    lateinit var tipoEditText: EditText
    lateinit var corEditText: EditText
    lateinit var documentosEditText: EditText
    lateinit var precoEditText: EditText

   val viewModel : CarroViewModel by viewModels { CarroViewModel.carroViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetalheBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        marcaEditText = binding.commonLayout.editTextMarca
        modeloEditText = binding.commonLayout.editTextModelo
        placaEditText = binding.commonLayout.editTextPlaca
        chassiEditText = binding.commonLayout.editTextChassi
        tipoEditText = binding.commonLayout.editTextTipo
        corEditText = binding.commonLayout.editTextCor
        documentosEditText = binding.commonLayout.editTextDocumentos
        precoEditText = binding.commonLayout.editTextPreco


        val idCarro = requireArguments().getInt("idCarro")
        viewModel.getContactById(idCarro)

        viewLifecycleOwner.lifecycleScope.launch{
            viewModel.stateDetail.collect {
                when (it) {
                    DetalheState.DeleteSuccess -> {
                        Snackbar.make(
                            binding.root,
                            "Veículo removido com sucesso",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        findNavController().popBackStack()
                    }

                    is DetalheState.GetByIdSuccess -> {
                        fillFields(it.c)
                    }

                    DetalheState.ShowLoading -> {}
                    DetalheState.UpdateSuccess -> {
                        Snackbar.make(
                            binding.root,
                            "Veículo alterado com sucesso",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        findNavController().popBackStack()
                    }
                }
            }
        }


        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
              menuInflater.inflate(R.menu.detalhe_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.action_alterarCarro -> {

                        carro.marca=marcaEditText.text.toString()
                        carro.modelo=modeloEditText.text.toString()
                        carro.placa=placaEditText.text.toString()
                        carro.chassi=chassiEditText.text.toString()
                        carro.tipo=tipoEditText.text.toString()
                        carro.cor=corEditText.text.toString()
                        carro.documentos=documentosEditText.text.toString()
                        carro.preco=precoEditText.text.toString()


                        viewModel.update(carro)

                        true
                    }
                    R.id.action_excluirCarro ->{
                        viewModel.delete(carro)
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }

    private fun fillFields(c: Carro) {
        carro=c
        marcaEditText.setText(carro.marca)
        modeloEditText.setText(carro.modelo)
        placaEditText.setText(carro.placa)
        chassiEditText.setText(carro.chassi)
        tipoEditText.setText(carro.tipo)
        corEditText.setText(carro.cor)
        documentosEditText.setText(carro.documentos)
        precoEditText.setText(carro.preco)


    }

}