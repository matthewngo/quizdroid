package edu.washington.mvn3.quizdroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class QuizActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val args = Bundle()
        args.putInt("topic", intent.getIntExtra("topic", 0))

        val fragment = OverviewPage()
        fragment.arguments = args

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
