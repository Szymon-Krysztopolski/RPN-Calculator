package com.example.rpn_calculator

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calc = Calculator(this)

        val butt_names = arrayOf<String>(
            "enter","back","undo","drop","swap","ac","settings",
            "pow","sqrt","actMulti","actDiv","actAdd","actSub",
            "actChgSign","dot",
            "num0","num1","num2","num3","num4","num5","num6","num7","num8","num9"
        )

        val buttons = ArrayList<Button>()

        for(i in butt_names){
            val resID = resources.getIdentifier(i, "id", packageName)
            buttons.add(findViewById(resID));
        }

        /*
        var buttons = arrayOf<Button>(
            findViewById(R.id.enter),
            findViewById(R.id.back),
            findViewById(R.id.undo),
            findViewById(R.id.drop),
            findViewById(R.id.swap),
            findViewById(R.id.ac),
            findViewById(R.id.settings),

            findViewById(R.id.pow),
            findViewById(R.id.sqrt),
            findViewById(R.id.actMulti),
            findViewById(R.id.actDiv),
            findViewById(R.id.actAdd),
            findViewById(R.id.actSub),

            findViewById(R.id.actChgSign),
            findViewById(R.id.dot),

            findViewById(R.id.num0),
            findViewById(R.id.num1),
            findViewById(R.id.num2),
            findViewById(R.id.num3),
            findViewById(R.id.num4),
            findViewById(R.id.num5),
            findViewById(R.id.num6),
            findViewById(R.id.num7),
            findViewById(R.id.num8),
            findViewById(R.id.num9)
        )
         */

        for(i in buttons.indices) {
            buttons[i].setOnClickListener { calc.press(buttons[i]) }
        }
    }
}