package com.example.cronoanaliseindustrial.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.cronoanaliseindustrial.R
import com.example.cronoanaliseindustrial.model.RegistroProducao
import com.example.cronoanaliseindustrial.util.TempoUtils

class MonitoramentoActivity : AppCompatActivity() {

    private lateinit var editLinha: EditText
    private lateinit var editOperador: EditText
    private lateinit var txtContador: TextView
    private lateinit var btnIniciar: Button
    private lateinit var btnRegistrar: Button
    private lateinit var btnFinalizar: Button
    private var tempoInicioMs: Long = 0L
    private var pecasProduzidas: Int = 0
    private var producaoEmAndamento: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monitoramento)

        editLinha = findViewById(R.id.editLinha)
        editOperador = findViewById(R.id.editOperador)
        txtContador = findViewById(R.id.txtContador)
        btnIniciar = findViewById(R.id.btnIniciar)
        btnRegistrar = findViewById(R.id.btnRegistrar)
        btnFinalizar = findViewById(R.id.btnFinalizar)

        btnIniciar.setOnClickListener { iniciarProducao() }
        btnRegistrar.setOnClickListener { registrarPeca() }
        btnFinalizar.setOnClickListener { finalizarProducao() }
    }

    private fun iniciarProducao() {
        val linha = editLinha.text.toString().trim()
        val operador = editOperador.text.toString().trim()

        if (linha.isEmpty()) {
            editLinha.error = "Informe a linha de produção"
            return
        }
        if (operador.isEmpty()) {
            editOperador.error = "Informe o operador"
            return
        }

        tempoInicioMs = System.currentTimeMillis()
        pecasProduzidas = 0
        producaoEmAndamento = true
        txtContador.text = pecasProduzidas.toString()

        editLinha.isEnabled = false
        editOperador.isEnabled = false

        btnIniciar.isEnabled = false
        btnRegistrar.isEnabled = true
        btnFinalizar.isEnabled = true

        Toast.makeText(this, "Produção iniciada", Toast.LENGTH_SHORT).show()
    }

    private fun registrarPeca() {
        if (!producaoEmAndamento) return
        pecasProduzidas++
        txtContador.text = pecasProduzidas.toString()
    }

    private fun finalizarProducao() {
        if (!producaoEmAndamento) return

        if (pecasProduzidas <= 0) {
            Toast.makeText(
                this,
                "Registre ao menos uma peça antes de finalizar",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val tempoFimMs = System.currentTimeMillis()
        val tempoTotalMs = tempoFimMs - tempoInicioMs
        val taktTimeSegundos = TempoUtils.calcularTaktTimeSegundos(tempoTotalMs, pecasProduzidas)

        val registro = RegistroProducao(
            linha = editLinha.text.toString().trim(),
            operador = editOperador.text.toString().trim(),
            pecasProduzidas = pecasProduzidas,
            tempoTotalMs = tempoTotalMs,
            taktTimeSegundos = taktTimeSegundos
        )

        val intent = Intent(this, RelatorioActivity::class.java).apply {
            putExtra(EXTRA_LINHA, registro.linha)
            putExtra(EXTRA_OPERADOR, registro.operador)
            putExtra(EXTRA_PECAS, registro.pecasProduzidas)
            putExtra(EXTRA_TEMPO_TOTAL_MS, registro.tempoTotalMs)
            putExtra(EXTRA_TAKT_TIME, registro.taktTimeSegundos)
        }
        startActivity(intent)

        resetarParaNovoCiclo()
    }

    private fun resetarParaNovoCiclo() {
        producaoEmAndamento = false
        pecasProduzidas = 0
        tempoInicioMs = 0L

        txtContador.text = pecasProduzidas.toString()

        editLinha.isEnabled = true
        editOperador.isEnabled = true
        editLinha.text.clear()
        editOperador.text.clear()

        btnIniciar.isEnabled = true
        btnRegistrar.isEnabled = false
        btnFinalizar.isEnabled = false
    }

    companion object {
        const val EXTRA_LINHA = "EXTRA_LINHA"
        const val EXTRA_OPERADOR = "EXTRA_OPERADOR"
        const val EXTRA_PECAS = "EXTRA_PECAS"
        const val EXTRA_TEMPO_TOTAL_MS = "EXTRA_TEMPO_TOTAL_MS"
        const val EXTRA_TAKT_TIME = "EXTRA_TAKT_TIME"
    }

}