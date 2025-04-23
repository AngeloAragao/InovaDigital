package com.example.inovadigitalapp.model

class Pedido {

    var nomeCliente = ""
    var tipoServico = ""
    var descricaoPedido = ""
    var quantidadePedido = ""
    var entregaPedido = ""
    var valorPedido = ""
    var statusPedido = ""
    override fun toString(): String {
        return "Pedido(nomeCliente='$nomeCliente', tipoServico='$tipoServico', descricaoPedido='$descricaoPedido', quantidadePedido='$quantidadePedido', entregaPedido='$entregaPedido', valorPedido='$valorPedido', statusPedido='$statusPedido')"
    }


}