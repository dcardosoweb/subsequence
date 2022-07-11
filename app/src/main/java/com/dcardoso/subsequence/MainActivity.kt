package com.dcardoso.subsequence

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.appcompat.app.AppCompatActivity
import com.dcardoso.subsequence.databinding.ActivityMainBinding
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()

    }

    private fun setupView(){
        binding.textViewResult.movementMethod = ScrollingMovementMethod()
        binding.buttonSend.setOnClickListener {
            binding.textViewResult.text = ""
            val regex = Regex("[^,0-9]")
            var input: Array<Int> = emptyArray()
            if(!binding.editInputVal.text.isNullOrEmpty())
                input = regex.replace(binding.editInputVal.text,"").split(",").map { it.toInt() }.toTypedArray()

            findAllSequences(input)
        }
    }

    fun findAllSequences(sequence: Array<Int>) {
        Arrays.sort(sequence)
        val result: Deque<Int?> = LinkedList<Int?>()
        findSequenceBlock(sequence, result, sequence.size - 1)
    }

    fun findSequenceBlock(sequence: Array<Int>, sequenceList: Deque<Int?>, sizeBlock: Int) {
        var index = sizeBlock
        if (index < 0) {
            with(binding){
                textViewResult.append(sequenceList.toString()+"\n")
                return
            }
        }

        sequenceList.addLast(sequence[index])
        findSequenceBlock(sequence, sequenceList, index - 1)
        sequenceList.pollLast()

        while (index > 0 && sequence[index] == sequence[index - 1]) {
            index--
        }
        findSequenceBlock(sequence, sequenceList, index - 1)
    }
}