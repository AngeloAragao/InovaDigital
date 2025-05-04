package com.example.inovadigitalapp.resources

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.inovadigitalapp.R

class ValorStatusAdapter(private var lista: List<Pair<String, Double>>) :
    RecyclerView.Adapter<ValorStatusAdapter.ValorStatusViewHolder>() {

    var onCardClickListener: ((String) -> Unit)? = null

    class ValorStatusViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val statusText: TextView = view.findViewById(R.id.textStatus)
        val valorText: TextView = view.findViewById(R.id.textValor) // Pode manter esse ID
        val statusCircle: View = view.findViewById(R.id.statusCircle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ValorStatusViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_valor_status, parent, false)
        return ValorStatusViewHolder(view)
    }

    override fun onBindViewHolder(holder: ValorStatusViewHolder, position: Int) {
        val (status, valorTotal) = lista[position]

        holder.statusText.text = status
        holder.valorText.text = "Total R$: %.2f".format(valorTotal).replace(".", ",")

        val color = when (status) {
            "Recebido" -> Color.BLUE
            "Produzindo" -> Color.YELLOW
            "Atrasado" -> Color.RED
            "Finalizado" -> Color.GREEN
            else -> Color.GRAY
        }
        holder.statusCircle.setBackgroundColor(color)

        holder.itemView.setOnClickListener {
            onCardClickListener?.invoke(status)
        }
    }

    override fun getItemCount(): Int = lista.size

    fun updateData(novaLista: List<Pair<String, Double>>) {
        lista = novaLista
        notifyDataSetChanged()
    }
}
