package com.example.pageswitching

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // 步驟 01：宣告 ActivityResultLauncher 作為 Activity 啟動器
    private lateinit var startSecActivityLauncher: ActivityResultLauncher<Intent>

    private lateinit var etInput: EditText
    private lateinit var btnSend: Button
    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etInput = findViewById(R.id.etInput)
        btnSend = findViewById(R.id.btnSend)
        tvResult = findViewById(R.id.tvResult)

        // 步驟 01：註冊 ActivityResultLauncher
        startSecActivityLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            // 步驟 05：透過 ActivityResultLauncher 接收回傳的資料，並更新到對應的 TextView 畫面上
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                val replyText = data?.getStringExtra("REPLY_MESSAGE")
                if (!replyText.isNullOrEmpty()) {
                    tvResult.text = "接收到的回傳訊息：$replyText"
                }
            }
        }

        btnSend.setOnClickListener {
            val inputText = etInput.text.toString()

            // 步驟 02：將輸入的文字放入 Intent 與 Bundle 中，並使用 ActivityResultLauncher 發送資料並前往 SecActivity
            val bundle = Bundle().apply {
                putString("INPUT_MESSAGE", inputText)
            }
            val intent = Intent(this, SecActivity::class.java).apply {
                putExtras(bundle)
            }
            startSecActivityLauncher.launch(intent)
        }
    }
}
