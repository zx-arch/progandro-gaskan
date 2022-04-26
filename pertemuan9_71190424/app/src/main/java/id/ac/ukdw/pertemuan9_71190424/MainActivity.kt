package id.ac.ukdw.pertemuan9_71190424

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.widget.AdapterView

import android.os.Bundle
import android.util.Size
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
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
            val bahasa = resources.getStringArray(R.array.spinner_bahasa)
            val spbahasa = findViewById<Spinner>(R.id.spinbahasa)
            val font = resources.getStringArray(R.array.spinner_fontsize)
            val spfont = findViewById<Spinner>(R.id.spinfont)
            if (spbahasa != null) {
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, bahasa)
                spbahasa.adapter = adapter
            }
            spbahasa.onItemSelectedListener = this
            if (spfont != null) {
                val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item, font)
                spfont.adapter = adapter
            }
            spfont.onItemSelectedListener = this
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

    @SuppressLint("SetTextI18n")
    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {

        val textfont = findViewById<TextView>(R.id.textfont)
        val textbahasa = findViewById<TextView>(R.id.textbahasa)
        if (parent.getItemAtPosition(pos)!="Choose" && parent.getItemAtPosition(pos)!="Size") {
            if (parent.getItemAtPosition(pos).toString().toIntOrNull() != null) {
                textfont.setTextSize(parent.getItemAtPosition(pos).toString().toFloat())
                textbahasa.setTextSize(parent.getItemAtPosition(pos).toString().toFloat())
            } else {
                if (parent.getItemAtPosition(pos).toString() == "English") {
                    textbahasa.setText("language setting")
                    textfont.setText("Font Size")
                } else if (parent.getItemAtPosition(pos).toString() == "Japanese") {
                    textbahasa.setText("言語設定")
                    textfont.setText("フォントサイズ")
                } else if (parent.getItemAtPosition(pos).toString() == "Spanish") {
                    textbahasa.setText("configuración de idioma")
                    textfont.setText("tamaño de fuente")
                } else if (parent.getItemAtPosition(pos).toString() == "Default") {
                    textbahasa.setText("Pengaturan Bahasa")
                    textfont.setText("Ukuran Font")
                }
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }

    fun login(username: String, password: String) {
        if (username == "admin" && password == "1234") {
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