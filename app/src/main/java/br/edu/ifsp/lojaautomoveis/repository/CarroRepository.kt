package br.edu.ifsp.lojaautomoveis.repository


import br.edu.ifsp.lojaautomoveis.data.CarroDAO
import br.edu.ifsp.lojaautomoveis.domain.Carro
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map


class CarroRepository (private val carroDAO: CarroDAO) {

    suspend fun insert(carro: Carro){
        carroDAO.insert(carro.toEntity())
    }

    suspend fun update(carro: Carro){
        carroDAO.update(carro.toEntity())
    }

    suspend fun delete(carro: Carro){
        carroDAO.delete(carro.toEntity())
    }

     fun getAllContacts(): Flow<List<Carro>> {
          return carroDAO.getAllContacts().map { list ->
            list.map {
                it.toDomain()
            }
        }

    }


    fun getContactById(id: Int): Flow<Carro>{
        return carroDAO.getContactById(id).filterNotNull().map {it.toDomain()}
    }


}