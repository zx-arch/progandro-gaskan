package id.ac.ukdw.pertemuan5_71190424

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val username = intent.getStringExtra("user")
        val usr = findViewById<TextView>(R.id.getusername)
        usr.text = "${username}"
        val btnLogout = findViewById<Button>(R.id.button3)
        btnLogout.setOnClickListener {
            val i = Intent(this,LoginActivity::class.java)
            startActivity(i)
        }
    }
}