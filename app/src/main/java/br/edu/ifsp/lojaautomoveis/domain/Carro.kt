package br.edu.ifsp.lojaautomoveis.domain

import br.edu.ifsp.lojaautomoveis.data.CarroEntity

data class Carro (
    var id:Int=0,
    var marca:String,
    var modelo:String,
    var placa:String,
    var chassi:String,
    var tipo:String,
    var cor:String,
    var documentos:String,
    var preco:String
){
    fun toEntity(): CarroEntity {
        return CarroEntity(id, marca, modelo, placa, chassi, tipo, cor, documentos, preco )
    }
}
