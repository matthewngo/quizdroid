package edu.washington.mvn3.quizdroid

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.pref_toolbar))

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val intent = Intent(this, PreferenceActivity::class.java)
        startActivity(intent)
        return true
    }
}
