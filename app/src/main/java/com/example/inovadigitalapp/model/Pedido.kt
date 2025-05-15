package com.example.inovadigitalapp.model

import com.google.gson.annotations.SerializedName

data class Pedido(
    @SerializedName("codigo") var codigo: String = "",
    @SerializedName("nomeCliente") var nomeCliente: String = "",
    @SerializedName("tipoServico") var tipoServico: String = "",
    @SerializedName("descricaoPedido") var descricaoPedido: String = "",
    @SerializedName("quantidadePedido") var quantidadePedido: String = "",
    @SerializedName("entregaPedido") var entregaPedido: String = "",
    @SerializedName("valorPedido") var valorPedido: String = "",
    @SerializedName("statusPedido") var statusPedido: String = ""
) {
    override fun toString(): String {
        return "Pedido(codigo='$codigo', nomeCliente='$nomeCliente', tipoServico='$tipoServico', descricaoPedido='$descricaoPedido', quantidadePedido='$quantidadePedido', entregaPedido='$entregaPedido', valorPedido='$valorPedido', statusPedido='$statusPedido')"
    }
}
