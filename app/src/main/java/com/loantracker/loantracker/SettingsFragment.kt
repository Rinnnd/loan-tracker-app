package com.loantracker.loantracker
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.loantracker.loantracker.databinding.FragmentSettingsBinding
class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit sharedPreferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val mainActivity = activity as MainActivity
        sharedPreferences = mainActivity.sharedPreferences
        // 显示当前设置
        val currentCutoff = mainActivity.getCutoffDay()
        binding.etCutoffDay.setText(currentCutoff.toString())
        // 保存按钮
        binding.btnSaveSettings.setOnClickListener {
            val cutoffStr = binding.etCutoffDay.text.toString().trim()
            val cutoffDay = cutoffStr.toIntOrNull()
            if (cutoffDay == null || cutoffDay <= 0 || cutoffDay > 31) {
                Toast.makeText(context, "请输入1-31之间的有效日期", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            sharedPreferences.edit().putInt(MainActivity.KEY_CUTOFF_DAY, cutoffDay).apply()
            Toast.makeText(context, "设置已保存", Toast.LENGTH_SHORT).show()
        }
        binding.tvAbout.text = """
            贷款还款记账APP v1.0
            
            功能说明：
            1. 支持添加多个借款平台
            2. 分别设置每个月还款日和还款金额
            3. 自动计算总待还和本月待还
            4. 可自定义每月结算日期
            5. 勾选标记已完成还款，数据自动同步
            6. 支持批量操作
        """.trimIndent()
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}