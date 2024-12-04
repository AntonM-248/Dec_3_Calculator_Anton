package com.creators.dec_3_calculator_relativelayout_anton

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.creators.dec_3_calculator_relativelayout_anton.databinding.ActivityMainBinding
import org.mariuszgromada.math.mxparser.Expression

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val tag = "MainActivity Debugging"
    private val toastDuration = Toast.LENGTH_SHORT
    private val divideOperator = "/"
    private val percentageOperator = "#"
    private val multiplyOperator = "*"
    private val plusOperator = "+"
    private val minusOperator = "-"
    private val decimalOperator = "."

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(tag, "Result is: ${Expression("5 % 3").calculate()}")

        val viewModel: CalculatorViewModel by viewModels()

        binding.apply{
            inputBox.text = viewModel.getToComputeString()

            acButton.setOnClickListener{
                viewModel.clearToComputeString()
                inputBox.text = viewModel.getToComputeString()
            }

            percentButton.setOnClickListener {
                if(viewModel.canCalculatePercentage()){
                    viewModel.addToComputeString(percentageOperator)
                    viewModel.computeResult()
                    inputBox.text = viewModel.getToComputeString()
                }
                else {
                    showToast("The current input is not valid for the percentage operator")
                }
            }

            divideButton.setOnClickListener {
                if(viewModel.canAddOperator()) {
                    viewModel.addToComputeString(divideOperator)
                    inputBox.text = viewModel.getToComputeString()
                }
                else {
                    showToast("Cannot add a divide operator currently")
                }
            }

            multiplyButton.setOnClickListener {
                if(viewModel.canAddOperator()) {
                    viewModel.addToComputeString(multiplyOperator)
                    inputBox.text = viewModel.getToComputeString()
                }
                else {
                    showToast("Cannot add a multiply operator currently")
                }
            }

            plusButton.setOnClickListener {
                if(viewModel.canAddOperator()) {
                    viewModel.addToComputeString(plusOperator)
                    inputBox.text = viewModel.getToComputeString()
                }
                else {
                    showToast("Cannot add a plus operator currently")
                }
            }

            minusButton.setOnClickListener {
                if(viewModel.canAddOperator()) {
                    viewModel.addToComputeString(minusOperator)
                    inputBox.text = viewModel.getToComputeString()
                }
                else {
                    showToast("Cannot add a minus operator currently")
                }
            }

            equalsButton.setOnClickListener {
                if(viewModel.canComputeResult()){
                    viewModel.computeResult()
                    inputBox.text = viewModel.getToComputeString()
                }
                else {
                    showToast("Cannot compute result for current input. Input must end with a digit")
                }
            }

            decimalButton.setOnClickListener {
                if(viewModel.canAddDecimal()) {
                    viewModel.addToComputeString(decimalOperator)
                    inputBox.text = viewModel.getToComputeString()
                }
                else {
                    showToast("Cannot add a decimal currently")
                }
            }

            zeroButton.setOnClickListener {
                if(viewModel.canAddZero()){
                    viewModel.addToComputeString(zeroButton.text.toString())
                    inputBox.text = viewModel.getToComputeString()
                }
                else {
                    showToast("Cannot add a zero currently")
                }
            }

            val numberButtonList = listOf(oneButton, twoButton, threeButton, fourButton, fiveButton,
                    sixButton, sevenButton, eightButton, nineButton)

            for (button in numberButtonList) {
                button.setOnClickListener{
                    viewModel.addToComputeString(button.text.toString())
                    inputBox.text = viewModel.getToComputeString()
                }
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun showToast(textToShow: String) {
        Toast.makeText(this, textToShow, toastDuration).show()
    }
}