package com.example.pageswitching

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecActivity : AppCompatActivity() {


    private lateinit var tvReceived: TextView
    private lateinit var etReply: EditText
    private lateinit var btnReply: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sec)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvReceived = findViewById(R.id.tvReceived)
        etReply = findViewById(R.id.etReply)
        btnReply = findViewById(R.id.btnReply)

        // 接收從 MainActivity 傳遞過來的資料 (Intent 與 Bundle)
        val bundle = intent.extras
        val receivedMessage = bundle?.getString("INPUT_MESSAGE")
        if (!receivedMessage.isNullOrEmpty()) {
            tvReceived.text = "從第一頁收到的文字：$receivedMessage"
        }

        btnReply.setOnClickListener {
            val replyText = etReply.text.toString()

            // 步驟 03：將準備回傳的文字放入 Intent，並使用 setResult() 方法儲存要回傳的資料
            val resultIntent = Intent().apply {
                putExtra("REPLY_MESSAGE", replyText)
            }
            setResult(RESULT_OK, resultIntent)

            // 步驟 04：呼叫 setResult() 後，接著使用 finish() 方法結束 SecActivity，並自動回傳到 MainActivity
            finish()
        }
    }
}
