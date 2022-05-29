package id.ac.ukdw.pertemuan12_71190424

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val edtKota = findViewById<EditText>(R.id.inputkota)
        val btnCari = findViewById<Button>(R.id.carikota)
        btnCari.setOnClickListener {
            cekCuaca(edtKota.text.toString())
        }
    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun cekCuaca(namakota: String) {
        val txtresult = findViewById<TextView>(R.id.txtresult)
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.openweathermap.org/data/2.5/forecast?q=${namakota}&lang=id&&APPID=4b88302c11200467b6f034b5204d7aa7&units=metric"
        val request = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                val weatherjson = JSONObject(response).getJSONArray("list")
                var str = ""
                val cuacasekarang = weatherjson.getJSONObject(1).getJSONArray("weather").getJSONObject(0).getString("description")
                val cuacabesok = weatherjson.getJSONObject(9).getJSONArray("weather").getJSONObject(0).getString("description")
                val cuacalusa = weatherjson.getJSONObject(17).getJSONArray("weather").getJSONObject(0).getString("description")
                str += "Cuaca di $namakota Sekarang : $cuacasekarang\nCuaca di $namakota Besok : $cuacabesok\nCuaca di $namakota Lusa : $cuacalusa"
                txtresult.text = str
            },
            {
                txtresult.text = "Tidak Ada Nama Kota $namakota"
            }
        )
        queue.add(request)
    }
}