package com.example.cronoanaliseindustrial.ui

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cronoanaliseindustrial.R
import com.example.cronoanaliseindustrial.util.TempoUtils

class RelatorioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_relatorio)

        val linha = intent.getStringExtra(MonitoramentoActivity.EXTRA_LINHA)
        val operador = intent.getStringExtra(MonitoramentoActivity.EXTRA_OPERADOR)
        val pecas = intent.getIntExtra(MonitoramentoActivity.EXTRA_PECAS, 0)
        val tempoTotalMs = intent.getLongExtra(MonitoramentoActivity.EXTRA_TEMPO_TOTAL_MS, 0L)
        val taktTime = intent.getDoubleExtra(MonitoramentoActivity.EXTRA_TAKT_TIME, 0.0)

        findViewById<TextView>(R.id.txtRelatorioLinha).text = linha
        findViewById<TextView>(R.id.txtRelatorioOperador).text = operador
        findViewById<TextView>(R.id.txtRelatorioPecas).text = pecas.toString()
        findViewById<TextView>(R.id.txtRelatorioTempo).text = TempoUtils.formatarMinutosSegundos(tempoTotalMs)
        findViewById<TextView>(R.id.txtRelatorioTaktTime).text = TempoUtils.formatarTaktTime(taktTime)

        findViewById<Button>(R.id.btnVoltar).setOnClickListener {
            finish()
        }
    }
}
