package com.example.cronoanaliseindustrial.util

import java.util.Locale

object TempoUtils {

    fun calcularTaktTimeSegundos(tempoTotalMs: Long, pecasProduzidas: Int): Double {
        if (pecasProduzidas <= 0) return 0.0
        return (tempoTotalMs / 1000.0) / pecasProduzidas
    }

    fun formatarMinutosSegundos(tempoTotalMs: Long): String {
        val totalSegundos = tempoTotalMs / 1000
        val minutos = totalSegundos / 60
        val segundos = totalSegundos % 60
        return "${minutos}min ${segundos}s"
    }

    fun formatarTaktTime(taktTimeSegundos: Double): String {
        return String.format(Locale("pt", "BR"), "%.2f s", taktTimeSegundos)
    }

}