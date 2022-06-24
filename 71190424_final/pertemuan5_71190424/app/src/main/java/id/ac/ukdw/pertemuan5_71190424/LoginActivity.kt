package id.ac.ukdw.pertemuan5_71190424

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val edtUsername = findViewById<EditText>(R.id.editTextTextPersonName)
        val edPassword = findViewById<EditText>(R.id.editTextTextPassword)
        val btnLogin = findViewById<Button>(R.id.login)
        btnLogin.setOnClickListener {
            if (edPassword.text.toString().equals("1234") && !edtUsername.text.toString().equals("")) {
                login(edtUsername.text.toString())
            } else if (edPassword.text.toString().equals("1234") && edtUsername.text.toString().equals("")){
                showMessage("Username Tidak Boleh Kosong")
            } else {
                showMessage("Password salah")
            }
        }
    }

    fun login(username: String) {
        val i: Intent = Intent(this,MainActivity::class.java)
        i.putExtra("user",username)
        startActivity(i)
    }

    fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onRestart() {
        super.onRestart()
        setContentView(R.layout.activity_login)
    }
}