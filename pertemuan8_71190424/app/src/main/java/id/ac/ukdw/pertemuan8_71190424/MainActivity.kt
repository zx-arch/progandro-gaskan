package id.ac.ukdw.pertemuan8_71190424

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.menu_toolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val pager = findViewById<ViewPager2>(R.id.pager)
        val listFragment: ArrayList<Fragment> = arrayListOf(EventActivity(),FolderActivity(),ContactActivity())

        val pagerAdapter = PagerAdapter(this, listFragment)
        pager.adapter = pagerAdapter
    }
    class PagerAdapter(val activity: AppCompatActivity, val listFragment: ArrayList<Fragment>): FragmentStateAdapter(activity) {
        override fun getItemCount(): Int = listFragment.size

        override fun createFragment(position: Int): Fragment = listFragment[position]
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_default,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.event -> Toast.makeText(this,"Event Log",Toast.LENGTH_LONG).show()
            R.id.folder -> Toast.makeText(this,"Folder",Toast.LENGTH_LONG).show()
            R.id.contact -> Toast.makeText(this,"Contact",Toast.LENGTH_LONG).show()
        }
        return true
    }
}