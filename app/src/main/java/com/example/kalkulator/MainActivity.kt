package com.example.kalkulator

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat

class MainActivity : Activity() {

    private lateinit var tvResult: TextView
    private var currentInput: String = ""
    private var operator: String = ""
    private var operand1: Double = 0.0
    private var operand2: Double = 0.0
    private var result: Double = 0.0
    private var isOperatorPressed: Boolean = false
    private var currentOperatorButton: Button? = null // Menyimpan referensi tombol operator yang sedang aktif


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvResult = findViewById(R.id.tvResult)

        // Number buttons
        val buttons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )

        buttons.forEach { id ->
            findViewById<Button>(id).setOnClickListener { onNumberClick((it as Button).text.toString()) }
        }

        // Operator buttons
        findViewById<Button>(R.id.btnAdd).setOnClickListener { onOperatorClick(it as Button,"+") }
        findViewById<Button>(R.id.btnSubtract).setOnClickListener { onOperatorClick(it as Button,"-") }
        findViewById<Button>(R.id.btnMultiply).setOnClickListener { onOperatorClick(it as Button,"*") }
        findViewById<Button>(R.id.btnDivide).setOnClickListener { onOperatorClick(it as Button,"/") }

        // Special buttons
        findViewById<Button>(R.id.btnEqual).setOnClickListener { onEqualClick() }
        findViewById<Button>(R.id.btnClear).setOnClickListener { onClearClick() }
        findViewById<Button>(R.id.btnDecimal).setOnClickListener { onDecimalClick() }
        findViewById<Button>(R.id.btnOff).setOnClickListener { finish() } // OFF button closes the app
    }

    // number button clicks
    private fun onNumberClick(number: String) {
        if (isOperatorPressed) {
            currentInput = number
            isOperatorPressed = false
        } else {
            currentInput += number
        }
        tvResult.text = currentInput
    }

    // operator button clicks
    private fun onOperatorClick(button: Button, op: String) {
        if (currentInput.isNotEmpty()) {
            operand1 = currentInput.toDouble()
            operator = op
            isOperatorPressed = true
            highlightOperatorButton(button)
        }
    }

    //  equal button click
    private fun onEqualClick() {
        if (currentInput.isNotEmpty() && operator.isNotEmpty()) {
            operand2 = currentInput.toDouble()

            result = when (operator) {
                "+" -> operand1 + operand2
                "-" -> operand1 - operand2
                "*" -> operand1 * operand2
                "/" -> {
                    if (operand2 != 0.0) {
                        operand1 / operand2
                    } else {
                        tvResult.text = "Error"
                        return
                    }
                }
                else -> 0.0
            }
            tvResult.text = result.toString()
            currentInput = result.toString()
            resetOperatorButtonColor()
        }
    }

    //  decimal button click
    private fun onDecimalClick() {
        if (!currentInput.contains(".")) {
            currentInput += "."
            tvResult.text = currentInput
        }
    }

    //  clear button click
    private fun onClearClick() {
        currentInput = ""
        operand1 = 0.0
        operand2 = 0.0
        operator = ""
        result = 0.0
        tvResult.text = "0"
    }

    private fun highlightOperatorButton(button: Button) {
        // Reset warna button
        resetOperatorButtonColor()

        // Ubah warna ketika button diklik
        button.setBackgroundColor(ContextCompat.getColor(this, R.color.operator_active))

        currentOperatorButton = button
    }

    private fun resetOperatorButtonColor() {
        currentOperatorButton?.let {
            it.setBackgroundColor(ContextCompat.getColor(this, R.color.operator_default))
        }
        currentOperatorButton = null
    }
}
