package com.prashant.material3_compose_template.roomdb

import androidx.room.*

@Dao
interface DaoInterface {

    @Query("SELECT * FROM user")
    suspend fun getAll(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

    @Delete(User::class)
    suspend fun delete(user: User)

    @Query("SELECT * FROM user WHERE id = :id")
    suspend fun searchById(id: Long): User?

    @Query("SELECT * FROM user WHERE name LIKE '%' || :value || '%'")
    suspend fun searchByCharacter(value: String): User?

    @Query("SELECT * FROM user WHERE name LIKE '%' || :value || '%'")
    suspend fun searchByCharacterList(value: String): List<User>

}