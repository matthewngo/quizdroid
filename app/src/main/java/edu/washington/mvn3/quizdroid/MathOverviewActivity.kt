package edu.washington.mvn3.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MathOverviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_math_overview)

        val names = arrayOf("Math", "Physics", "Marvel Super Heroes")
        val desc = arrayOf("Math", "Physics", "Marvel Super Heroes")
        val mQuestions = arrayOf("1+1", "1*1")
        val mOptions = arrayOf("0","1","2","3","99","1","98","97")
        // which option is correct from 0..3
        val mAnswers = arrayOf("2", "1")

        val pQuestions = arrayOf("What is one of Newton's Laws")
        val pOptions = arrayOf("f = ma","1+1=2","1+2=5","science")
        val pAnswers = arrayOf("0")

        val mhQuestions = arrayOf("Which is a Marvel Super Hero")
        val mhOptions = arrayOf("!","heh","nope","Iron Man")
        val mhAnswers = arrayOf("3")


        val topic = intent.getIntExtra("topic", 0)
        val topicName = findViewById<TextView>(R.id.topicName)
        val topicDesc = findViewById<TextView>(R.id.topicDescription)
        var num = 1
        var questions = mQuestions
        var options = mOptions
        var answers = mAnswers
        if (topic == 0) {
            // MATH OPTION
            num = mQuestions.size
            questions = mQuestions
            options = mOptions
            answers = mAnswers
        } else if (topic == 1) {
            // PHYSICS OPTION
            num = pQuestions.size
            questions = pQuestions
            options = pOptions
            answers = pAnswers
        } else if (topic == 2) {
            // HERO OPTION
            num = mhQuestions.size
            questions = mhQuestions
            options = mhOptions
            answers = mhAnswers
        }

        topicName.text = names[topic] + " Overview"
        topicDesc.text = "This quiz will test your knowledge of " + desc[topic] + " with " + num + " question(s)."

        findViewById<Button>(R.id.beginTest).setOnClickListener {
            val intent = Intent(this, QuizPageActivity::class.java)
            intent.putExtra("questions", questions)
            intent.putExtra("options", options)
            intent.putExtra("answers", answers)
            intent.putExtra("index", 0)
            intent.putExtra("correctScore", 0)
            intent.putExtra("totalScore", 0)
            startActivity(intent)
        }

    }
}
