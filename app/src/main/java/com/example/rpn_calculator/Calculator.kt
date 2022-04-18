package com.example.rpn_calculator

import android.content.Intent
import android.graphics.Color
import android.widget.Toast
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import java.io.Serializable
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.sqrt

class Calculator(tmp: MainActivity) : Serializable {
    private val ma=tmp

    private val main_text: TextView = ma.findViewById(R.id.main_num)
    private val alphabet = arrayOf("0","1","2","3","4","5","6","7","8","9",".")
    private val stack_SIZE = 4
    private var stack_prec = 5
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
                R.id.settings -> openSettings()
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
            if(main_text.text[0]=='-'){
                main_text.text = main_text.text.drop(1)
            } else {
                main_text.text = "-".plus(main_text.text)
            }
        } else if(stack.isNotEmpty()) {
            MEGA_stuck_update()
            stack[stack.size-1]=-1*stack[stack.size-1]
            stack_update()
        } else {
            MYtoast_mess("There is no number to change")
        }
    }

    private fun stack_add(){
        MEGA_stuck_update()
        if(main_text.text.isNotEmpty()){
            try{
                stack.push(main_text.text.toString().toDouble())
            } catch (e: Exception){
                stack_undo()
                MYtoast_mess("error with conversion to double")
            } finally {
                main_text.text=""
            }
        } else if (stack.isNotEmpty()){
            stack.push(stack[stack.size-1])
        } else {
            stack_undo()
            MYtoast_mess("text is empty")
        }
        stack_update()
    }

    private fun stack_update(){
        for(i in 0 until stack_SIZE) screen_stack[i].text = "s".plus((i+1).toString().plus(": "))

        if(stack.isNotEmpty()){
            val tmp: Double = 10.0.pow(stack_prec)
            for(i in 0 until min(stack_SIZE,stack.size)){
                screen_stack[i].text = "s".plus((i+1).toString().plus(": ".plus((round(stack[stack.size-i-1]*tmp)/tmp).toString())))
            }
        }
    }

    private fun stack_clear(){
        if(stack.isNotEmpty()){
            MEGA_stuck_update()
            stack.clear()
            stack_update()
        } else {
            MYtoast_mess("stack is empty")
        }
    }

    fun stack_undo(){
        if(MEGA_stack.isNotEmpty()){
            stack=MEGA_stack.pop()
            stack_update()
        } else {
            MYtoast_mess("MEGAstack is empty")
        }
    }

    private fun MEGA_stuck_update(){
        MEGA_stack.push(stack.clone() as Stack<Double>)
    }

    private fun action1arg(act: Char){
        if(stack.isNotEmpty()){
            MEGA_stuck_update()
            val a: Double = stack.pop()
            when(act){
                'q' -> {
                    if(a>=0) stack.push(sqrt(a))
                    else {
                        stack_undo()
                        MYtoast_mess("you can't sqrt negative number")
                    }
                }
            }
            stack_update()
        } else {
            MYtoast_mess("stack is empty")
        }
    }

    private fun action2arg(act: Char){
        if(stack.size>=2){
            MEGA_stuck_update()
            val b: Double = stack.pop()
            val a: Double = stack.pop()
            when(act) {
                '+' -> stack.push(a + b)
                '-' -> stack.push(a - b)
                '*' -> stack.push(a * b)
                '/' -> {
                    if (b!=0.0) stack.push(a / b) else {
                        stack_undo()
                        MYtoast_mess("you can't div by zero")
                    }
                }
                'p' -> stack.push(a.pow(b))
                's' -> {
                    stack.push(b)
                    stack.push(a)
                }
            }
            stack_update()
        } else {
            MYtoast_mess("size of stack is below 2")
        }
    }

    fun MYtoast_mess(mess: String){
        Toast.makeText(ma.applicationContext, mess, Toast.LENGTH_SHORT).show()
    }

    private fun chgStackPrec(prec: Int){
        stack_prec=prec
    }

    private fun openSettings(){
        val intent = Intent(ma, MySettings::class.java)
        ma.startActivityForResult(intent,1)
    }

    fun updateSettings(list: ArrayList<String>){
        val settButts_array = arrayOf<Button>(
            ma.findViewById(R.id.enter),
            ma.findViewById(R.id.undo),
            ma.findViewById(R.id.drop),
            ma.findViewById(R.id.swap),
            ma.findViewById(R.id.ac),
            ma.findViewById(R.id.settings),
            ma.findViewById(R.id.back)
        )
        val textViewScreen_array = arrayOf<TextView>(
            ma.findViewById(R.id.stack1),
            ma.findViewById(R.id.stack2),
            ma.findViewById(R.id.stack3),
            ma.findViewById(R.id.stack4),
            ma.findViewById(R.id.main_num)
        )

        ma.findViewById<ConstraintLayout>(R.id.screen).setBackgroundColor(translate_color(list[0]))
        for(i in textViewScreen_array){
            i.setTextColor(translate_color(list[1]))
        }
        for(i in settButts_array){
            i.setBackgroundColor(translate_color(list[2]))
            i.setTextColor(translate_color(list[3]))
        }
        chgStackPrec(list[4].toInt())

        stack_update()
    }

    private fun translate_color(color: String): Int{
        when(color){
            "Red" -> return Color.RED
            "Green" -> return Color.GREEN
            "Blue" -> return Color.BLUE
            "Black" -> return Color.BLACK
            "White" -> return Color.WHITE
            "Gray" -> return Color.GRAY
            else -> return Color.CYAN
        }
    }
}