package br.edu.ifsp.lojaautomoveis.data


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CarroDAO {
    @Insert
    suspend fun insert(carroEntity: CarroEntity)

    @Update
    suspend fun update (carroEntity: CarroEntity)

    @Delete
    suspend fun delete(carroEntity: CarroEntity)

    @Query("SELECT * FROM carros ORDER BY modelo")
    fun getAllContacts(): Flow<List<CarroEntity>>

    @Query("SELECT * FROM carros WHERE id=:id")
    fun getContactById(id: Int): Flow<CarroEntity>


}