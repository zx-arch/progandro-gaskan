package id.ac.ukdw.pertemuan10_71190424

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private var dbhelper: SQLiteOpenHelper?= null
    private var db: SQLiteDatabase? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbhelper= DatabaseHelper(this)
        db=dbhelper?.writableDatabase

        val btnSimpan= findViewById<Button>(R.id.btnSimpan)
        val btnHapus= findViewById<Button>(R.id.btnhapus)
        val btnCari= findViewById<Button>(R.id.btnCari)
        val editNama= findViewById<EditText>(R.id.editNama)
        val editUsia= findViewById<EditText>(R.id.editUsia)
        val columns = arrayOf(
            BaseColumns._ID,
            DatabaseContact.Penduduk.COLUMN_NAME_NAMA,
            DatabaseContact.Penduduk.COLUMN_NAME_USIA
        )

        fun selectAll(): Cursor? {
            return db?.query(
                DatabaseContact.Penduduk.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null
            )
        }

        fun selectBySearch(): Cursor? {
            return db?.query(
                DatabaseContact.Penduduk.TABLE_NAME,
                columns,
                "nama = ? OR usia = ?",
                arrayOf(editNama.text.toString(),editUsia.text.toString()),
                null,
                null,
                null
            )
        }

        btnSimpan.setOnClickListener {
            if (editNama.text.toString()=="" || editUsia.text.toString()=="") {
                Toast.makeText(this,"Nama dan Usia Harus Diisi",Toast.LENGTH_LONG).show()
            } else {
                val values: ContentValues=ContentValues().apply {
                    put(DatabaseContact.Penduduk.COLUMN_NAME_NAMA,editNama.text.toString())
                    put(DatabaseContact.Penduduk.COLUMN_NAME_USIA,editUsia.text.toString())
                }
                db?.insert(DatabaseContact.Penduduk.TABLE_NAME, null, values)
                editNama.setText("")
                editUsia.setText("")
                selectAll()?.let { show(it) }
            }
        }

        btnHapus.setOnClickListener {
            if (editNama.text.toString()=="" && editUsia.text.toString()=="") {
                Toast.makeText(this,"Tidak Ditemukan Data yang Akan Dihapus",Toast.LENGTH_LONG).show()
            } else {
                val selection = "nama = ? OR usia = ?"
                val selectionArgs = arrayOf(editNama.text.toString(), editUsia.text.toString())
                db?.delete(DatabaseContact.Penduduk.TABLE_NAME, selection, selectionArgs)
                editNama.setText("")
                editUsia.setText("")
                selectAll()?.let { show(it) }
            }
        }
        btnCari.setOnClickListener {
            if (editNama.text.toString()=="" && editUsia.text.toString()=="") {
                Toast.makeText(this,"Isikan Nama atau Usia yang Ingin Dicari",Toast.LENGTH_LONG).show()
            }
            else {
                selectBySearch()?.let { show(it) }
                val txtHasil= findViewById<TextView>(R.id.txtHasil)
                if (txtHasil.text.toString()=="") {
                    txtHasil.text = "Data Pencarian Tidak Ditemukan"
                }
                editNama.setText("")
                editUsia.setText("")
            }
        }
        selectAll()?.let { show(it) }
    }

    @SuppressLint("Range", "Recycle")
    fun show(query : Cursor) {
        var result=""
        with(query){
            while (this.moveToNext()){
                result += getString(getColumnIndex(DatabaseContact.Penduduk.COLUMN_NAME_NAMA)) +
                        " umur ${getString(getColumnIndex(DatabaseContact.Penduduk.COLUMN_NAME_USIA))} thn\n"
            }
            val txtHasil= findViewById<TextView>(R.id.txtHasil)
            txtHasil.text=result
        }
    }
}