package com.example.inovadigitalapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StatusAdapter(private var lista: List<Pair<String, Int>>) :
    RecyclerView.Adapter<StatusAdapter.StatusViewHolder>() {

    class StatusViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val statusText: TextView = view.findViewById(R.id.textStatus)
        val quantidadeText: TextView = view.findViewById(R.id.textQuantidade)
        val statusCircle: View = view.findViewById(R.id.statusCircle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatusViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_status, parent, false)
        return StatusViewHolder(view)
    }

    override fun onBindViewHolder(holder: StatusViewHolder, position: Int) {
        val (status, quantidade) = lista[position]
        holder.statusText.text = status
        holder.quantidadeText.text = "Quantidade: $quantidade"

        val color = when (status) {
            "Recebido" -> Color.BLUE
            "Em andamento" -> Color.YELLOW
            "Atrasado" -> Color.RED
            "Finalizado" -> Color.GREEN
            else -> Color.GRAY
        }
        holder.statusCircle.setBackgroundColor(color)
    }

    override fun getItemCount() = lista.size

    // Método para atualizar os dados dinamicamente
    fun updateData(novaLista: List<Pair<String, Int>>) {
        lista = novaLista
        notifyDataSetChanged()
    }
}
