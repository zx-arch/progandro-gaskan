package id.ac.ukdw.pertemuan6_71190424

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment

class FragmentAPage3 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val i = inflater.inflate(R.layout.fragment_a_page3,container,false)
        val btnpage1 = i.findViewById<Button>(R.id.button_page_1)

        btnpage1.setOnClickListener {
            val intent = Intent (this@FragmentAPage3.context, MainActivity::class.java)
            startActivity(intent)
        }
        return i
    }
}