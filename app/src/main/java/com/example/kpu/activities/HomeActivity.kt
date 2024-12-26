package com.example.kpu.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kpu.R
import com.example.kpu.data.AppDatabase
import com.example.kpu.VoterAdapter
import com.example.kpu.VoterDetailActivity
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Setup RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Setup Database and VoterDao
        val database = AppDatabase.getDatabase(this)
        val voterDao = database.voterDao()

        // Observe Voter List
        voterDao.getAllVoters().observe(this, Observer { voters ->
            val adapter = VoterAdapter(this, voters, { voter ->
                // Aksi Edit: Klik pada ikon edit untuk membuka halaman Edit
                val intent = Intent(this, AddVoterActivity::class.java)
                intent.putExtra("voter_id", voter.id)
                startActivity(intent)
            }, { voter ->
                // Aksi Hapus: Klik pada ikon hapus untuk menghapus data
                lifecycleScope.launch {
                    try {
                        voterDao.delete(voter) // Menghapus data
                        Log.d("HomeActivity", "Data voter berhasil dihapus")
                    } catch (e: Exception) {
                        Log.e("HomeActivity", "Error deleting voter: ${e.message}")
                    }
                }
            }, { voter ->
                // Aksi Mata (Detail): Klik pada ikon mata untuk melihat detail
                val intent = Intent(this, VoterDetailActivity::class.java)
                intent.putExtra("voter_id", voter.id)  // Pass ID voter to Detail Activity
                startActivity(intent)
            })
            recyclerView.adapter = adapter
        })

        // Tombol Tambah Data
        findViewById<Button>(R.id.btnTambahData).setOnClickListener {
            Log.d("HomeActivity", "Tombol Tambah Data diklik")
            startActivity(Intent(this, AddVoterActivity::class.java))
        }

        // Tombol Logout
        findViewById<Button>(R.id.btnLogout).setOnClickListener {
            Log.d("HomeActivity", "Logout diklik")
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}