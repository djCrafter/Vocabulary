package com.example.dictionary

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private val wordToDefn = HashMap<String, String>()
    private val words = ArrayList<String>()
    private val defns = ArrayList<String>()
    private lateinit var myAdapter : ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        readDictionaryFile()
        setupList()

        definitions_list.setOnItemClickListener { _, _, index, _ ->  itemClickHandler(index)}
    }


    private fun itemClickHandler(index : Int){
        if(defns[index] == wordToDefn[the_word.text]){
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
            fillTheList()
        }
        else {
            Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show()
        }

        defns.removeAt(index)
        myAdapter.notifyDataSetChanged()


        if(defns.size == 0) {
            setupList()
            myAdapter.notifyDataSetChanged()
        }
    }
    

    private fun readDictionaryFile() {
       val reader =  Scanner(resources.openRawResource(R.raw .grewords))
        while(reader.hasNextLine()){
            val line = reader.nextLine()

            val notion = line.substringBefore("-")
            val definition = line.substringAfter("-")

            if(notion.isNotEmpty() &&  definition.isNotEmpty()) {
                words.add(notion)
                wordToDefn.put(notion, definition)
            }
        }
    }

    private fun setupList() {
        fillTheList()
        myAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, defns)
        definitions_list.adapter = myAdapter
    }

    private fun fillTheList(){
        //pick a random word
        val rand = Random()
        val index = rand.nextInt(words.size)
        val word = words[index]
        the_word.text = word

        defns.clear()
        defns.add(wordToDefn[word]!!)
        words.shuffle()
        for(otherWord in words.subList(0,9)){
            if(otherWord == word || defns.size == 9){
                continue
            }
            defns.add(wordToDefn[otherWord]!!)
        }
        defns.shuffle()
    }
}
