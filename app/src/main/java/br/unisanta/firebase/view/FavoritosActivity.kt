package br.unisanta.firebase.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.unisanta.firebase.R
import br.unisanta.firebase.adapter.ProdutoAdapter
import br.unisanta.firebase.api.RetrofitInstance
import br.unisanta.firebase.model.Produto
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class FavoritosActivity : AppCompatActivity() {
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_favoritos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        db.collection("usuarios").document(uid)
            .collection("favoritos").get().addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    var produtos = mutableListOf<Produto>()

                    for (document in task.result.documents) {
                        produtos.add(Produto(
                            id = document["id"] as Int,
                            titulo = document["titulo"] as String,
                            categoria = document["categoria"] as String,
                            preco = document["preco"] as Double
                        ))
                    }

                    val rvProdutos = findViewById<RecyclerView>(R.id.rv_produtos_lista)
                    rvProdutos.layoutManager = LinearLayoutManager(this)

                    rvProdutos.adapter = ProdutoAdapter(produtos)
                }

            }
    }
}