package edu.washington.mvn3.quizdroid

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.*
import android.widget.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.pref_toolbar))

        QuizApp.resetRepo()
        val topics = QuizApp.stateRepository.getTopics()
        val adapter = ArrayAdapter(this, R.layout.listview_item, topics)
        val listView = findViewById<ListView>(R.id.topicList)
        listView.adapter = adapter


        if (!connectedToInternet()) {
            if (Settings.System.getInt(contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) != 0) {
                // on airplane mode
                val alert = AlertDialog.Builder(this)
                alert.setTitle("Turn off airplane mode?")
                alert.setPositiveButton("Yes", DialogInterface.OnClickListener { _, _ -> startActivity(Intent(android.provider.Settings.ACTION_SETTINGS))})
                alert.setNegativeButton("No", DialogInterface.OnClickListener { _, _ -> })
                alert.create().show()
            } else {
                val alert = AlertDialog.Builder(this)
                alert.setTitle("You are not connected to any signal")
                alert.setNeutralButton("Continue", DialogInterface.OnClickListener { _, _ -> })
                alert.create().show()
            }
        } else {
            listView.setOnItemClickListener { _, _, position, _ ->
                // position = 0..2
                val intent = Intent(this, QuizActivity::class.java)
                intent.putExtra("topic", position)
                startActivity(intent)
            }
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

    fun connectedToInternet(): Boolean {
        val netInfo = (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }
}
