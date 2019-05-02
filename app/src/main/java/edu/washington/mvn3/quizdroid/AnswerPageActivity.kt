package edu.washington.mvn3.quizdroid

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class AnswerPageActivity : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        container!!.removeAllViews()
        return inflater.inflate(R.layout.activity_answer_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val givenAns = getView()!!.findViewById<TextView>(R.id.givenAns)
        val correctAns = getView()!!.findViewById<TextView>(R.id.correctAns)
        val scoreText = getView()!!.findViewById<TextView>(R.id.scoreText)
        val finishButton = getView()!!.findViewById<Button>(R.id.finishButton)

        val questions = arguments!!.getStringArray("questions")
        val answers = arguments!!.getStringArray("answers")
        val options = arguments!!.getStringArray("options")
        val index = arguments!!.getInt("index")
        val given = arguments!!.getInt("given")
        val correctScore = arguments!!.getInt("correctScore")
        val totalScore = arguments!!.getInt("totalScore")

        givenAns.text = "Given Answer: " + options[4*index + given]
        correctAns.text = "Correct Answer: " + options[answers[index].toInt()]
        scoreText.text = "You have " + correctScore + " out of " + totalScore + " correct."
        if (index + 1 < questions.size) {
            finishButton.text = "Next"
            finishButton.setOnClickListener {
                val args = Bundle()
                args.putStringArray("questions", questions)
                args.putStringArray("options", options)
                args.putStringArray("answers", answers)
                args.putInt("index", index+1)
                args.putInt("correctScore", correctScore)
                args.putInt("totalScore", totalScore)

                val fragment = QuizPageActivity()
                fragment.arguments = args

                val transaction = fragmentManager!!.beginTransaction()
                transaction.replace(R.id.frameLayout, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        } else {
            finishButton.text = "Finish"
            finishButton.setOnClickListener {
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
