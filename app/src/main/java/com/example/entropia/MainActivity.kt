package com.example.entropia

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.ln

class MainActivity : AppCompatActivity() {
    private lateinit var editText: EditText
    private lateinit var emptyLabel: TextView
    private lateinit var textView7: TextView
    private lateinit var textView8: TextView
    private lateinit var twoNumTextText: EditText
    private lateinit var threeNumTextText: EditText
    private lateinit var fourNumTextText: EditText
    private lateinit var fiveNumTextText: EditText
    private lateinit var sixNumTextText: EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var entriphy = false
        var avaragaV = false

        emptyLabel = findViewById<TextView>(R.id.entropheLabel)
        editText = findViewById(R.id.editText)
        textView7 = findViewById(R.id.textView7)
        twoNumTextText = findViewById(R.id.twoNumTextText)
        threeNumTextText = findViewById(R.id.threeNumTextText)
        fourNumTextText = findViewById(R.id.fourNumTextText)
        fiveNumTextText = findViewById(R.id.fiveNumTextText)
        sixNumTextText = findViewById(R.id.sixNumTextText)

        textView8 = findViewById(R.id.textView8)

        val result: Button = findViewById(R.id.calculateButton)
        result.setOnClickListener {
            main(editText)
            if (avaragaV){
                var isb = (textView7.text.toString()).toDouble() -(emptyLabel.text.toString()).toDouble()
                textView8.text="Избыток = $isb"
            }
            entriphy = true
        }

        val avgResult: Button = findViewById(R.id.averageButton)
        avgResult.setOnClickListener {
            average()
            if (entriphy){
                var isb = (textView7.text.toString()).toDouble() -(emptyLabel.text.toString()).toDouble()
                textView8.text="Избыток = $isb"
            }
            avaragaV = true
        }
    }


    fun main(editText: EditText) {
        val input = editText.text.toString()
        val probabilities = parseProbabilities(input)

        if (probabilities != null) {
            val entropy = calculateEntropy(probabilities)
            emptyLabel.text = "$entropy"
        } else {
            emptyLabel.text = "Ошибка в формате ввода."
        }
    }

    fun parseProbabilities(input: String): List<Double>? {
        val regex = """\s+""".toRegex()
        val probabilities = input.trim().split(regex).map { it.toDoubleOrNull() }

        return probabilities?.filterNotNull()
    }

    fun calculateEntropy(probabilities: List<Double>): Double {
        var sum = 0.0
        probabilities.forEach { elem ->
            if (elem > 0) {
                sum -= elem * ln(elem) / ln(2.0)
            }
        }
        return sum
    }

    fun average() {
        val average1 = allCount()
        textView7.text = "$average1"
    }

    fun allCount(): Double {
        var sum = 0.0
        sum += calculateSum(parseProbabilities(twoNumTextText.text.toString()))*2
        sum += calculateSum(parseProbabilities(threeNumTextText.text.toString()))*3
        sum += calculateSum(parseProbabilities(fourNumTextText.text.toString()))*4
        sum += calculateSum(parseProbabilities(fiveNumTextText.text.toString()))*5
        sum += calculateSum(parseProbabilities(sixNumTextText.text.toString()))*6

        return sum
    }

    fun calculateSum(probabilities: List<Double>?): Double {
        return probabilities?.sum() ?: 0.0
    }
}