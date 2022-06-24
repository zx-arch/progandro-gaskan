package id.ac.ukdw.final_project_71190424

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private var db = Firebase.firestore
    private var storageReference: StorageReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnLogout = findViewById<Button>(R.id.btnLogout)
        val btnTambah = findViewById<Button>(R.id.btnTambah)
        val inputData = findViewById<EditText>(R.id.search_film)
        val btnCari = findViewById<Button>(R.id.btnCari)
        val btngetAllData = findViewById<Button>(R.id.btnGetAllData)
        val btnHapus = findViewById<Button>(R.id.btnHapus)
        storageReference = FirebaseStorage.getInstance().reference

        btnLogout.setOnClickListener {
            Firebase.auth.signOut()
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
            finish()
        }
        btnTambah.setOnClickListener {
            val i = Intent(this, TambahActivity::class.java)
            startActivity(i)
        }
        btnHapus.setOnClickListener {
            delete(inputData.text.toString())
        }
        btnCari.setOnClickListener {
            cariData(inputData.text.toString())
        }
        btngetAllData.setOnClickListener {
            inputData.setText("")
            selectData()
        }
        selectData()
    }

    @SuppressLint("SetTextI18n")
    private fun selectData() {
        db.collection("film")
            .get()
            .addOnSuccessListener {result ->
                val dataFilm = findViewById<TextView>(R.id.data_film)
                var hasil = ""
                for (doc in result) {
                    hasil += "Judul : "+doc.get("judul").toString()+"\nGenre : "+doc.get("genre")+
                            "\nNama Produser : "+doc.get("nm_produser").toString()+
                            "\nTahun Rilis : "+doc.get("tahun_rilis").toString()+"\n\n"
                }
                dataFilm.text = hasil
            }
    }

    private fun cariData(data: String) {
        db.collection("film")
            .get()
            .addOnSuccessListener {result ->
                val dataFilm = findViewById<TextView>(R.id.data_film)

                var hasil = ""
                for (doc in result) {
                    if (doc.get("judul").toString().contains(data, ignoreCase = true) || doc.get("judul").toString()==data.lowercase() ||
                        doc.get("genre").toString().contains(data, ignoreCase = true) || doc.get("genre").toString()==data.lowercase() ||
                        doc.get("tahun_rilis").toString().contains(data, ignoreCase = true) || doc.get("genre").toString()==data.lowercase()) {
                        hasil += "Judul :  "+doc.get("judul").toString()+"\nGenre : "+doc.get("genre")+
                                "\nNama Produser : "+doc.get("nm_produser").toString()+"\nTahun Rilis : "+doc.get("tahun_rilis").toString()+"\n\n"
                    }
                }
                if (hasil == "") {
                    hasil="Data Pencarian Tidak Ditemukan"
                }
                dataFilm.text = hasil
            }
    }

    private fun delete(data: String) {
        if (data != "") {
            db.collection("film")
                .get()
                .addOnSuccessListener { result ->
                    val dataFilm = findViewById<TextView>(R.id.data_film)
                    val data = findViewById<EditText>(R.id.search_film).text.toString()
                    for (doc in result) {
                        if (doc.get("judul").toString().contains(data, ignoreCase = true) || doc.get("judul")
                                .toString() == data ||
                            doc.get("genre").toString().contains(data, ignoreCase = true) || doc.get("genre")
                                .toString() == data ||
                            doc.get("tahun_rilis").toString().contains(data, ignoreCase = true) || doc.get("genre")
                                .toString() == data
                        ) {
                            val getImg = doc.get("poster").toString().split("%2F")[1].split("?")[0]
                            val getImgFile = storageReference?.child("myImages/$getImg")
                            if (getImgFile != null) {
                                getImgFile.delete().addOnSuccessListener {
                                    db.collection("film").document(doc.id).delete()
                                    selectData()
                                }
                            }
                            db.collection("film").document(doc.id).delete()
                            selectData()
                        }
                    }
                }
        }
    }
}



