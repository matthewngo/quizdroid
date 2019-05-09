package edu.washington.mvn3.quizdroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Spinner
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView


class PreferenceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference)

        val spinner = findViewById<Spinner>(R.id.numSpinner)
        val items = arrayOf(10, 30, 60, 120, 240, 480)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        spinner.setAdapter(adapter)

        val urlText = findViewById<TextView>(R.id.urlTextBox)
        val updateButton = findViewById<Button>(R.id.updateButton)

        updateButton.setOnClickListener {
            val urlString = urlText.text
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
