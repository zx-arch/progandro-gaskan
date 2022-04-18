package id.ac.ukdw.pertemuan7_71190424

import android.os.Bundle
import android.content.Intent
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailPage : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_page)
        val cetakgambar = findViewById<ImageView>(R.id.profil_contact)
//        val ambilgambar=intent.getStringExtra("foto")
        val cetaknama = findViewById<TextView>(R.id.contactname)
        val ambilnama=intent.getStringExtra("nama")
        val bundle: Bundle = intent.extras!!
        val ambilgambar=bundle.getInt("foto")
        val cetaknomor= findViewById<TextView>(R.id.contactnumber)
        val ambilnomor = intent.getStringExtra("nomor")
        cetakgambar.setImageResource(ambilgambar)
        cetaknama.text=ambilnama
        cetaknomor.text=ambilnomor
    }
}