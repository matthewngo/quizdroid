package edu.washington.mvn3.quizdroid

import android.app.Application
import android.util.Log

interface TopicRepositoryInterface {
    fun getRepository(): ArrayList<TopicObject>
    fun getTopics(): Array<String>
    fun getTopic(index: Int): TopicObject
    fun initRepository()
}

class StateRepository : TopicRepositoryInterface {
    private val repo = arrayListOf<TopicObject>()

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

    override fun initRepository() {
        val m1 = QuizObject("1+1", arrayOf("0","1","2","3"), 2)
        val m2 = QuizObject("1*1", arrayOf("99","1","98","97"), 1)
        val p1 = QuizObject("What is one of Newton's Laws", arrayOf("f = ma","1+1=2","1+2=5","science"), 0)
        val sh1 = QuizObject("Which is a Marvel Super Hero", arrayOf("!","heh","nope","Iron Man"), 3)

        repo.add(TopicObject("Math", "Simple math", "Testing your math skills", arrayOf(m1,m2)))
        repo.add(TopicObject("Physics", "Simple physics", "Testing your physics skills", arrayOf(p1)))
        repo.add(TopicObject("Marvel Super Heroes", "Simple heroes", "Testing your Marvel Super Hero skills", arrayOf(sh1)))
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
        init {
            stateRepository.initRepository()
        }
    }

}

data class QuizObject(val question: String, val answers: Array<String>, val correct: Int)
data class TopicObject(val title: String, val shortDesc: String, val longDesc: String, val qObjects: Array<QuizObject>)