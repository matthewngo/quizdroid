package edu.washington.mvn3.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val topics = arrayOf("Math", "Physics", "Marvel Super Heroes")
        val adapter = ArrayAdapter(this, R.layout.listview_item, topics)
        val listView = findViewById<ListView>(R.id.topicList)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            // position = 0..2
            val intent = Intent(this, MathOverviewActivity::class.java)
            intent.putExtra("topic", position)
            startActivity(intent)
        }

    }
}