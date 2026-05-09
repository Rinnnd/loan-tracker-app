package com.loantracker.loantracker
import androidx.room.*
import kotlinx.coroutines.flow.Flow
@Dao
interface LoanDao {
    @Query("SELECT * FROM loans ORDER BY repaymentDay ASC")
    fun getAllLoans(): Flow<List<Loan>>
    
    @Query("SELECT SUM(monthlyAmount) FROM loans WHERE isPaid = 0")
    fun getTotalUnpaid(): Flow<Double?>
    
    @Query("SELECT SUM(monthlyAmount) FROM loans WHERE isPaid = 0 AND ((repaymentDay <= :cutoffDay AND :currentDay <= :cutoffDay) OR (repaymentDay >= :cutoffDay AND :currentDay >= :cutoffDay) OR (repaymentDay <= :cutoffDay AND :currentDay >= :cutoffDay))")
    fun getThisMonthUnpaid(cutoffDay: Int, currentDay: Int): Flow<Double?>
    
    @Insert
    suspend fun insertLoan(loan: Loan): Long
    
    @Update
    suspend fun updateLoan(loan: loan)
    
    @Delete
    suspend fun deleteLoan(loan: Loan)
    
    @Query("UPDATE loans SET isPaid = :isPaid WHERE id = :id")
    suspend fun updatePaidStatus(id: Long, isPaid: Boolean)
    
    @Query("UPDATE loans SET isPaid = 0 WHERE ((repaymentDay <= :cutoffDay AND :currentDay <= :cutoffDay) OR (repaymentDay >= :cutoffDay AND :currentDay >= :cutoffDay) OR (repaymentDay <= :cutoffDay AND :currentDay >= :cutoffDay))")
    suspend fun resetMonthUnpaid(cutoffDay: Int, currentDay: Int)
    
    @Query("UPDATE loans SET isPaid = 1")
    suspend fun markAllAsPaid()
}