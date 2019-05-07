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

        val topic = arguments!!.getInt("topic")
        val topicName = getView()!!.findViewById<TextView>(R.id.topicName)
        val topicDesc = getView()!!.findViewById<TextView>(R.id.topicDescription)

        val qObjects = QuizApp.stateRepository.getTopic(topic).qObjects
        val qTemp = ArrayList<String>()
        val oTemp = ArrayList<String>()
        val aTemp = ArrayList<String>()
        for (obj in qObjects) {
            qTemp.add(obj.question)
            oTemp.addAll(obj.answers)
            aTemp.add(obj.correct.toString())
        }
        val questions = qTemp.toTypedArray()
        val options = oTemp.toTypedArray()
        val answers = aTemp.toTypedArray()

        topicName.text = QuizApp.stateRepository.getTopic(topic).title + " Overview"
        topicDesc.text = QuizApp.stateRepository.getTopic(topic).longDesc

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
