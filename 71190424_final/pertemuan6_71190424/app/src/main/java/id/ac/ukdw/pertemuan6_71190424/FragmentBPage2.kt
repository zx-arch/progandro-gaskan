package id.ac.ukdw.pertemuan6_71190424

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment

class FragmentBPage2 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val i = inflater.inflate(R.layout.fragment_b_page2,container,false)
        val btnpage3 = i.findViewById<Button>(R.id.button_page_3)

        btnpage3.setOnClickListener {
            val intent = Intent (this@FragmentBPage2.context, ThirdActivity::class.java)
            startActivity(intent)
        }
        return i
    }
}