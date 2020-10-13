package com.budzter.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    // flags for the usage of the decimal point
    var lastNumeric = false
    var lastDot = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onDigit(view: View){
        tvInput.append((view as Button).text)
        lastNumeric = true
    }

    fun onClear(view: View){
        tvInput.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onDecimalPoint(view: View){
        if(lastNumeric && !lastDot){
            tvInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View){
        if(lastNumeric && !isOperatorExist(tvInput.text.toString())){
            tvInput.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    fun onEquals(view: View){
        if(lastNumeric){
            var tvValue = tvInput.text.toString()
            var prefix = ""

            // for negative numbers, removes the negative and save to a variable
            if(tvValue.startsWith("-")){
                prefix = "-"
                tvValue = tvValue.substring(1)
            }

            try {
                if(tvValue.contains("-")){
                    var (one, two) = tvValue.split("-")
                    // add the prefix to the number again as Int
                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }
                    tvInput.text = removeDotZero((one.toDouble() - two.toDouble()).toString())

                } else if(tvValue.contains("+")){
                    var (one, two) = tvValue.split("+")
                    // add the prefix to the number again as Int
                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }
                    tvInput.text = removeDotZero((one.toDouble() + two.toDouble()).toString())

                } else if(tvValue.contains("/")){
                    var (one, two) = tvValue.split("/")
                    // add the prefix to the number again as Int
                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }
                    tvInput.text = removeDotZero((one.toDouble() / two.toDouble()).toString())

                } else if(tvValue.contains("*")){
                    var (one, two) = tvValue.split("*")
                    // add the prefix to the number again as Int
                    if(!prefix.isEmpty()){
                        one = prefix + one
                    }
                    tvInput.text = removeDotZero((one.toDouble() * two.toDouble()).toString())

                }

            } catch (e: ArithmeticException){
                e.printStackTrace()
            }

        }
    }

    private fun isOperatorExist(valueStr: String) : Boolean{
        // checks for arithmetic that starts with a negative
        return if (valueStr.startsWith("-")){
            false
        } else {
            valueStr.contains("+") || valueStr.contains("-") ||
                    valueStr.contains("*") || valueStr.contains("/")
        }
    }
    
    private fun removeDotZero(resultStr: String) : String{
        if(resultStr.contains(".0")){
            return resultStr.substring(0, resultStr.length - 2)
        }
        return resultStr
    }
}