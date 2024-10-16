package br.edu.ifsp.lojaautomoveis.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.lojaautomoveis.databinding.CarroCelulaBinding
import br.edu.ifsp.lojaautomoveis.domain.Carro


class CarroAdapter: RecyclerView.Adapter<CarroAdapter.CarroViewHolder>(),
    Filterable {

    var onItemClick: ((Carro)->Unit) ?= null

    var carrosLista = ArrayList<Carro>()
    var carrosListaFilterable = ArrayList<Carro>()

    private lateinit var binding: CarroCelulaBinding


  fun updateList(newList: List<Carro> ){
        carrosLista = newList as ArrayList<Carro>
        carrosListaFilterable = carrosLista
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CarroViewHolder {

        binding = CarroCelulaBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return  CarroViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarroViewHolder, position: Int) {
        holder.modeloVH.text = carrosListaFilterable[position].modelo
        holder.placaVH.text = carrosListaFilterable[position].placa
    }

    override fun getItemCount(): Int {
        return carrosListaFilterable.size
    }

    inner class CarroViewHolder(view: CarroCelulaBinding): RecyclerView.ViewHolder(view.root)
    {
        val modeloVH = view.modelo
        val placaVH = view.placa

        init {
            view.root.setOnClickListener {
                onItemClick?.invoke(carrosListaFilterable[adapterPosition])
            }
        }

    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                if (p0.toString().isEmpty())
                    carrosListaFilterable = carrosLista
                else
                {
                    val resultList = ArrayList<Carro>()
                    for (row in carrosLista)
                        if (row.modelo.lowercase().contains(p0.toString().lowercase()))
                            resultList.add(row)
                    carrosListaFilterable = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = carrosListaFilterable
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                carrosListaFilterable = p1?.values as ArrayList<Carro>
                notifyDataSetChanged()
            }

        }
    }

}