package br.edu.ifsp.lojaautomoveis.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.edu.ifsp.lojaautomoveis.domain.Carro


@Entity(tableName = "carros")
data class CarroEntity (
 @PrimaryKey(autoGenerate = true)
 val id: Int,
 val marca:String,
 val modelo:String,
 val placa:String,
 val chassi:String,
 val tipo:String,
 val cor:String,
 val documentos:String,
 val preco:String
 )
{
 fun toDomain(): Carro {
  return Carro(id, marca, modelo, placa, chassi, tipo, cor, documentos, preco )
 }
}

