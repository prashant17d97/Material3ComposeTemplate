package com.prashant.material3_compose_template.backup

import android.app.backup.*
import android.os.ParcelFileDescriptor
import android.util.Log
import java.io.File
import java.io.FileInputStream

class AppBackupAgent : BackupAgentHelper() {
    private val MY_PREFS_BACKUP_KEY = "my_prefs"
    private val MY_DB_BACKUP_KEY = "my_db"

    companion object {
        const val TAG = "AppBackupAgent"
    }

    override fun onCreate() {
        Log.e(TAG, "onCreate: ", )
        val prefBackupHelper = SharedPreferencesBackupHelper(this, "my_prefs_file")
        addHelper(MY_PREFS_BACKUP_KEY, prefBackupHelper)

        val dbBackupHelper = FileBackupHelper(this, "/databases/database.db")
        addHelper(MY_DB_BACKUP_KEY, dbBackupHelper)
    }


    override fun onBackup(
        oldState: ParcelFileDescriptor?,
        data: BackupDataOutput?,
        newState: ParcelFileDescriptor?
    ) {
        Log.e(TAG, "onBackup")


        // Backup shared preferences
        val prefsFile = File(filesDir, "../shared_prefs/my_prefs_file.xml")
        if (prefsFile.exists()) {
            val prefsStream = FileInputStream(prefsFile)
            val prefsData = prefsStream.readBytes()
            data?.writeEntityHeader(MY_PREFS_BACKUP_KEY, prefsData.size)
            data?.writeEntityData(prefsData, prefsData.size)
            prefsStream.close()
        }

        // Backup database
        val dbFile = File(filesDir, "../databases/my_database.db")
        if (dbFile.exists()) {
            val dbStream = FileInputStream(dbFile)
            val dbData = dbStream.readBytes()
            data?.writeEntityHeader(MY_DB_BACKUP_KEY, dbData.size)
            data?.writeEntityData(dbData, dbData.size)
            dbStream.close()
        }
    }

    override fun onRestore(
        data: BackupDataInput?,
        appVersionCode: Int,
        newState: ParcelFileDescriptor?
    ) {
        Log.e(TAG, "onRestore")

        while (data?.readNextHeader() == true) {
            val key = data.key
            val dataSize = data.dataSize
            Log.d(TAG, "Restoring key=$key, dataSize=$dataSize")

            when (key) {
                MY_PREFS_BACKUP_KEY -> {
                    val prefsData = ByteArray(dataSize)
                    data.readEntityData(prefsData, 0, dataSize)
                    val prefsFile = File(filesDir, "../shared_prefs/my_prefs_file.xml")
                    prefsFile.writeBytes(prefsData)
                }
                MY_DB_BACKUP_KEY -> {
                    val dbData = ByteArray(dataSize)
                    data.readEntityData(dbData, 0, dataSize)
                    val dbFile = File(filesDir, "../databases/my_database.db")
                    dbFile.writeBytes(dbData)
                }
            }
        }
    }
}