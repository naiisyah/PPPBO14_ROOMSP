package com.example.kpu.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.kpu.R
import com.example.kpu.data.VoterEntity
import com.example.kpu.data.AppDatabase
import kotlinx.coroutines.launch

class AddVoterActivity : AppCompatActivity() {
    private lateinit var etNama: EditText
    private lateinit var etNIK: EditText
    private lateinit var rgGender: RadioGroup
    private lateinit var rbLakiLaki: RadioButton
    private lateinit var rbPerempuan: RadioButton
    private lateinit var etAlamat: EditText
    private lateinit var btnSimpan: Button

    private var voterId: Int = -1  // Default ID yang berarti data baru

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_voter)

        // Inisialisasi elemen UI
        etNama = findViewById(R.id.etNama)
        etNIK = findViewById(R.id.etNIK)
        rgGender = findViewById(R.id.rgGender)
        rbLakiLaki = findViewById(R.id.rbLakiLaki)
        rbPerempuan = findViewById(R.id.rbPerempuan)
        etAlamat = findViewById(R.id.etAlamat)
        btnSimpan = findViewById(R.id.btnSimpan)

        // Ambil ID pemilih dari Intent
        val voterId = intent.getIntExtra("voter_id", -1)
        if (voterId != -1) {
            this.voterId = voterId
            loadVoterData(voterId)
        }

        // Tombol Simpan
        btnSimpan.setOnClickListener {
            val name = etNama.text.toString()
            val nik = etNIK.text.toString()
            val gender = if (rbLakiLaki.isChecked) "Laki-laki" else "Perempuan"
            val address = etAlamat.text.toString()

            val voter = VoterEntity(voterId, name, nik, gender, address)

            lifecycleScope.launch {
                if (voterId == -1) {
                    // Menambahkan data baru
                    AppDatabase.getDatabase(applicationContext).voterDao().insert(voter)
                } else {
                    // Memperbarui data yang sudah ada
                    AppDatabase.getDatabase(applicationContext).voterDao().update(voter)
                }
                finish()  // Kembali ke HomeActivity setelah simpan/update
            }
        }
    }

    private fun loadVoterData(voterId: Int) {
        lifecycleScope.launch {
            val voter = AppDatabase.getDatabase(applicationContext).voterDao().getVoterById(voterId)
            voter?.let {
                etNama.setText(it.name)
                etNIK.setText(it.nik)
                etAlamat.setText(it.address)
                if (it.gender == "Laki-laki") {
                    rbLakiLaki.isChecked = true
                } else {
                    rbPerempuan.isChecked = true
                }
            }
        }
    }
}