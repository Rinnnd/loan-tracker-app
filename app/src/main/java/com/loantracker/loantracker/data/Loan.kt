package com.loantracker.loantracker.data
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "loans")
data class Loan(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val platformName: String,        // 借款平台名称
    val monthlyAmount: Double,       // 每月还款金额
    val repaymentDay: Int,           // 还款日（几号）
    var isPaid: Boolean = false,     // 本月是否已还款
    val createTime: Long = System.currentTimeMillis()
)