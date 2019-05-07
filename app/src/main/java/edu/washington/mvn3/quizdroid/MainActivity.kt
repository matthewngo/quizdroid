package edu.washington.mvn3.quizdroid

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val topics = QuizApp.stateRepository.getTopics()
        val adapter = ArrayAdapter(this, R.layout.listview_item, topics)
        val listView = findViewById<ListView>(R.id.topicList)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            // position = 0..2
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("topic", position)
            startActivity(intent)
        }
    }
}
