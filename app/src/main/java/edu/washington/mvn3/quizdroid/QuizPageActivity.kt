package edu.washington.mvn3.quizdroid

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView

class QuizPageActivity : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        container!!.removeAllViews()
        return inflater.inflate(R.layout.activity_quiz_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val questions = arguments!!.getStringArray("questions")
        val options = arguments!!.getStringArray("options")
        val answers = arguments!!.getStringArray("answers")

        val qLabel = getView()!!.findViewById<TextView>(R.id.questionText)
        val o1 = getView()!!.findViewById<RadioButton>(R.id.radio2)
        val o2 = getView()!!.findViewById<RadioButton>(R.id.radio3)
        val o3 = getView()!!.findViewById<RadioButton>(R.id.radio1)
        val o4 = getView()!!.findViewById<RadioButton>(R.id.radio4)
        val radios = arrayOf(o1, o2, o3, o4)
        val submitButton = getView()!!.findViewById<Button>(R.id.submitButton)

        var index = arguments!!.getInt("index")
        initPage(questions, options, answers, qLabel, radios, submitButton, index)

    }

    fun initPage(questions: Array<String>?, options: Array<String>?, answers: Array<String>?, qLabel: TextView,
        radios: Array<RadioButton>, submitButton: Button, index: Int) {
        var numCorrect = arguments!!.getInt("correctScore")
        var numTotal = arguments!!.getInt("totalScore")
        qLabel.text = questions!![index]
        radios[0].text = options!![index*4]
        radios[1].text = options[index*4+1]
        radios[2].text = options[index*4+2]
        radios[3].text = options[index*4+3]
        submitButton.isEnabled = false

        radios[0].setOnClickListener { submitButton.isEnabled = true }
        radios[1].setOnClickListener { submitButton.isEnabled = true }
        radios[2].setOnClickListener { submitButton.isEnabled = true }
        radios[3].setOnClickListener { submitButton.isEnabled = true }

        submitButton.setOnClickListener {
            val ans = answers!![index].toInt()
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

            val args = Bundle()
            args.putStringArray("questions", questions)
            args.putStringArray("options", options)
            args.putStringArray("answers", answers)
            args.putInt("index", index)
            args.putInt("given", boxChecked)
            args.putInt("correctScore", numCorrect)
            args.putInt("totalScore", numTotal)

            val fragment = AnswerPageActivity()
            fragment.arguments = args

            val transaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.frameLayout, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
}
