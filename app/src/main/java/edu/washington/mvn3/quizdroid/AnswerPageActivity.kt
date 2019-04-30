package edu.washington.mvn3.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class AnswerPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer_page)

        val givenAns = findViewById<TextView>(R.id.givenAns)
        val correctAns = findViewById<TextView>(R.id.correctAns)
        val scoreText = findViewById<TextView>(R.id.scoreText)
        val finishButton = findViewById<Button>(R.id.finishButton)

        val questions = intent.getStringArrayExtra("questions")
        val answers = intent.getStringArrayExtra("answers")
        val options = intent.getStringArrayExtra("options")
        val index = intent.getIntExtra("index", 0)
        val given = intent.getIntExtra("given", 0)
        val correctScore = intent.getIntExtra("correctScore", 0)
        val totalScore = intent.getIntExtra("totalScore", 0)

        givenAns.text = "Given Answer: " + options[4*index + given]
        correctAns.text = "Correct Answer: " + options[answers[index].toInt()]
        scoreText.text = "You have " + correctScore + " out of " + totalScore + " correct."
        if (index + 1 < questions.size) {
            finishButton.text = "Next"
            finishButton.setOnClickListener {
                val intent = Intent(this, QuizPageActivity::class.java)
                intent.putExtra("questions", questions)
                intent.putExtra("options", options)
                intent.putExtra("answers", answers)
                intent.putExtra("index", index+1)
                intent.putExtra("correctScore", correctScore)
                intent.putExtra("totalScore", totalScore)
                startActivity(intent)
            }
        } else {
            finishButton.text = "Finish"
            finishButton.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }


    }
}
