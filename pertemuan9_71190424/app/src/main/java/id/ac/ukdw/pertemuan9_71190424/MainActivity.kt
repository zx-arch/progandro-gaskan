package id.ac.ukdw.pertemuan9_71190424

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    var sp: SharedPreferences? = null
    var spEdit: SharedPreferences.Editor? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sp = getSharedPreferences("mySP", MODE_PRIVATE)
        spEdit = sp?.edit()

        if (sp?.getBoolean("isLogin",false) == true) {
            setContentView(R.layout.activity_home)
            val logout = findViewById<Button>(R.id.logout)
            logout.setOnClickListener {
                logout()
            }
        } else {
            setContentView(R.layout.activity_main)
            val username = findViewById<EditText>(R.id.username)
            val password = findViewById<EditText>(R.id.password)

            val login = findViewById<Button>(R.id.buttonlogin)
            login.setOnClickListener {
                login(username.text.toString(),password.text.toString())
            }
        }
    }
    fun login(username: String, password: String) {
        if (username.equals("admin") && password.equals("1234")) {
            spEdit?.putBoolean("isLogin",true)
            spEdit?.commit()
            val i = Intent(this,MainActivity::class.java)
            startActivity(i)
            finish()
        } else {
            Toast.makeText(this,"Username dan Password Salah", Toast.LENGTH_LONG).show()
        }
    }

    fun logout() {
        spEdit?.putBoolean("isLogin",false)
        spEdit?.commit()
        val i = Intent(this,MainActivity::class.java)
        startActivity(i)
        finish()
    }
}