package edu.washington.mvn3.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView

class QuizPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_page)

        val questions = intent.getStringArrayExtra("questions")
        val options = intent.getStringArrayExtra("options")
        val answers = intent.getStringArrayExtra("answers")

        val qLabel = findViewById<TextView>(R.id.questionText)
        val o1 = findViewById<RadioButton>(R.id.radio2)
        val o2 = findViewById<RadioButton>(R.id.radio3)
        val o3 = findViewById<RadioButton>(R.id.radio1)
        val o4 = findViewById<RadioButton>(R.id.radio4)
        val radios = arrayOf(o1, o2, o3, o4)
        val submitButton = findViewById<Button>(R.id.submitButton)

        var index = intent.getIntExtra("index", 0)
        initPage(questions, options, answers, qLabel, radios, submitButton, index)

    }

    fun initPage(questions: Array<String>, options: Array<String>, answers: Array<String>, qLabel: TextView,
        radios: Array<RadioButton>, submitButton: Button, index: Int) {
        var numCorrect = intent.getIntExtra("correctScore", 0)
        var numTotal = intent.getIntExtra("totalScore", 0)
        qLabel.text = questions[index]
        radios[0].text = options[index*4]
        radios[1].text = options[index*4+1]
        radios[2].text = options[index*4+2]
        radios[3].text = options[index*4+3]
        submitButton.isEnabled = false

        radios[0].setOnClickListener { submitButton.isEnabled = true }
        radios[1].setOnClickListener { submitButton.isEnabled = true }
        radios[2].setOnClickListener { submitButton.isEnabled = true }
        radios[3].setOnClickListener { submitButton.isEnabled = true }

        submitButton.setOnClickListener {
            val ans = answers[index].toInt()
            var boxChecked = 0
            for (i in 0..3) {
                if (radios[i].isChecked) {
                    boxChecked = i
                    break
                }
            }
            if (boxChecked == ans) {
                // right answer
                numCorrect += 1
            }
            numTotal += 1

            val intent = Intent(this, AnswerPageActivity::class.java)
            intent.putExtra("questions", questions)
            intent.putExtra("options", options)
            intent.putExtra("answers", answers)
            intent.putExtra("index", index)
            intent.putExtra("given", boxChecked)
            intent.putExtra("correctScore", numCorrect)
            intent.putExtra("totalScore", numTotal)
            startActivity(intent)
        }
    }
}
