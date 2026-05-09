// 网页版APP的增强功能
// 本地数据存储（使用localStorage）
const STORAGE_KEY = 'loan_tracker_data';
function saveData() {
    const data = {
        loans: loans,
        settlementDay: settlementDay,
        lastUpdate: new Date().toISOString()
    };
    localStorage.setItem(STORAGE_KEY, JSON.stringify(data));
    console.log('数据已保存到本地存储');
}
function loadData() {
    const saved = localStorage.getItem(STORAGE_KEY);
    if (saved) {
        try {
            const data = JSON.parse(saved);
            loans = data.loans || [];
            settlementDay = data.settlementDay || 1;
            console.log('从本地存储加载数据');
        } catch (e) {
            console.error('加载数据失败:', e);
            initDefaultData();
        }
    } else {
        initDefaultData();
    }
    updateDisplay();
}
function initDefaultData() {
    loans = [
        { id: 1, name: "房贷", day: 15, amount: 3500, paid: false },
        { id: 2, name: "车贷", day: 20, amount: 2500, paid: false },
        { id: 3, name: "消费贷", day: 10, amount: 1200, paid: true }
    ];
    settlementDay = 1;
}
// 修改togglePaid函数，自动保存
function togglePaid(id) {
    const loan = loans.find(l => l.id === id);
    if (loan) {
        loan.paid = !loan.paid;
        updateDisplay();
        saveData();
    }
}
// 修改markAllPaid函数，自动保存
function markAllPaid() {
    loans.forEach(loan => {
        loan.paid = true;
    });
    updateDisplay();
    saveData();
}
// 修改addLoan函数，自动保存
function addLoan() {
    const name = prompt("请输入借款平台名称（如：房贷、车贷）：");
    if (!name) return;
    
    const day = parseInt(prompt("请输入每月还款日（1-31号）："));
    if (!day || day < 1 || day > 31) {
        alert("还款日必须是1-31号");
        return;
    }
    
    const amount = parseFloat(prompt("请输入每月还款金额："));
    if (!amount || amount <= 0) {
        alert("金额必须大于0");
        return;
    }
    
    const newId = loans.length > 0 ? loans[loans.length - 1].id + 1 : 1;
    loans.push({
        id: newId,
        name: name,
        day: day,
        amount: amount,
        paid: false
    });
    
    updateDisplay();
    saveData();
}
// 修改saveSettings函数，自动保存
function saveSettings() {
    const input = document.getElementById('settlement-input');
    const newDay = parseInt(input.value);
    if (newDay && newDay >= 1 && newDay <= 31) {
        settlementDay = newDay;
        alert("设置已保存！");
        updateDisplay();
        saveData();
    } else {
        alert("请输入1-31之间的数字");
    }
}
// 初始化时加载数据
window.addEventListener('DOMContentLoaded', function() {
    loadData();
    showTab('summary');
});