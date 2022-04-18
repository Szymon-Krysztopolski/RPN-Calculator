package com.example.rpn_calculator

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener {
    private lateinit var calc: Calculator
    lateinit var gestureDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        gestureDetector = GestureDetector(this)

        calc = Calculator(this)
        val butt_names = arrayOf(
            "enter","back","undo","drop","swap","ac","settings",
            "pow","sqrt","actMulti","actDiv","actAdd","actSub",
            "actChgSign","dot",
            "num0","num1","num2","num3","num4","num5","num6","num7","num8","num9"
        )
        val buttons = ArrayList<Button>()

        for(i in butt_names){
            val resID = resources.getIdentifier(i, "id", packageName)
            buttons.add(findViewById(resID))
        }

        for(i in buttons.indices) {
            buttons[i].setOnClickListener { calc.press(buttons[i]) }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==1 && resultCode==Activity.RESULT_OK){
            calc.updateSettings(data?.getStringArrayListExtra("options")!!)
        }
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (gestureDetector.onTouchEvent(event)) {
            true
        }
        else {
            super.onTouchEvent(event)
        }
    }

    override fun onDown(p0: MotionEvent?): Boolean {
        return false
    }

    override fun onShowPress(p0: MotionEvent?) {
        return
    }

    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
        return false
    }

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        return false
    }

    override fun onLongPress(p0: MotionEvent?) {
        return
    }

    override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        calc.stack_undo()
        return true
    }
}