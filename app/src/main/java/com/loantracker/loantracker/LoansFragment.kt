package com.loantracker.loantracker
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.loantracker.loantracker.databinding.FragmentLoansBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
class LoansFragment : Fragment() {
    private var _binding: FragmentLoansBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: LoanAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoansBinding.inflate(inflater, container, false)
        val mainActivity = activity as MainActivity
        val db = AppDatabaseProvider.getDatabase(mainActivity)
        adapter = LoanAdapter { loan, isPaid ->
            lifecycleScope.launch {
                db.loanDao().updatePaidStatus(loan.id, isPaid)
            }
        }
        binding.rvLoans.layoutManager = LinearLayoutManager(context)
        binding.rvLoans.adapter = adapter
        // 观察数据更新
        lifecycleScope.launch {
            db.loanDao().getAllLoans().collectLatest { loans ->
                adapter.submitList(loans)
            }
        }
        // 添加按钮
        binding.fabAdd.setOnClickListener {
            showAddLoanDialog { newLoan ->
                lifecycleScope.launch {
                    db.loanDao().insertLoan(newLoan)
                    Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show()
                }
            }
        }
        // 批量标记全部已还款按钮
        binding.btnMarkAllPaid.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("批量操作")
                .setMessage("确定要将所有借款标记为已还款吗？")
                .setPositiveButton("确定") { _, _ ->
                    lifecycleScope.launch {
                        db.loanDao().markAllAsPaid()
                        Toast.makeText(context, "已全部标记为已还款", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("取消", null)
                .show()
        }
        return binding.root
    }
    private fun showAddLoanDialog(onAdd: (Loan) -> Unit) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_loan, null)
        val etPlatform = dialogView.findViewById<EditText>(R.id.et_platform_name)
        val etDay = dialogView.findViewById<EditText>(R.id.et_repayment_day)
        val etAmount = dialogView.findViewById<EditText>(R.id.et_monthly_amount)
        AlertDialog.Builder(requireContext())
            .setTitle("添加借款")
            .setView(dialogView)
            .setPositiveButton("添加") { _, _ ->
                val platform = etPlatform.text.toString().trim()
                val dayStr = etDay.text.toString().trim()
                val amountStr = etAmount.text.toString().trim()
                if (platform.isEmpty() || dayStr.isEmpty() || amountStr.isEmpty()) {
                    Toast.makeText(context, "请填写完整信息", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                val day = dayStr.toIntOrNull()
                val amount = amountStr.toDoubleOrNull()
                if (day == null || amount == null || day <= 0 || day > 31 || amount <= 0) {
                    Toast.makeText(context, "输入格式不正确", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                val loan = Loan(
                    platformName = platform,
                    repaymentDay = day,
                    monthlyAmount = amount,
                    isPaid = false,
                    createdAt = System.currentTimeMillis()
                )
                onAdd(loan)
            }
            .setNegativeButton("取消", null)
            .show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}