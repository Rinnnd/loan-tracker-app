package com.loantracker.loantracker
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "loans")
data class Loan(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val platformName: String,        // 借款平台名称
    val repaymentDay: Int,           // 还款日（几号）
    val monthlyAmount: Double,       // 月还款金额
    val isPaid: Boolean,            // 本月是否已还款
    val createdAt: Long             // 创建时间戳
)