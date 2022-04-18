package id.ac.ukdw.pertemuan7_71190424

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(val listcontact: ArrayList<Contact>) : RecyclerView.Adapter<ContactAdapter.ContactHolder>() {
    class ContactHolder(val v: View) : RecyclerView.ViewHolder(v) {
        var contact: Contact? = null

        fun bindView(contact: Contact) {
            this.contact = contact
            v.findViewById<TextView>(R.id.listcontact).text = contact.listcontact
            v.findViewById<TextView>(R.id.contactname).text = contact.name
            v.findViewById<ImageView>(R.id.profil_contact).setImageResource(contact.profil)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_contact,parent,false)
        return ContactHolder(v)
    }

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        holder.bindView(listcontact[position])
    }

    override fun getItemCount(): Int = listcontact.size
}