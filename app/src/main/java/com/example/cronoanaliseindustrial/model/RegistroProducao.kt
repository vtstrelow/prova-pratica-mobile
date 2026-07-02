package com.example.cronoanaliseindustrial.model
data class RegistroProducao(

    val linha: String,
    val operador: String,
    val pecasProduzidas: Int,
    val tempoTotalMs: Long,
    val taktTimeSegundos: Double

)