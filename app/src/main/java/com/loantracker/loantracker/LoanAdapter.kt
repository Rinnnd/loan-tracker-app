package com.loantracker.loantracker
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.loantracker.loantracker.databinding.ItemLoanBinding
class LoanAdapter(
    private val onCheckedChange: (Loan, Boolean) -> Unit
) : ListAdapter<Loan, LoanAdapter.LoanViewHolder>(DiffCallback) {
    companion object DiffCallback : DiffUtil.ItemCallback<Loan>() {
        override fun areItemsTheSame(oldItem: Loan, newItem: Loan): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Loan, newItem: Boolean): Boolean {
            return oldItem == newItem
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoanViewHolder {
        val binding = ItemLoanBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LoanViewHolder(binding)
    }
    override fun onBindViewHolder(holder: LoanViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    inner class LoanViewHolder(
        private val binding: ItemLoanBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loan: Loan) {
            binding.tvPlatformName.text = loan.platformName
            binding.tvRepaymentDay.text = "每月${loan.repaymentDay}日"
            binding.tvAmount.text = "¥ %.2f".format(loan.monthlyAmount)
            binding.cbIsPaid.isChecked = loan.isPaid
            binding.cbIsPaid.setOnCheckedChangeListener { _, isChecked ->
                if (binding.cbIsPaid.isPressed) {
                    onCheckedChange(loan, isChecked)
                }
            }
        }
    }
}