package br.unisanta.firebase.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import br.unisanta.firebase.R
import br.unisanta.firebase.model.Produto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore


class ProdutoAdapter(private val produtos: List<Produto>) : RecyclerView.Adapter<ProdutoAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txvNome = itemView.findViewById<TextView>(R.id.txv_produto_titulo)
        val txvCategoria = itemView.findViewById<TextView>(R.id.txv_produto_categoria)
        val txvPreco = itemView.findViewById<TextView>(R.id.txv_produto_preco)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_produto, parent, false)
        view.setOnClickListener {



        }
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return produtos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val produto = produtos[position]

        holder.txvNome.text = produto.titulo
        holder.txvCategoria.text = produto.categoria
        holder.txvPreco.text = "$${produto.preco.toString()}"

        holder.itemView.setOnClickListener {
            val uid = FirebaseAuth.getInstance().currentUser!!.uid
            val dados = hashMapOf(
                "id" to produto.id,
                "titulo" to produto.titulo,
                "preco" to produto.preco,
                "categoria" to produto.categoria,
                "salvoEm" to FieldValue.serverTimestamp()
            )
            FirebaseFirestore.getInstance()
                .collection("usuarios").document(uid)
                .collection("favoritos").document(produto.id.toString())
                .set(dados)
        }
    }
}