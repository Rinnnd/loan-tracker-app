package com.loantracker.loantracker.data
import androidx.room.Database
import androidx.room.RoomDatabase
@Database(entities = [Loan::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun loanDao(): LoanDao
}