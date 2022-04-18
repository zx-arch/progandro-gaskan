package id.ac.ukdw.pertemuan7_71190424

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listContact = ArrayList<Contact>()
        listContact.add(Contact("List Contact","Ryo Altius Benito",R.mipmap.benito_pp,"0852223245","ryo.altius@ti.ukdw.ac.id"))
        listContact.add(Contact("","Mishael Elian Dharmawan",R.mipmap.elian_pp,"08522233245","mishael.elian@ti.ukdw.ac.id"))
        listContact.add(Contact("","Yacinthus Dheka Pratomo Putro",R.mipmap.dheka_pp,"08522443245","yacinthus.dheka@ti.ukdw.ac.id"))

        val rcyContact = findViewById<RecyclerView>(R.id.rcyContact)
        rcyContact.layoutManager = LinearLayoutManager(this)
        val ContactAdapter = ContactAdapter(listContact,this)
        rcyContact.adapter = ContactAdapter

    }
}