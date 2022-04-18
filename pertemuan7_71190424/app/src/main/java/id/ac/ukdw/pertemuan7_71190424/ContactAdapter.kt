package id.ac.ukdw.pertemuan7_71190424

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(val listcontact: ArrayList<Contact>,var context: Context) : RecyclerView.Adapter<ContactAdapter.ContactHolder>() {
    class ContactHolder(val v: View) : RecyclerView.ViewHolder(v) {
        var contact: Contact? = null

        fun bindView(contact: Contact,context: Context) {
            this.contact = contact
            v.findViewById<TextView>(R.id.listcontact).text = contact.listcontact
            v.findViewById<TextView>(R.id.contactname).text = contact.name
            v.findViewById<ImageView>(R.id.profil_contact).setImageResource(contact.profil)
            val btndetail = v.findViewById<Button>(R.id.detail)
            v.setOnClickListener {
                val i: Intent = Intent(v.context, DetailPage::class.java)
                i.putExtra("foto",contact.profil)
                i.putExtra("nama",contact.name)
                i.putExtra("nomor",contact.nomor)
                context.startActivity(i)
            }
            btndetail.setOnClickListener {
                val i: Intent = Intent(v.context, DetailPage::class.java)
                i.putExtra("foto",contact.profil)
                i.putExtra("nama",contact.name)
                i.putExtra("nomor",contact.nomor)
                context.startActivity(i)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_contact,parent,false)
        return ContactHolder(v)
    }

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        holder.bindView(listcontact[position],context)
    }

    override fun getItemCount(): Int = listcontact.size
}