package com.loantracker.loantracker.data
import androidx.room.*
import kotlinx.coroutines.flow.Flow
@Dao
interface LoanDao {
    
    @Query("SELECT * FROM loans ORDER BY repaymentDay ASC")
    fun getAllLoans(): Flow<List<Loan>>
    
    @Query("SELECT SUM(monthlyAmount) FROM loans WHERE isPaid = 0")
    fun getTotalUnpaid(): Flow<Double?>
    
    @Query("SELECT * FROM loans WHERE id = :id")
    suspend fun getLoanById(id: Long): Loan?
    
    @Insert
    suspend fun insertLoan(loan: Loan): Long
    
    @Update
    suspend fun updateLoan(loan: Loan)
    
    @Delete
    suspend fun deleteLoan(loan: Loan)
    
    @Query("UPDATE loans SET isPaid = :isPaid")
    suspend fun updateAllPaidStatus(isPaid: Boolean)
    
    @Query("SELECT SUM(monthlyAmount) FROM loans WHERE isPaid = 0 AND " +
           "((strftime('%m', 'now') != strftime('%m', datetime(createTime/1000, 'unixepoch')))" +
           " OR (strftime('%Y', 'now') != strftime('%Y', datetime(createTime/1000, 'unixepoch'))) OR isPaid = 0)")
    fun getCurrentMonthUnpaid(): Flow<Double?>
}