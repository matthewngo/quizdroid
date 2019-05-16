package edu.washington.mvn3.quizdroid

import android.Manifest
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.DownloadManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.WindowManager
import android.widget.*


class PreferenceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preference)

        val spinner = findViewById<Spinner>(R.id.numSpinner)
        val items = arrayOf(1, 2, 5, 10, 30, 60, 120)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        spinner.setAdapter(adapter)

        val urlText = findViewById<TextView>(R.id.urlTextBox)
        val updateButton = findViewById<Button>(R.id.updateButton)
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val count = intent.getIntExtra("count", 0)
        val intent = Intent(this, AlarmReceiver::class.java)
        var pressed = false

        updateButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                System.out.println("GETTING PERMISSSION")
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
            } else {
                if (count == 1) {
                    val cancelIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
                    alarmManager.cancel(cancelIntent)
                    println("CANCELED!!")
                }
                intent.putExtra("url", urlText.text.toString())
                val alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
                val interval = spinner.selectedItem.toString().toLong() * 60000
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, alarmIntent)
                Toast.makeText(this, "Will Download From " + urlText.text.toString(), Toast.LENGTH_SHORT).show()
                val nextIntent = Intent(this, MainActivity::class.java)
                nextIntent.putExtra("count", 1)
                startActivity(nextIntent)
            }
        }
    }

    class AlarmReceiver: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            var uri: Uri? = null
            var request:DownloadManager.Request? = null
            try {
                uri = Uri.parse(intent!!.getStringExtra("url"))
                request = DownloadManager.Request(uri)
            } catch (e: Exception) {
                println("Failed " + intent!!.getStringExtra("url"))
                Toast.makeText(context, "Failed: Retry Download Settings", Toast.LENGTH_SHORT).show()
                return
            }
            val downloadManager = context!!.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            request.setTitle("Download")
            request.setDescription("Downloading Questions JSON")
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "questions.json")
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE)
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.allowScanningByMediaScanner()
            val downloadID = downloadManager.enqueue(request)
            Toast.makeText(context, "DOWNLOADING FROM URL...", Toast.LENGTH_SHORT).show()
            println("DOWNLOADING..." + uri)
        }
    }
}

