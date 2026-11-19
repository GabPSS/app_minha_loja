package br.unisanta.firebase.model

import com.google.gson.annotations.SerializedName

data class Produto(
    var id: Int = 0,
    @SerializedName("title")
    var titulo: String,
    @SerializedName("price")
    var preco: Double,
    @SerializedName("category")
    var categoria: String
)
