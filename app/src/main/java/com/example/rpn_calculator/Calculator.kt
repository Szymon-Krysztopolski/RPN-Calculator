package com.example.rpn_calculator

import android.widget.Toast
import android.widget.Button
import android.widget.TextView
import java.lang.Exception
import java.util.*
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

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
    private var MEGA_stack = Stack<Stack<Double>>()

    fun press(button: Button) {
        when(button.text){
            in alphabet -> main_num_add(button.text as String)
            else -> when(button.id){
                R.id.settings -> Toast.makeText(ma.applicationContext, "here", Toast.LENGTH_SHORT).show()
                R.id.actChgSign -> chgSign()
                R.id.enter -> stack_add()
                R.id.back -> main_num_del()
                R.id.actAdd -> action2arg('+')
                R.id.actSub -> action2arg('-')
                R.id.actMulti -> action2arg('*')
                R.id.actDiv -> action2arg('/')
                R.id.pow -> action2arg('p')
                R.id.swap -> action2arg('s')
                R.id.sqrt -> action1arg('q')
                R.id.drop -> action1arg(' ')
                R.id.ac -> stack_clear()
                R.id.undo -> stack_undo()
                else -> MYtoast_mess(button.text.toString())
            }
        }
    }
    private fun main_num_add(s: String){
        main_text.text = main_text.text.toString().plus(s)
    }
    private fun main_num_del(){
        main_text.text = main_text.text.dropLast(1);
    }
    private fun chgSign(){
        if(main_text.text.isNotEmpty()){
            MEGA_stuck_update()
            if(main_text.text[0]=='-'){
                main_text.text = main_text.text.drop(1)
            } else {
                main_text.text = "-".plus(main_text.text)
            }
        }
    }
    private fun stack_add(){
        try{
            MEGA_stuck_update()
            stack.push(main_text.text.toString().toDouble())
            stack_update()
        } catch (e: Exception){
            MYtoast_mess("Error with conversion to double")
        } finally {
            main_text.text=""
        }
    }
    private fun stack_update(){
        for(i in 0 until stack_SIZE) screen_stack[i].text = "s".plus((i+1).toString().plus(": "))

        if(stack.isNotEmpty()){
            for(i in 0 until min(stack_SIZE,stack.size)){
                screen_stack[i].text = "s".plus((i+1).toString().plus(": ".plus(stack[stack.size-i-1].toString())))
            }
        }
    }
    private fun stack_clear(){
        MEGA_stuck_update()
        stack.clear()
        stack_update()
    }
    private fun stack_undo(){
        if(MEGA_stack.isNotEmpty()){
            stack=MEGA_stack.pop()
            stack_update()
        } else {
            MYtoast_mess("MEGAstack is empty")
        }
    }
    private  fun MEGA_stuck_update(){
        MEGA_stack.push(stack.clone() as Stack<Double>)
    }
    private fun action1arg(act: Char){
        if(stack.isNotEmpty()){
            MEGA_stuck_update()
            val a: Double = stack.pop()
            when(act){
                'q' -> stack.push(sqrt(a))
            }
            stack_update()
        } else {
            MYtoast_mess("stack is empty")
        }
    }
    private fun action2arg(act: Char){
        if(stack.size>=2){
            MEGA_stuck_update()
            val a: Double = stack.pop()
            val b: Double = stack.pop()
            when(act) {
                '+' -> stack.push(a + b)
                '-' -> stack.push(a - b)
                '*' -> stack.push(a * b)
                '/' -> {
                    if (b!=0.0) stack.push(a / b) else {
                        stack.push(b)
                        stack.push(a)
                        MYtoast_mess("you can't div by zero")
                    }
                }
                'p' -> stack.push(a.pow(b))
                's' -> {
                    stack.push(a)
                    stack.push(b)
                }
            }
            stack_update()
        } else {
            MYtoast_mess("size of stack is below 2")
        }
    }
    private fun MYtoast_mess(mess: String){
        Toast.makeText(ma.applicationContext, mess, Toast.LENGTH_SHORT).show()
    }
}