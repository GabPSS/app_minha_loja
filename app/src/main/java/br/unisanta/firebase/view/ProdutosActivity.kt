package br.unisanta.firebase.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.unisanta.firebase.R
import br.unisanta.firebase.adapter.ProdutoAdapter
import br.unisanta.firebase.api.RetrofitInstance
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch

class ProdutosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_produtos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lifecycleScope.launch {
            try {
                val produtos = RetrofitInstance.api.getProdutos()

                val rvProdutos = findViewById<RecyclerView>(R.id.rv_produtos_lista)
                rvProdutos.layoutManager = LinearLayoutManager(this@ProdutosActivity)

                rvProdutos.adapter = ProdutoAdapter(produtos)


            }
            catch (e:Exception)
            {
                Log.d("API", e.message.toString())
                Toast.makeText(
                    baseContext,
                    "Erro ao baixar os produtos!",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }

        val fabSair = findViewById<FloatingActionButton>(R.id.fab_produtos_sair)

        fabSair.setOnClickListener {
            Firebase.auth.signOut()
            finish()
        }

        val btnFavoritos = findViewById<Button>(R.id.btn_produtos_favoritos)

        btnFavoritos.setOnClickListener {
            val intent = Intent(this, FavoritosActivity::class.java)
            startActivity(intent)
        }


    }
}