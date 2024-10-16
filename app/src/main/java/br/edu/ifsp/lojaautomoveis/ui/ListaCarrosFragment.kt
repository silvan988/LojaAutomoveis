package br.edu.ifsp.lojaautomoveis.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import br.edu.ifsp.lojaautomoveis.R
import br.edu.ifsp.lojaautomoveis.adapter.CarroAdapter
import br.edu.ifsp.lojaautomoveis.databinding.FragmentListaCarrosBinding
import br.edu.ifsp.lojaautomoveis.domain.Carro
import br.edu.ifsp.lojaautomoveis.viewmodel.CarroViewModel
import br.edu.ifsp.lojaautomoveis.viewmodel.ListaState
import kotlinx.coroutines.launch


class ListaCarrosFragment : Fragment(){

    private var _binding: FragmentListaCarrosBinding? = null

    private val binding get() = _binding!!

    lateinit var carroAdapter: CarroAdapter

    val viewModel : CarroViewModel by viewModels { CarroViewModel.carroViewModelFactory() }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListaCarrosBinding.inflate(inflater, container, false)

        binding.fab.setOnClickListener { findNavController().navigate(R.id.action_listaCarrosFragment_to_cadastroFragment) }

        return binding.root

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.getAllContacts()
        setupViewModel()



        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.main_menu, menu)

                val searchView = menu.findItem(R.id.action_search).actionView as SearchView
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(p0: String?): Boolean {
                        TODO("Not yet implemented")
                    }

                    override fun onQueryTextChange(p0: String?): Boolean {
                        carroAdapter.filter.filter(p0)
                        return true
                    }

                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                TODO("Not yet implemented")
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

    }


    private fun setupViewModel(){
        viewLifecycleOwner.lifecycleScope.launch{
            viewModel.stateList.collect{
                when(it){
                    is ListaState.SearchAllSuccess -> {
                        setupRecyclerView(it.carros)
                    }
                    ListaState.ShowLoading -> {}
                    ListaState.EmptyState -> {binding.textEmptyList.visibility=View.VISIBLE}
                }
            }
        }
    }

    private fun setupRecyclerView(contactList: List<Carro>)
    {

        carroAdapter = CarroAdapter().apply { updateList(contactList) }
        binding.recyclerview.adapter = carroAdapter
        carroAdapter.onItemClick = {it ->
            val bundle = Bundle()
            bundle.putInt("idCarro", it.id)
            findNavController().navigate(
                   R.id.action_listaCarrosFragment_to_detalheFragment,
                    bundle
               )
        }

    }

}