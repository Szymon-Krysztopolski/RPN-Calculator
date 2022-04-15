package com.example.rpn_calculator

import android.widget.Toast
import android.widget.Button
import android.widget.TextView
import java.util.*
import kotlin.math.min

class Calculator(tmp: MainActivity) {
    private val ma = tmp
    private val main_text: TextView = ma.findViewById(R.id.main_num)
    private val alphabet = arrayOf("0","1","2","3","4","5","6","7","8","9",".")
    private val stack_SIZE = 4;
    val screen_stack = arrayOf<TextView>(
        ma.findViewById(R.id.stack1),
        ma.findViewById(R.id.stack2),
        ma.findViewById(R.id.stack3),
        ma.findViewById(R.id.stack4)
    )
    private var stack = Stack<Double>()

    fun press(button: Button) {
        when(button.text){
            in alphabet -> main_num_add(button.text as String)
            else -> when(button.id){
                R.id.settings -> Toast.makeText(ma.applicationContext, "here", Toast.LENGTH_SHORT).show()
                R.id.actChgSign -> chgSign()
                R.id.enter -> stack_add()
                else -> Toast.makeText(ma.applicationContext, button.text, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun main_num_add(s: String){
        main_text.text = main_text.text.toString().plus(s)
    }

    private fun chgSign(){
        if(main_text.text.isNotEmpty()){
            if(main_text.text[0]=='-'){
                main_text.text = main_text.text.drop(1)
            } else {
                main_text.text = "-".plus(main_text.text)
            }
        }
    }

    private fun stack_add(){
        stack.push(main_text.text.toString().toDouble())
        main_text.text=""
        stack_update()
    }

    private fun stack_update(){
        if(stack.isNotEmpty()){
            for(i in 0 until min(stack_SIZE,stack.size)){
                screen_stack[i].text = stack[stack.size-i-1].toString()
            }
        }
    }
}