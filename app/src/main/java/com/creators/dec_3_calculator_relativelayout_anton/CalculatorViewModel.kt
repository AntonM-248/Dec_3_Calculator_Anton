package com.creators.dec_3_calculator_relativelayout_anton

import android.util.Log
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import org.mariuszgromada.math.mxparser.Expression

class CalculatorViewModel: ViewModel() {
    //for logging
    private val TAG = "CalculatorViewModel debugging"

    //for functionality
    private var toComputeString: String = ""
    private val operators = listOf("+", "-", "*", "/")
    private val operatorsDivideIndex = 3
    private val decimalOperator = "."
    private val zeroString = "0"
    private val operatorCheckRegex = "\\+|-|*|/"

    fun canAddOperator(): Boolean {
        Log.d(TAG, "Can Add Operator fun: " + "To computeString is not empty: " + toComputeString.isNotEmpty()
                + ". Last is digit: " + toComputeString.last().isDigit())
        return toComputeString.isNotEmpty() && toComputeString.last().isDigit()
    }

    fun canAddDecimal(): Boolean {
        Log.d(TAG, "Can Add Decimal " + "To computeString is not empty: " + toComputeString.isNotEmpty()
                + ". Last does not have decimal: " + !toComputeString.split(operatorCheckRegex).last().contains(decimalOperator))
        return toComputeString.isNotEmpty()
                && !toComputeString.split(operatorCheckRegex).last().contains('.')
    }

    fun canCalculatePercentage(): Boolean {
        val canCalculatePercent: Boolean = toComputeString.split(operatorCheckRegex).size == 1
                && toComputeString.split(operatorCheckRegex).last().last().isDigit()
        Log.d(TAG, "Can calculate percent of $toComputeString: $canCalculatePercent")
        return canCalculatePercent
    }

    fun canAddZero(): Boolean {
        //can add zero if
        //last char is a non divide operator
        //last number is not just 0
        //last char has decimal
        val numbersArray = toComputeString.split(operatorCheckRegex)
        val lastNumberString = toComputeString.split(operatorCheckRegex).last()
        val computeStringNotEmpty = toComputeString.isNotEmpty()
        val computeStringLastCharIsOperatorButNotDivideOperator = computeStringNotEmpty
                && toComputeString.last().toString() in operators
                && toComputeString.last().toString() != operators[operatorsDivideIndex]
        val computeStringEqualsZero = computeStringNotEmpty
                && toComputeString.split(operatorCheckRegex).last() == zeroString
        val computeStringLastNumberHasDecimal = computeStringNotEmpty &&
                toComputeString.split(operatorCheckRegex).last().contains(decimalOperator)
        val canAddZero: Boolean = toComputeString.isEmpty()
                || computeStringLastCharIsOperatorButNotDivideOperator
                || !computeStringEqualsZero
                || computeStringLastNumberHasDecimal
        return canAddZero
    }

    fun canComputeResult(): Boolean {
        return toComputeString.last().isDigit()
    }

    fun getToComputeString(): String {
        Log.d(TAG, "ToComputeString: $toComputeString")
        return toComputeString
    }

    fun addToComputeString(stringToAdd: String) {
        Log.d(TAG, "Adding $stringToAdd to $toComputeString")
        toComputeString = toComputeString.plus(stringToAdd)
    }

    fun computeResult(): Double {
        val result = Expression(toComputeString).calculate()
        Log.d(TAG, "Computation result: $result")
        toComputeString = result.toString()
        return result
    }

    fun clearToComputeString() {
        toComputeString = ""
    }
}