package br.unisanta.firebase.api

import br.unisanta.firebase.model.Produto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("products")
    suspend fun getProdutos() : List<Produto>
}