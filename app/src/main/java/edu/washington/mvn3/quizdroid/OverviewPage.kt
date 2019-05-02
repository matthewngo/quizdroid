package edu.washington.mvn3.quizdroid

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class OverviewPage : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        container!!.removeAllViews()
        return inflater.inflate(R.layout.activity_math_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        val topic = arguments!!.getInt("topic")
        val topicName = getView()!!.findViewById<TextView>(R.id.topicName)
        val topicDesc = getView()!!.findViewById<TextView>(R.id.topicDescription)

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

        getView()!!.findViewById<Button>(R.id.beginTest).setOnClickListener {
            val args = Bundle()
            args.putStringArray("questions", questions)
            args.putStringArray("options", options)
            args.putStringArray("answers", answers)
            args.putInt("index", 0)
            args.putInt("correctScore", 0)
            args.putInt("totalScore", 0)

            val fragment = QuizPage()
            fragment.arguments = args

            val transaction = fragmentManager!!.beginTransaction()
            transaction.replace(R.id.frameLayout, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

    }
}
