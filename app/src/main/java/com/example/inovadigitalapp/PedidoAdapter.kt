package com.example.inovadigitalapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.inovadigitalapp.model.Pedido

class PedidoAdapter(private var lista: List<Pedido>) : RecyclerView.Adapter<PedidoAdapter.PedidoViewHolder>() {

    var onPedidoClickListener: ((Pedido) -> Unit)? = null  // Declara o listener como opcional

    class PedidoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val pedidoNome: TextView = view.findViewById(R.id.pedidoNome)
        val pedidoStatus: TextView = view.findViewById(R.id.pedidoStatus)
        val pedidoData: TextView = view.findViewById(R.id.pedidoData)

        // Associar o listener de clique dentro do ViewHolder
        fun bind(pedido: Pedido, listener: ((Pedido) -> Unit)?) {
            itemView.setOnClickListener {
                listener?.invoke(pedido)  // Dispara o listener com o pedido
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pedido, parent, false)
        return PedidoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PedidoViewHolder, position: Int) {
        val pedido = lista[position]
        holder.pedidoNome.text = pedido.nomeCliente
        holder.pedidoStatus.text = pedido.statusPedido
        holder.pedidoData.text = pedido.entregaPedido

        // Chama o método de bind para configurar o listener de clique
        holder.bind(pedido, onPedidoClickListener)
    }

    override fun getItemCount() = lista.size

    // Método para atualizar os dados no adapter
    fun updateData(novaLista: List<Pedido>) {
        lista = novaLista
        notifyDataSetChanged()
    }
}
