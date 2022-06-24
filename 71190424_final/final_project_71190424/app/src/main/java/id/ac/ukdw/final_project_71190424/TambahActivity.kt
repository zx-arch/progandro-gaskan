package id.ac.ukdw.final_project_71190424

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.*
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.IOException
import java.util.*

class TambahActivity : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    lateinit var imagePreview: ImageView
    lateinit var btn_uploadgambar: Button
    lateinit var btn_submit: Button
    private var linkPoster = ""
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambahdata)

        imagePreview = findViewById(R.id.addPoster)
        btn_uploadgambar = findViewById(R.id.btnUpload)
        btn_submit = findViewById(R.id.btnSubmit)
        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        val txtJudul = findViewById<EditText>(R.id.nm_judul)
        val txtGenre = findViewById<EditText>(R.id.genre)
        val txtProduser = findViewById<EditText>(R.id.addproduser)
        val txtPemeranUtamaSatu = findViewById<EditText>(R.id.nm_pemeransatu)
        val txtPemeranUtamaDua = findViewById<EditText>(R.id.nm_pemerandua)
        val txtPemeranUtamaTiga = findViewById<EditText>(R.id.nm_pemerantiga)
        val txtTahunRilis = findViewById<EditText>(R.id.addthn_rilis)
        btn_uploadgambar.setOnClickListener {
            launchGallery()
        }
        btn_submit.setOnClickListener {
            uploadImage()
            val film = Film(txtJudul.text.toString(),txtGenre.text.toString(),txtProduser.text.toString(),linkPoster,txtPemeranUtamaSatu.text.toString(),txtPemeranUtamaDua.text.toString(),txtPemeranUtamaTiga.text.toString(),txtTahunRilis.text.toString().toInt())
            db.collection("film").add(film)
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            Toast.makeText(this, "Data Berhasil Ditambah", Toast.LENGTH_SHORT).show()
            finish()
        }

    }

    private fun uploadImage() {
        if(filePath != null){
            val ref = storageReference?.child("myImages/" + UUID.randomUUID().toString() + ".jpg")
            if (ref != null) {
                linkPoster = "firebasestorage.googleapis.com/v0/b/"+ref.bucket.toString()+"/o/myImages%2F"+ref.name.toString()+"?alt=media"
            }
            val uploadTask = ref?.putFile(filePath!!)

        }else{
            Toast.makeText(this, "Please Upload an Image", Toast.LENGTH_SHORT).show()
        }

    }

    private fun launchGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE_REQUEST){
            if(data == null || data.data == null){
                return
            }
        }
        filePath = data?.data

        try {
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, filePath)

            imagePreview.setImageBitmap(bitmap)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}