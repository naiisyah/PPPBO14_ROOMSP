package com.example.kpu.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface VoterDao {
    @Query("SELECT * FROM voter_table WHERE id = :id")
    suspend fun getVoterById(id: Int): VoterEntity?

    @Delete
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(voter: VoterEntity)

    @Update
    suspend fun update(voter: VoterEntity) // Menambahkan update query

    @Query("SELECT * FROM voter_table")
    fun getAllVoters(): LiveData<List<VoterEntity>>

    suspend fun delete(voter: VoterEntity)  // Fungsi untuk menghapus data
}