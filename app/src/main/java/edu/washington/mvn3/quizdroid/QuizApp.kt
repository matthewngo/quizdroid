package edu.washington.mvn3.quizdroid

import android.app.Application
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Log
import org.json.JSONArray
import java.io.File

interface TopicRepositoryInterface {
    fun getRepository(): ArrayList<TopicObject>
    fun getTopics(): Array<String>
    fun getTopic(index: Int): TopicObject
    fun initRepository()
}

class StateRepository : TopicRepositoryInterface {
    private var repo = arrayListOf<TopicObject>()

    override fun getTopic(index: Int): TopicObject {
        return repo[index]
    }

    override fun getTopics(): Array<String> {
        var result = ArrayList<String>()
        for (obj in repo) {
            result.add(obj.title)
        }
        return result.toTypedArray()
    }

    override fun getRepository(): ArrayList<TopicObject> {
        return repo
    }

    // Offline repository
    override fun initRepository() {
        val m1 = QuizObject("1+1", arrayOf("0","1","2","3"), 2)
        val m2 = QuizObject("1*1", arrayOf("99","1","98","97"), 1)
        val p1 = QuizObject("What is one of Newton's Laws", arrayOf("f = ma","1+1=2","1+2=5","science"), 0)
        val sh1 = QuizObject("Which is a Marvel Super Hero", arrayOf("!","heh","nope","Iron Man"), 3)

        repo.add(TopicObject("Math", "Simple math", "Testing your math skills", arrayOf(m1,m2)))
        repo.add(TopicObject("Physics", "Simple physics", "Testing your physics skills", arrayOf(p1)))
        repo.add(TopicObject("Marvel Super Heroes", "Simple heroes", "Testing your Marvel Super Hero skills", arrayOf(sh1)))
    }

    // Online repository
    fun initRepositoryJSON() {
        val temp = arrayListOf<TopicObject>()
        var jsonFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/questions.json")
        for (i in Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).listFiles()) {
            System.out.println("ITEM: " + i.name)
        }
        if (!jsonFile.exists()) {
            jsonFile = File(Environment.getExternalStorageDirectory(), "custom.json")
        }
        val json = JSONArray(jsonFile.readText())
        for (i in 0..json.length()-1) {
            val topic = json.getJSONObject(i)
            val title = topic.getString("title")
            val desc = topic.getString("desc")
            val questions = topic.getJSONArray("questions")
            val repoQuestions = ArrayList<QuizObject>()
            for (qIndex in 0..questions.length()-1) {
                val qObject = questions.getJSONObject(qIndex)
                val text = qObject.getString("text")
                val correct = qObject.getInt("answer") - 1
                val answers = qObject.getJSONArray("answers")
                val repoAnswers = ArrayList<String>()
                for (aIndex in 0..answers.length()-1) {
                    repoAnswers.add(answers[aIndex].toString())
                }
                repoQuestions.add(QuizObject(text, repoAnswers.toTypedArray(), correct))
            }
            temp.add(TopicObject(title, desc, desc, repoQuestions.toTypedArray()))
        }
        repo = temp
    }
}

class QuizApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.i("QuizApp", "Starting Up QuizApp")
    }

    // singleton object
    companion object {
        val stateRepository = StateRepository()

        fun resetRepo() {
            stateRepository.initRepositoryJSON()
        }
        init {
            //stateRepository.initRepository()
            stateRepository.initRepositoryJSON()
        }
    }
}

data class QuizObject(val question: String, val answers: Array<String>, val correct: Int)
data class TopicObject(val title: String, val shortDesc: String, val longDesc: String, val qObjects: Array<QuizObject>)