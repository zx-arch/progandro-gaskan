package id.ac.ukdw.pertemuan11_71190424

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private val db = Firebase.firestore
    private var tombolcariditekan = false
    private var querycheck = "select * from mahasiswa"
    private var editnama = ""
    private var editnim = ""
    private var editipk = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSimpan = findViewById<Button>(R.id.btnSimpan)
        val btnCari = findViewById<Button>(R.id.btnCari)
        val editNama = findViewById<EditText>(R.id.editNama)
        editnama = editNama.text.toString()
        val editNim = findViewById<EditText>(R.id.editNIM)
        val editIpk = findViewById<EditText>(R.id.editIPK)

        val spsort = resources.getStringArray(R.array.sort_spinner)
        val spinner = findViewById<Spinner>(R.id.sorting)
        if (spinner != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spsort)
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = this
        btnSimpan.setOnClickListener {
            if (editNama.text.toString()=="" || editNim.text.toString()=="" || editIpk.text.toString()=="") {
                Toast.makeText(this,"Nama NIM dan IPK Harus Diisi Semua",Toast.LENGTH_LONG).show()
            } else {
                if (editNim.text.toString().length == 8) {
                    if (editIpk.text.toString().toDouble() <= 4.0) {
                        val mahasiswa = Mahasiswa(editNama.text.toString(),editNim.text.toString().toInt(),editIpk.text.toString().toDouble())
                        db.collection("mahasiswa").add(mahasiswa)
                        editNama.setText("")
                        editNim.setText("")
                        editIpk.setText("")
                        refreshData()
                    } else {
                        Toast.makeText(this,"IPK Tidak Boleh Lebih dari 4.0", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this,"NIM harus 8 digit",Toast.LENGTH_LONG).show()
                }
            }
        }

        btnCari.setOnClickListener {
            tombolcariditekan = true
            search(editNama.text.toString(),editNim.text.toString(),editIpk.text.toString())
        }
        refreshData()
    }

    private fun refreshData() {
        db.collection("mahasiswa")
            .get()
            .addOnSuccessListener {result ->
                val txtHasil = findViewById<TextView>(R.id.txtHasil)
                var hasil = ""
                for (doc in result) {
                    hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                }
                txtHasil.text = hasil
            }
    }

    @SuppressLint("SetTextI18n")
    fun search(nama: String="", nim: String="", ipk: String="") {
        if (nama=="" && nim=="" && ipk=="") {
            Toast.makeText(this,"Masukkan Nama atau NIM atau IPK yang Ingin Dicari",Toast.LENGTH_LONG).show()
        } else {
            if (nama!="" && nim=="" && ipk=="") {  // cari berdasarkan nama saja
                editnama = nama
                querycheck = "select nama from mahasiswa"
                db.collection("mahasiswa")
                    .whereEqualTo("nama",nama)
                    .get()
                    .addOnSuccessListener {result ->
                        val txtHasil = findViewById<TextView>(R.id.txtHasil)
                        var hasil = ""
                        for (doc in result) {
                            hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                        }
                        if (hasil=="") {
                            hasil+="Data Pencarian Tidak Ditemukan"
                        }
                        txtHasil.text = hasil
                    }
            } else if (nama=="" && nim!="" && ipk=="") {  // cari berdasarkan nim saja
                editnim = nim
                db.collection("mahasiswa")
                    .whereEqualTo("nim",nim.toInt())
                    .get()
                    .addOnSuccessListener {result ->
                        val txtHasil = findViewById<TextView>(R.id.txtHasil)
                        var hasil = ""
                        for (doc in result) {
                            hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                        }
                        if (hasil=="") {
                            hasil+="Data Pencarian Tidak Ditemukan"
                        }
                        txtHasil.text = hasil
                    }
            } else if (nama=="" && nim=="" && ipk!="") {  // cari berdasarkan ipk saja
                editipk = ipk
                querycheck = "select ipk from mahasiswa"
                db.collection("mahasiswa")
                    .whereEqualTo("ipk",ipk.toDouble())
                    .get()
                    .addOnSuccessListener {result ->
                        val txtHasil = findViewById<TextView>(R.id.txtHasil)
                        var hasil = ""
                        for (doc in result) {
                            hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                        }
                        if (hasil=="") {
                            hasil+="Data Pencarian Tidak Ditemukan"
                        }
                        txtHasil.text = hasil
                    }
            } else if (nama!="" && nim!="" && ipk=="") {  // cari berdasarkan nama dan nim saja
                editnama = nama
                editnim = nim
                db.collection("mahasiswa")
                    .whereEqualTo("nama",nama)
                    .whereEqualTo("nim",nim.toInt())
                    .get()
                    .addOnSuccessListener {result ->
                        val txtHasil = findViewById<TextView>(R.id.txtHasil)
                        var hasil = ""
                        for (doc in result) {
                            hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                        }
                        if (hasil=="") {
                            hasil+="Data Pencarian Tidak Ditemukan"
                        }
                        txtHasil.text = hasil
                    }
            } else if (nama!="" && nim!="" && ipk!="") {  // cari berdasarkan nama, nim dan ipk
                editnama = nama
                editnim = nim
                editipk = ipk
                db.collection("mahasiswa")
                    .whereEqualTo("nama",nama)
                    .whereEqualTo("nim",nim.toInt())
                    .whereEqualTo("ipk",ipk.toDouble())
                    .get()
                    .addOnSuccessListener {result ->
                        val txtHasil = findViewById<TextView>(R.id.txtHasil)
                        var hasil = ""
                        for (doc in result) {
                            hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                        }
                        if (hasil=="") {
                            hasil+="Data Pencarian Tidak Ditemukan"
                        }
                        txtHasil.text = hasil
                    }
            } else if (nama!="" && nim=="" && ipk!="") { // cari berdasarkan nama dan ipk saja
                editnama = nama
                editipk = ipk
                querycheck = "select nama, nim from mahasiswa"
                db.collection("mahasiswa")
                    .whereEqualTo("nama",nama)
                    .whereEqualTo("ipk",ipk.toDouble())
                    .get()
                    .addOnSuccessListener {result ->
                        val txtHasil = findViewById<TextView>(R.id.txtHasil)
                        var hasil = ""
                        for (doc in result) {
                            hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                        }
                        if (hasil=="") {
                            hasil+="Data Pencarian Tidak Ditemukan"
                        }
                        txtHasil.text = hasil
                    }
            } else if (nama=="" && nim!="" && ipk!="") { // cari berdasarkan nim dan ipk saja
                editnim = nim
                editipk = ipk
                db.collection("mahasiswa")
                    .whereEqualTo("nim",nim.toInt())
                    .whereEqualTo("ipk",ipk.toDouble())
                    .get()
                    .addOnSuccessListener {result ->
                        val txtHasil = findViewById<TextView>(R.id.txtHasil)
                        var hasil = ""
                        for (doc in result) {
                            hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                        }
                        if (hasil=="") {
                            hasil+="Data Pencarian Tidak Ditemukan"
                        }
                        txtHasil.text = hasil
                    }
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
        if (parent != null) {
            if (parent.getItemAtPosition(pos)!="Sort By") {
                if (tombolcariditekan==false) {
                    if (parent.getItemAtPosition(pos) == "Nama Ascending" && querycheck == "select * from mahasiswa") {
                        db.collection("mahasiswa")
                            .orderBy("nama")
                            .get()
                            .addOnSuccessListener { result ->
                                val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                var hasil = ""
                                for (doc in result) {
                                    hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${
                                        doc.get(
                                            "ipk"
                                        )
                                    }\n"
                                }
                                txtHasil.text = hasil
                            }
                    } else if (parent.getItemAtPosition(pos) == "Nama Descanding" && querycheck == "select * from mahasiswa") {

                        db.collection("mahasiswa")
                            .orderBy("nama", Query.Direction.DESCENDING)
                            .get()
                            .addOnSuccessListener { result ->
                                val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                var hasil = ""
                                for (doc in result) {
                                    hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${
                                        doc.get(
                                            "ipk"
                                        )
                                    }\n"
                                }
                                txtHasil.text = hasil
                            }
                    } else if (parent.getItemAtPosition(pos) == "NIM Ascending" && querycheck == "select * from mahasiswa") {
                        db.collection("mahasiswa")
                            .orderBy("nim")
                            .get()
                            .addOnSuccessListener { result ->
                                val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                var hasil = ""
                                for (doc in result) {
                                    hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${
                                        doc.get(
                                            "ipk"
                                        )
                                    }\n"
                                }
                                txtHasil.text = hasil
                            }
                    } else if (parent.getItemAtPosition(pos) == "NIM Descanding" && querycheck == "select * from mahasiswa") {
                        db.collection("mahasiswa")
                            .orderBy("nim", Query.Direction.DESCENDING)
                            .get()
                            .addOnSuccessListener { result ->
                                val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                var hasil = ""
                                for (doc in result) {
                                    hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${
                                        doc.get(
                                            "ipk"
                                        )
                                    }\n"
                                }
                                txtHasil.text = hasil
                            }
                    } else if (parent.getItemAtPosition(pos) == "IPK Ascending" && querycheck == "select * from mahasiswa") {
                        db.collection("mahasiswa")
                            .orderBy("ipk")
                            .get()
                            .addOnSuccessListener { result ->
                                val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                var hasil = ""
                                for (doc in result) {
                                    hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${
                                        doc.get(
                                            "ipk"
                                        )
                                    }\n"
                                }
                                txtHasil.text = hasil
                            }
                    } else if (parent.getItemAtPosition(pos) == "IPK Descanding" && querycheck == "select * from mahasiswa") {
                        db.collection("mahasiswa")
                            .orderBy("ipk", Query.Direction.DESCENDING)
                            .get()
                            .addOnSuccessListener { result ->
                                val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                var hasil = ""
                                for (doc in result) {
                                    hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${
                                        doc.get(
                                            "ipk"
                                        )
                                    }\n"
                                }
                                txtHasil.text = hasil
                            }
                    }
                }


                else if (tombolcariditekan==true) {
                    if (parent.getItemAtPosition(pos) == "Nama Ascending") {
                        if (editnama!="" && editnim=="" && editipk=="") {  // cari berdasarkan nama saja
                            db.collection("mahasiswa")
                                .whereEqualTo("nama",editnama)
                                .orderBy("nama")
                                .get()
                                .addOnSuccessListener {result ->
                                    val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                    var hasil = ""
                                    for (doc in result) {
                                        hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                                    }
                                    if (hasil=="") {
                                        hasil+="Data Pencarian Tidak Ditemukan"
                                    }
                                    txtHasil.text = hasil
                                }
                        } else if (editnama=="" && editnim!="" && editipk=="") {  // cari berdasarkan nim saja
                            db.collection("mahasiswa")
                                .whereEqualTo("nim",editnim.toInt())
                                .orderBy("nama")
                                .get()
                                .addOnSuccessListener {result ->
                                    val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                    var hasil = ""
                                    for (doc in result) {
                                        hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                                    }
                                    if (hasil=="") {
                                        hasil+="Data Pencarian Tidak Ditemukan"
                                    }
                                    txtHasil.text = hasil
                                }
                        } else if (editnama=="" && editnim=="" && editipk!="") {  // cari berdasarkan ipk saja
                            db.collection("mahasiswa")
                                .whereEqualTo("ipk",editipk.toDouble())
                                .orderBy("nama")
                                .get()
                                .addOnSuccessListener {result ->
                                    val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                    var hasil = ""
                                    for (doc in result) {
                                        hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                                    }
                                    if (hasil=="") {
                                        hasil+="Data Pencarian Tidak Ditemukan"
                                    }
                                    txtHasil.text = hasil
                                }
                        } else if (editnama!="" && editnim!="" && editipk=="") {  // cari berdasarkan nama dan nim saja
                            db.collection("mahasiswa")
                                .whereEqualTo("nama",editnama)
                                .whereEqualTo("nim",editnim.toInt())
                                .orderBy("nama")
                                .get()
                                .addOnSuccessListener {result ->
                                    val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                    var hasil = ""
                                    for (doc in result) {
                                        hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                                    }
                                    if (hasil=="") {
                                        hasil+="Data Pencarian Tidak Ditemukan"
                                    }
                                    txtHasil.text = hasil
                                }
                        } else if (editnama!="" && editnim!="" && editipk!="") {  // cari berdasarkan nama, nim dan ipk
                            db.collection("mahasiswa")
                                .whereEqualTo("nama",editnama)
                                .whereEqualTo("nim",editnim.toInt())
                                .whereEqualTo("ipk",editipk.toDouble())
                                .orderBy("nama")
                                .get()
                                .addOnSuccessListener {result ->
                                    val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                    var hasil = ""
                                    for (doc in result) {
                                        hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                                    }
                                    if (hasil=="") {
                                        hasil+="Data Pencarian Tidak Ditemukan"
                                    }
                                    txtHasil.text = hasil
                                }
                        } else if (editnama!="" && editnim=="" && editipk!="") { // cari berdasarkan nama dan ipk saja
                            querycheck = "select nama, nim from mahasiswa"
                            db.collection("mahasiswa")
                                .whereEqualTo("nama",editnama)
                                .whereEqualTo("ipk",editipk.toDouble())
                                .orderBy("nama")
                                .get()
                                .addOnSuccessListener {result ->
                                    val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                    var hasil = ""
                                    for (doc in result) {
                                        hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                                    }
                                    if (hasil=="") {
                                        hasil+="Data Pencarian Tidak Ditemukan"
                                    }
                                    txtHasil.text = hasil
                                }
                        } else if (editnama=="" && editnim!="" && editipk!="") { // cari berdasarkan nim dan ipk saja
                            db.collection("mahasiswa")
                                .whereEqualTo("nim",editnim.toInt())
                                .whereEqualTo("ipk",editipk.toDouble())
                                .orderBy("nama")
                                .get()
                                .addOnSuccessListener {result ->
                                    val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                    var hasil = ""
                                    for (doc in result) {
                                        hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                                    }
                                    if (hasil=="") {
                                        hasil+="Data Pencarian Tidak Ditemukan"
                                    }
                                    txtHasil.text = hasil
                                }
                        }
                    } else if (parent.getItemAtPosition(pos) == "Nama Descanding") {
                        if (editnama!="" && editnim=="" && editipk=="") {  // cari berdasarkan nama saja
                            db.collection("mahasiswa")
                                .whereEqualTo("nama",editnama)
                                .orderBy("nama",Query.Direction.DESCENDING)
                                .get()
                                .addOnSuccessListener {result ->
                                    val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                    var hasil = ""
                                    for (doc in result) {
                                        hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                                    }
                                    if (hasil=="") {
                                        hasil+="Data Pencarian Tidak Ditemukan"
                                    }
                                    txtHasil.text = hasil
                                }
                        } else if (editnama=="" && editnim!="" && editipk=="") {  // cari berdasarkan nim saja
                            db.collection("mahasiswa")
                                .whereEqualTo("nim",editnim.toInt())
                                .orderBy("nama",Query.Direction.DESCENDING)
                                .get()
                                .addOnSuccessListener {result ->
                                    val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                    var hasil = ""
                                    for (doc in result) {
                                        hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                                    }
                                    if (hasil=="") {
                                        hasil+="Data Pencarian Tidak Ditemukan"
                                    }
                                    txtHasil.text = hasil
                                }
                        } else if (editnama=="" && editnim=="" && editipk!="") {  // cari berdasarkan ipk saja
                            db.collection("mahasiswa")
                                .whereEqualTo("ipk",editipk.toDouble())
                                .orderBy("nama",Query.Direction.DESCENDING)
                                .get()
                                .addOnSuccessListener {result ->
                                    val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                    var hasil = ""
                                    for (doc in result) {
                                        hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                                    }
                                    if (hasil=="") {
                                        hasil+="Data Pencarian Tidak Ditemukan"
                                    }
                                    txtHasil.text = hasil
                                }
                        } else if (editnama!="" && editnim!="" && editipk=="") {  // cari berdasarkan nama dan nim saja
                            db.collection("mahasiswa")
                                .whereEqualTo("nama",editnama)
                                .whereEqualTo("nim",editnim.toInt())
                                .orderBy("nama",Query.Direction.DESCENDING)
                                .get()
                                .addOnSuccessListener {result ->
                                    val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                    var hasil = ""
                                    for (doc in result) {
                                        hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                                    }
                                    if (hasil=="") {
                                        hasil+="Data Pencarian Tidak Ditemukan"
                                    }
                                    txtHasil.text = hasil
                                }
                        } else if (editnama!="" && editnim!="" && editipk!="") {  // cari berdasarkan nama, nim dan ipk
                            db.collection("mahasiswa")
                                .whereEqualTo("nama",editnama)
                                .whereEqualTo("nim",editnim.toInt())
                                .whereEqualTo("ipk",editipk.toDouble())
                                .orderBy("nama",Query.Direction.DESCENDING)
                                .get()
                                .addOnSuccessListener {result ->
                                    val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                    var hasil = ""
                                    for (doc in result) {
                                        hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                                    }
                                    if (hasil=="") {
                                        hasil+="Data Pencarian Tidak Ditemukan"
                                    }
                                    txtHasil.text = hasil
                                }
                        } else if (editnama!="" && editnim=="" && editipk!="") { // cari berdasarkan nama dan ipk saja
                            querycheck = "select nama, nim from mahasiswa"
                            db.collection("mahasiswa")
                                .whereEqualTo("nama",editnama)
                                .whereEqualTo("ipk",editipk.toDouble())
                                .orderBy("nama",Query.Direction.DESCENDING)
                                .get()
                                .addOnSuccessListener {result ->
                                    val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                    var hasil = ""
                                    for (doc in result) {
                                        hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                                    }
                                    if (hasil=="") {
                                        hasil+="Data Pencarian Tidak Ditemukan"
                                    }
                                    txtHasil.text = hasil
                                }
                        } else if (editnama=="" && editnim!="" && editipk!="") { // cari berdasarkan nim dan ipk saja
                            db.collection("mahasiswa")
                                .whereEqualTo("nim",editnim.toInt())
                                .whereEqualTo("ipk",editipk.toDouble())
                                .orderBy("nama",Query.Direction.DESCENDING)
                                .get()
                                .addOnSuccessListener {result ->
                                    val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                    var hasil = ""
                                    for (doc in result) {
                                        hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                                    }
                                    if (hasil=="") {
                                        hasil+="Data Pencarian Tidak Ditemukan"
                                    }
                                    txtHasil.text = hasil
                                }
                        }
                    }else if (parent.getItemAtPosition(pos) == "NIM Ascending") {
                        if (editnama!="" && editnim=="" && editipk=="") {  // cari berdasarkan nama saja
                            db.collection("mahasiswa")
                                .whereEqualTo("nama",editnama)
                                .orderBy("nim")
                                .get()
                                .addOnSuccessListener {result ->
                                    val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                    var hasil = ""
                                    for (doc in result) {
                                        hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                                    }
                                    if (hasil=="") {
                                        hasil+="Data Pencarian Tidak Ditemukan"
                                    }
                                    txtHasil.text = hasil
                                }
                        } else if (editnama=="" && editnim!="" && editipk=="") {  // cari berdasarkan nim saja
                            db.collection("mahasiswa")
                                .whereEqualTo("nim",editnim.toInt())
                                .orderBy("nim")
                                .get()
                                .addOnSuccessListener {result ->
                                    val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                    var hasil = ""
                                    for (doc in result) {
                                        hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                                    }
                                    if (hasil=="") {
                                        hasil+="Data Pencarian Tidak Ditemukan"
                                    }
                                    txtHasil.text = hasil
                                }
                        } else if (editnama=="" && editnim=="" && editipk!="") {  // cari berdasarkan ipk saja
                            db.collection("mahasiswa")
                                .whereEqualTo("ipk",editipk.toDouble())
                                .orderBy("nim")
                                .get()
                                .addOnSuccessListener {result ->
                                    val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                    var hasil = ""
                                    for (doc in result) {
                                        hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                                    }
                                    if (hasil=="") {
                                        hasil+="Data Pencarian Tidak Ditemukan"
                                    }
                                    txtHasil.text = hasil
                                }
                        } else if (editnama!="" && editnim!="" && editipk=="") {  // cari berdasarkan nama dan nim saja
                            db.collection("mahasiswa")
                                .whereEqualTo("nama",editnama)
                                .whereEqualTo("nim",editnim.toInt())
                                .orderBy("nim")
                                .get()
                                .addOnSuccessListener {result ->
                                    val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                    var hasil = ""
                                    for (doc in result) {
                                        hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                                    }
                                    if (hasil=="") {
                                        hasil+="Data Pencarian Tidak Ditemukan"
                                    }
                                    txtHasil.text = hasil
                                }
                        } else if (editnama!="" && editnim!="" && editipk!="") {  // cari berdasarkan nama, nim dan ipk
                            db.collection("mahasiswa")
                                .whereEqualTo("nama",editnama)
                                .whereEqualTo("nim",editnim.toInt())
                                .whereEqualTo("ipk",editipk.toDouble())
                                .orderBy("nim")
                                .get()
                                .addOnSuccessListener {result ->
                                    val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                    var hasil = ""
                                    for (doc in result) {
                                        hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                                    }
                                    if (hasil=="") {
                                        hasil+="Data Pencarian Tidak Ditemukan"
                                    }
                                    txtHasil.text = hasil
                                }
                        } else if (editnama!="" && editnim=="" && editipk!="") { // cari berdasarkan nama dan ipk saja
                            querycheck = "select nama, nim from mahasiswa"
                            db.collection("mahasiswa")
                                .whereEqualTo("nama",editnama)
                                .whereEqualTo("ipk",editipk.toDouble())
                                .orderBy("nim")
                                .get()
                                .addOnSuccessListener {result ->
                                    val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                    var hasil = ""
                                    for (doc in result) {
                                        hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                                    }
                                    if (hasil=="") {
                                        hasil+="Data Pencarian Tidak Ditemukan"
                                    }
                                    txtHasil.text = hasil
                                }
                        } else if (editnama=="" && editnim!="" && editipk!="") { // cari berdasarkan nim dan ipk saja
                            db.collection("mahasiswa")
                                .whereEqualTo("nim",editnim.toInt())
                                .whereEqualTo("ipk",editipk.toDouble())
                                .orderBy("nim")
                                .get()
                                .addOnSuccessListener {result ->
                                    val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                    var hasil = ""
                                    for (doc in result) {
                                        hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                                    }
                                    if (hasil=="") {
                                        hasil+="Data Pencarian Tidak Ditemukan"
                                    }
                                    txtHasil.text = hasil
                                }
                        }
                    } else if (parent.getItemAtPosition(pos) == "NIM Descanding") {
                        if (editnama!="" && editnim=="" && editipk=="") {  // cari berdasarkan nama saja
                            db.collection("mahasiswa")
                                .whereEqualTo("nama",editnama)
                                .orderBy("nim",Query.Direction.DESCENDING)
                                .get()
                                .addOnSuccessListener {result ->
                                    val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                    var hasil = ""
                                    for (doc in result) {
                                        hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                                    }
                                    if (hasil=="") {
                                        hasil+="Data Pencarian Tidak Ditemukan"
                                    }
                                    txtHasil.text = hasil
                                }
                        } else if (editnama=="" && editnim!="" && editipk=="") {  // cari berdasarkan nim saja
                            db.collection("mahasiswa")
                                .whereEqualTo("nim",editnim.toInt())
                                .orderBy("nim",Query.Direction.DESCENDING)
                                .get()
                                .addOnSuccessListener {result ->
                                    val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                    var hasil = ""
                                    for (doc in result) {
                                        hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                                    }
                                    if (hasil=="") {
                                        hasil+="Data Pencarian Tidak Ditemukan"
                                    }
                                    txtHasil.text = hasil
                                }
                        } else if (editnama=="" && editnim=="" && editipk!="") {  // cari berdasarkan ipk saja
                            db.collection("mahasiswa")
                                .whereEqualTo("ipk",editipk.toDouble())
                                .orderBy("nim",Query.Direction.DESCENDING)
                                .get()
                                .addOnSuccessListener {result ->
                                    val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                    var hasil = ""
                                    for (doc in result) {
                                        hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                                    }
                                    if (hasil=="") {
                                        hasil+="Data Pencarian Tidak Ditemukan"
                                    }
                                    txtHasil.text = hasil
                                }
                        } else if (editnama!="" && editnim!="" && editipk=="") {  // cari berdasarkan nama dan nim saja
                            db.collection("mahasiswa")
                                .whereEqualTo("nama",editnama)
                                .whereEqualTo("nim",editnim.toInt())
                                .orderBy("nim",Query.Direction.DESCENDING)
                                .get()
                                .addOnSuccessListener {result ->
                                    val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                    var hasil = ""
                                    for (doc in result) {
                                        hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                                    }
                                    if (hasil=="") {
                                        hasil+="Data Pencarian Tidak Ditemukan"
                                    }
                                    txtHasil.text = hasil
                                }
                        } else if (editnama!="" && editnim!="" && editipk!="") {  // cari berdasarkan nama, nim dan ipk
                            db.collection("mahasiswa")
                                .whereEqualTo("nama",editnama)
                                .whereEqualTo("nim",editnim.toInt())
                                .whereEqualTo("ipk",editipk.toDouble())
                                .orderBy("nim",Query.Direction.DESCENDING)
                                .get()
                                .addOnSuccessListener {result ->
                                    val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                    var hasil = ""
                                    for (doc in result) {
                                        hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                                    }
                                    if (hasil=="") {
                                        hasil+="Data Pencarian Tidak Ditemukan"
                                    }
                                    txtHasil.text = hasil
                                }
                        } else if (editnama!="" && editnim=="" && editipk!="") { // cari berdasarkan nama dan ipk saja
                            querycheck = "select nama, nim from mahasiswa"
                            db.collection("mahasiswa")
                                .whereEqualTo("nama",editnama)
                                .whereEqualTo("ipk",editipk.toDouble())
                                .orderBy("nim",Query.Direction.DESCENDING)
                                .get()
                                .addOnSuccessListener {result ->
                                    val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                    var hasil = ""
                                    for (doc in result) {
                                        hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                                    }
                                    if (hasil=="") {
                                        hasil+="Data Pencarian Tidak Ditemukan"
                                    }
                                    txtHasil.text = hasil
                                }
                        } else if (editnama=="" && editnim!="" && editipk!="") { // cari berdasarkan nim dan ipk saja
                            db.collection("mahasiswa")
                                .whereEqualTo("nim",editnim.toInt())
                                .whereEqualTo("ipk",editipk.toDouble())
                                .orderBy("nim",Query.Direction.DESCENDING)
                                .get()
                                .addOnSuccessListener {result ->
                                    val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                    var hasil = ""
                                    for (doc in result) {
                                        hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                                    }
                                    if (hasil=="") {
                                        hasil+="Data Pencarian Tidak Ditemukan"
                                    }
                                    txtHasil.text = hasil
                                }
                        }
                    } else if (parent.getItemAtPosition(pos) == "IPK Ascending") {
                        if (editnama!="" && editnim=="" && editipk=="") {  // cari berdasarkan nama saja
                            db.collection("mahasiswa")
                                .whereEqualTo("nama",editnama)
                                .orderBy("ipk")
                                .get()
                                .addOnSuccessListener {result ->
                                    val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                    var hasil = ""
                                    for (doc in result) {
                                        hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                                    }
                                    if (hasil=="") {
                                        hasil+="Data Pencarian Tidak Ditemukan"
                                    }
                                    txtHasil.text = hasil
                                }
                        } else if (editnama=="" && editnim!="" && editipk=="") {  // cari berdasarkan nim saja
                            db.collection("mahasiswa")
                                .whereEqualTo("nim",editnim.toInt())
                                .orderBy("ipk")
                                .get()
                                .addOnSuccessListener {result ->
                                    val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                    var hasil = ""
                                    for (doc in result) {
                                        hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                                    }
                                    if (hasil=="") {
                                        hasil+="Data Pencarian Tidak Ditemukan"
                                    }
                                    txtHasil.text = hasil
                                }
                        } else if (editnama=="" && editnim=="" && editipk!="") {  // cari berdasarkan ipk saja
                            db.collection("mahasiswa")
                                .whereEqualTo("ipk",editipk.toDouble())
                                .orderBy("ipk")
                                .get()
                                .addOnSuccessListener {result ->
                                    val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                    var hasil = ""
                                    for (doc in result) {
                                        hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                                    }
                                    if (hasil=="") {
                                        hasil+="Data Pencarian Tidak Ditemukan"
                                    }
                                    txtHasil.text = hasil
                                }
                        } else if (editnama!="" && editnim!="" && editipk=="") {  // cari berdasarkan nama dan nim saja
                            db.collection("mahasiswa")
                                .whereEqualTo("nama",editnama)
                                .whereEqualTo("nim",editnim.toInt())
                                .orderBy("ipk")
                                .get()
                                .addOnSuccessListener {result ->
                                    val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                    var hasil = ""
                                    for (doc in result) {
                                        hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                                    }
                                    if (hasil=="") {
                                        hasil+="Data Pencarian Tidak Ditemukan"
                                    }
                                    txtHasil.text = hasil
                                }
                        } else if (editnama!="" && editnim!="" && editipk!="") {  // cari berdasarkan nama, nim dan ipk
                            db.collection("mahasiswa")
                                .whereEqualTo("nama",editnama)
                                .whereEqualTo("nim",editnim.toInt())
                                .whereEqualTo("ipk",editipk.toDouble())
                                .orderBy("ipk")
                                .get()
                                .addOnSuccessListener {result ->
                                    val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                    var hasil = ""
                                    for (doc in result) {
                                        hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                                    }
                                    if (hasil=="") {
                                        hasil+="Data Pencarian Tidak Ditemukan"
                                    }
                                    txtHasil.text = hasil
                                }
                        } else if (editnama!="" && editnim=="" && editipk!="") { // cari berdasarkan nama dan ipk saja
                            querycheck = "select nama, nim from mahasiswa"
                            db.collection("mahasiswa")
                                .whereEqualTo("nama",editnama)
                                .whereEqualTo("ipk",editipk.toDouble())
                                .orderBy("ipk")
                                .get()
                                .addOnSuccessListener {result ->
                                    val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                    var hasil = ""
                                    for (doc in result) {
                                        hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                                    }
                                    if (hasil=="") {
                                        hasil+="Data Pencarian Tidak Ditemukan"
                                    }
                                    txtHasil.text = hasil
                                }
                        } else if (editnama=="" && editnim!="" && editipk!="") { // cari berdasarkan nim dan ipk saja
                            db.collection("mahasiswa")
                                .whereEqualTo("nim",editnim.toInt())
                                .whereEqualTo("ipk",editipk.toDouble())
                                .orderBy("ipk")
                                .get()
                                .addOnSuccessListener {result ->
                                    val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                    var hasil = ""
                                    for (doc in result) {
                                        hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                                    }
                                    if (hasil=="") {
                                        hasil+="Data Pencarian Tidak Ditemukan"
                                    }
                                    txtHasil.text = hasil
                                }
                        }
                    } else if (parent.getItemAtPosition(pos) == "IPK Descanding") {
                        if (editnama!="" && editnim=="" && editipk=="") {  // cari berdasarkan nama saja
                            db.collection("mahasiswa")
                                .whereEqualTo("nama",editnama)
                                .orderBy("ipk",Query.Direction.DESCENDING)
                                .get()
                                .addOnSuccessListener {result ->
                                    val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                    var hasil = ""
                                    for (doc in result) {
                                        hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                                    }
                                    if (hasil=="") {
                                        hasil+="Data Pencarian Tidak Ditemukan"
                                    }
                                    txtHasil.text = hasil
                                }
                        } else if (editnama=="" && editnim!="" && editipk=="") {  // cari berdasarkan nim saja
                            db.collection("mahasiswa")
                                .whereEqualTo("nim",editnim.toInt())
                                .orderBy("ipk",Query.Direction.DESCENDING)
                                .get()
                                .addOnSuccessListener {result ->
                                    val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                    var hasil = ""
                                    for (doc in result) {
                                        hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                                    }
                                    if (hasil=="") {
                                        hasil+="Data Pencarian Tidak Ditemukan"
                                    }
                                    txtHasil.text = hasil
                                }
                        } else if (editnama=="" && editnim=="" && editipk!="") {  // cari berdasarkan ipk saja
                            db.collection("mahasiswa")
                                .whereEqualTo("ipk",editipk.toDouble())
                                .orderBy("ipk",Query.Direction.DESCENDING)
                                .get()
                                .addOnSuccessListener {result ->
                                    val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                    var hasil = ""
                                    for (doc in result) {
                                        hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                                    }
                                    if (hasil=="") {
                                        hasil+="Data Pencarian Tidak Ditemukan"
                                    }
                                    txtHasil.text = hasil
                                }
                        } else if (editnama!="" && editnim!="" && editipk=="") {  // cari berdasarkan nama dan nim saja
                            db.collection("mahasiswa")
                                .whereEqualTo("nama",editnama)
                                .whereEqualTo("nim",editnim.toInt())
                                .orderBy("ipk",Query.Direction.DESCENDING)
                                .get()
                                .addOnSuccessListener {result ->
                                    val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                    var hasil = ""
                                    for (doc in result) {
                                        hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                                    }
                                    if (hasil=="") {
                                        hasil+="Data Pencarian Tidak Ditemukan"
                                    }
                                    txtHasil.text = hasil
                                }
                        } else if (editnama!="" && editnim!="" && editipk!="") {  // cari berdasarkan nama, nim dan ipk
                            db.collection("mahasiswa")
                                .whereEqualTo("nama",editnama)
                                .whereEqualTo("nim",editnim.toInt())
                                .whereEqualTo("ipk",editipk.toDouble())
                                .orderBy("ipk",Query.Direction.DESCENDING)
                                .get()
                                .addOnSuccessListener {result ->
                                    val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                    var hasil = ""
                                    for (doc in result) {
                                        hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                                    }
                                    if (hasil=="") {
                                        hasil+="Data Pencarian Tidak Ditemukan"
                                    }
                                    txtHasil.text = hasil
                                }
                        } else if (editnama!="" && editnim=="" && editipk!="") { // cari berdasarkan nama dan ipk saja
                            querycheck = "select nama, nim from mahasiswa"
                            db.collection("mahasiswa")
                                .whereEqualTo("nama",editnama)
                                .whereEqualTo("ipk",editipk.toDouble())
                                .orderBy("ipk",Query.Direction.DESCENDING)
                                .get()
                                .addOnSuccessListener {result ->
                                    val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                    var hasil = ""
                                    for (doc in result) {
                                        hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                                    }
                                    if (hasil=="") {
                                        hasil+="Data Pencarian Tidak Ditemukan"
                                    }
                                    txtHasil.text = hasil
                                }
                        } else if (editnama=="" && editnim!="" && editipk!="") { // cari berdasarkan nim dan ipk saja
                            db.collection("mahasiswa")
                                .whereEqualTo("nim",editnim.toInt())
                                .whereEqualTo("ipk",editipk.toDouble())
                                .orderBy("ipk",Query.Direction.DESCENDING)
                                .get()
                                .addOnSuccessListener {result ->
                                    val txtHasil = findViewById<TextView>(R.id.txtHasil)
                                    var hasil = ""
                                    for (doc in result) {
                                        hasil += "${doc.get("nama")} NIM ${doc.get("nim")} IPK : ${doc.get("ipk")}\n"
                                    }
                                    if (hasil=="") {
                                        hasil+="Data Pencarian Tidak Ditemukan"
                                    }
                                    txtHasil.text = hasil
                                }
                        }
                    }
                }
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}