package com.example.mycalculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // 1. 於類別頂部宣告所有 UI 元件變數
    private lateinit var processTextView: TextView
    private lateinit var resultTextView: TextView

    private lateinit var btn0: Button
    private lateinit var btn1: Button
    private lateinit var btn2: Button
    private lateinit var btn3: Button
    private lateinit var btn4: Button
    private lateinit var btn5: Button
    private lateinit var btn6: Button
    private lateinit var btn7: Button
    private lateinit var btn8: Button
    private lateinit var btn9: Button
    private lateinit var btnDot: Button

    private lateinit var btnPlus: Button
    private lateinit var btnMinus: Button
    private lateinit var btnMultiply: Button
    private lateinit var btnDivide: Button

    private lateinit var btnAC: Button
    private lateinit var btnC: Button
    private lateinit var btnBack: Button
    private lateinit var btnEqual: Button

    // 狀態變數
    private var currentInput: String = "0"
    private var firstOperand: Double? = null
    private var pendingOperator: String? = null
    private var isNewInput: Boolean = true
    private var processExpression: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 初始化元件與設定監聽器
        initViews()
        setupClickListeners()
        updateDisplay()
    }

    // 初始化所有 UI 元件
    private fun initViews() {
        processTextView = findViewById(R.id.processTextView)
        resultTextView = findViewById(R.id.resultTextView)

        btn0 = findViewById(R.id.btn0)
        btn1 = findViewById(R.id.btn1)
        btn2 = findViewById(R.id.btn2)
        btn3 = findViewById(R.id.btn3)
        btn4 = findViewById(R.id.btn4)
        btn5 = findViewById(R.id.btn5)
        btn6 = findViewById(R.id.btn6)
        btn7 = findViewById(R.id.btn7)
        btn8 = findViewById(R.id.btn8)
        btn9 = findViewById(R.id.btn9)
        btnDot = findViewById(R.id.btnDot)

        btnPlus = findViewById(R.id.btnPlus)
        btnMinus = findViewById(R.id.btnMinus)
        btnMultiply = findViewById(R.id.btnMultiply)
        btnDivide = findViewById(R.id.btnDivide)

        btnAC = findViewById(R.id.btnAC)
        btnC = findViewById(R.id.btnC)
        btnBack = findViewById(R.id.btnBack)
        btnEqual = findViewById(R.id.btnEqual)
    }

    // 設定按鈕點擊監聽器
    private fun setupClickListeners() {
        // 數字與小數點按鈕
        btn0.setOnClickListener { appendNumber("0") }
        btn1.setOnClickListener { appendNumber("1") }
        btn2.setOnClickListener { appendNumber("2") }
        btn3.setOnClickListener { appendNumber("3") }
        btn4.setOnClickListener { appendNumber("4") }
        btn5.setOnClickListener { appendNumber("5") }
        btn6.setOnClickListener { appendNumber("6") }
        btn7.setOnClickListener { appendNumber("7") }
        btn8.setOnClickListener { appendNumber("8") }
        btn9.setOnClickListener { appendNumber("9") }
        btnDot.setOnClickListener { appendNumber(".") }

        // 運算符按鈕
        btnPlus.setOnClickListener { setOperator("+") }
        btnMinus.setOnClickListener { setOperator("-") }
        btnMultiply.setOnClickListener { setOperator("×") }
        btnDivide.setOnClickListener { setOperator("÷") }

        // 功能按鈕
        btnEqual.setOnClickListener { calculateResult() }
        btnC.setOnClickListener { clearCurrentInput() }
        btnAC.setOnClickListener { clearAll() }
        btnBack.setOnClickListener { backspace() }
    }

    // 獨立函式：附加數字
    private fun appendNumber(digit: String) {
        if (isNewInput) {
            currentInput = if (digit == ".") "0." else digit
            isNewInput = false
        } else {
            if (digit == ".") {
                if (!currentInput.contains(".")) {
                    currentInput += "."
                }
            } else {
                if (currentInput == "0") {
                    currentInput = digit
                } else {
                    currentInput += digit
                }
            }
        }
        updateDisplay()
    }

    // 獨立函式：設定運算符
    private fun setOperator(op: String) {
        val inputValue = currentInput.toDoubleOrNull() ?: return

        if (firstOperand == null) {
            firstOperand = inputValue
        } else if (!isNewInput && pendingOperator != null) {
            val intermediateResult = compute(firstOperand!!, inputValue, pendingOperator!!)
            if (intermediateResult == null) {
                showError("Error")
                return
            }
            firstOperand = intermediateResult
            currentInput = formatResult(intermediateResult)
        }

        pendingOperator = op
        processExpression = "${formatResult(firstOperand!!)} $op"
        isNewInput = true
        updateDisplay()
    }

    // 獨立函式：計算結果
    private fun calculateResult() {
        val operator = pendingOperator ?: return
        val first = firstOperand ?: return
        val second = currentInput.toDoubleOrNull() ?: return

        val result = compute(first, second, operator)

        if (result == null) {
            showError("Error")
            return
        }

        processExpression = "${formatResult(first)} $operator ${formatResult(second)} ="
        currentInput = formatResult(result)
        firstOperand = result
        pendingOperator = null
        isNewInput = true
        updateDisplay()
    }

    // 使用 when 陳述式判斷運算符並進行四則運算
    private fun compute(first: Double, second: Double, operator: String): Double? {
        return when (operator) {
            "+" -> first + second
            "-" -> first - second
            "×" -> first * second
            "÷" -> {
                if (second == 0.0) {
                    null // 避免除以 0 導致錯誤
                } else {
                    first / second
                }
            }
            else -> null
        }
    }

    // 獨立函式：清除當前輸入 (C)
    private fun clearCurrentInput() {
        currentInput = "0"
        isNewInput = true
        updateDisplay()
    }

    // 獨立函式：全部清除 (AC)
    private fun clearAll() {
        currentInput = "0"
        firstOperand = null
        pendingOperator = null
        isNewInput = true
        processExpression = ""
        updateDisplay()
    }

    // 獨立函式：倒退鍵 (⌫)
    private fun backspace() {
        if (!isNewInput && currentInput.isNotEmpty()) {
            currentInput = currentInput.dropLast(1)
            if (currentInput.isEmpty() || currentInput == "-") {
                currentInput = "0"
                isNewInput = true
            }
            updateDisplay()
        }
    }

    // 獨立函式：更新顯示內容
    private fun updateDisplay() {
        processTextView.text = processExpression
        resultTextView.text = currentInput
    }

    // 顯示錯誤狀態
    private fun showError(message: String) {
        processTextView.text = processExpression
        resultTextView.text = message
        currentInput = "0"
        firstOperand = null
        pendingOperator = null
        isNewInput = true
    }

    // 格式化輸出結果 (若是整數則去除 .0)
    private fun formatResult(value: Double): String {
        return if (value % 1.0 == 0.0) {
            value.toLong().toString()
        } else {
            value.toString()
        }
    }
}