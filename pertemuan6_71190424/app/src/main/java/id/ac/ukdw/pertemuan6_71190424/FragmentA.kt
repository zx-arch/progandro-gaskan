package id.ac.ukdw.pertemuan6_71190424

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

class FragmentA : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val i = inflater.inflate(R.layout.fragment_a,container,false)
        val btnpage2 = i.findViewById<Button>(R.id.button_page_2)

        btnpage2.setOnClickListener {
            val intent = Intent (this@FragmentA.context, SecondActivity::class.java)
            startActivity(intent)
        }
        return i
    }
}