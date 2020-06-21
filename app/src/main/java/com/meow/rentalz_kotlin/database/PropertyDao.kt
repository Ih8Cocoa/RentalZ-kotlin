package com.meow.rentalz_kotlin.database

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PropertyDao {
    @Insert
    suspend fun insert(property: Property)

    @Update
    suspend fun update(property: Property)

    @Delete
    suspend fun delete(property: Property)

    @Query("delete from property_table")
    suspend fun deleteAll()

    @Query("select *, rowid from property_table order by rowid desc")
    fun allProperties(): LiveData<List<Property>>

    @Query("select *, rowid from property_table where rowid = :id")
    suspend fun findPropertyById(id: Long): Property

    @Query("SELECT *, rowid FROM property_table WHERE property_table MATCH :query order by rowid desc")
    fun searchProperties(query: String): Flow<Property>

    @Query("delete from property_table where rowid = :id")
    suspend fun deletePropertyById(id: Long)
}