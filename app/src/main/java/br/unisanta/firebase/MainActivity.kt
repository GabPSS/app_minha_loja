package br.unisanta.firebase

import android.content.Intent
import android.os.Bundle
import android.provider.Browser
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.unisanta.firebase.view.ProdutosActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth

        if (auth.currentUser != null) {
            val intent = Intent(this, ProdutosActivity::class.java)
            startActivity(intent)
        }


        val btnEntrar = findViewById<Button>(R.id.btn_main_entrar)
        val btnCadastrar = findViewById<Button>(R.id.btn_main_cadastrar)

        val edtEmail = findViewById<EditText>(R.id.edt_main_email)
        val edtSenha = findViewById<EditText>(R.id.edt_main_senha)


        btnEntrar.setOnClickListener {
            val email = edtEmail.text.toString().trim()
            val senha = edtSenha.text.toString()

            if (senha.length < 6) {
                Toast.makeText(
                    baseContext,
                    "Senha deve possuir mais de 6 caracteres.",
                    Toast.LENGTH_SHORT,
                ).show()
                return@setOnClickListener
            }

            if (Regex("^\\w+@\\w+\\.\\w+\$").matches(email).not()) {
                Toast.makeText(
                    baseContext,
                    "Email inválido, digite novamente.",
                    Toast.LENGTH_SHORT,
                ).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(edtEmail.text.toString(), senha)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d("FIREBASE", "signInWithEmail:success")

                        val intent = Intent(this, ProdutosActivity::class.java)
                        startActivity(intent)
                    } else {
                        Log.w("FIREBASE", "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Falha na autenticação! Usuário ou Senha incorretos.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
        }

        btnCadastrar.setOnClickListener {
            val email = edtEmail.text.toString()
            val senha = edtSenha.text.toString()

            auth.createUserWithEmailAndPassword(edtEmail.text.toString(), senha)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d("FIREBASE", "signInWithEmail:success")
                        Toast.makeText(
                            baseContext,
                            "Usuário criado com sucesso!",
                            Toast.LENGTH_SHORT,
                        ).show()

                        val intent = Intent(this, ProdutosActivity::class.java)
                        startActivity(intent)
                    } else {
                        Log.w("FIREBASE", "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Falha ao criar usuário!",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
        }
    }
}