package br.edu.ifsp.lojaautomoveis

import android.app.Application
import br.edu.ifsp.lojaautomoveis.data.CarroDatabase
import br.edu.ifsp.lojaautomoveis.repository.CarroRepository

class CarroApplication:Application() {
    val database by lazy { CarroDatabase.getDatabase(this) }
    val repository by lazy { CarroRepository(database.carroDAO()) }
}