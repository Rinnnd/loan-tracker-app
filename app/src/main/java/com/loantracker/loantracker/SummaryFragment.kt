package com.loantracker.loantracker
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.loantracker.loantracker.databinding.FragmentSummaryBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Calendar
class SummaryFragment : Fragment() {
    private var _binding: FragmentSummaryBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSummaryBinding.inflate(inflater, container, false)
        val mainActivity = activity as MainActivity
        val db = AppDatabaseProvider.getDatabase(mainActivity)
        val calendar = Calendar.getInstance()
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        val cutoffDay = mainActivity.getCutoffDay()
        // 观察全部待还款
        lifecycleScope.launch {
            db.loanDao().getTotalUnpaid().collectLatest { total ->
                val totalAmount = total ?: 0.0
                binding.tvTotalUnpaid.text = "¥ %.2f".format(totalAmount)
            }
        }
        // 观察本月待还款
        lifecycleScope.launch {
            db.loanDao().getThisMonthUnpaid(cutoffDay, currentDay).collectLatest { thisMonth ->
                val thisMonthAmount = thisMonth ?: 0.0
                binding.tvThisMonthUnpaid.text = "¥ %.2f".format(thisMonthAmount)
            }
        }
        binding.tvCutoffDay.text = "结算日设置: 每月$cutoffDay 日"
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}