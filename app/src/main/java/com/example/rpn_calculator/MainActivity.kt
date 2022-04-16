package com.example.rpn_calculator

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var calc: Calculator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        calc = Calculator(this)
        val butt_names = arrayOf<String>(
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
}