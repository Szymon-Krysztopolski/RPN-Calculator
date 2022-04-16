package com.example.rpn_calculator

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class MySettings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val val_def = arrayOf(5,3,0,3,4)
        val spinners_array = arrayOf<Spinner>(
            this.findViewById(R.id.spinColorScreen), //gray
            this.findViewById(R.id.spinColorScreenText), //black
            this.findViewById(R.id.spinColorButtSett), //red
            this.findViewById(R.id.spinColorButtSettText), //black
            this.findViewById(R.id.spinPrecision) //5
        )
        for(i in spinners_array.indices){
            spinners_array[i].setSelection(val_def[i])
        }
        //findViewById<Spinner>(R.id.spinColorScreen).setSelection(2)

        val settButt: Button = findViewById(R.id.ButtSettings)
        settButt.setOnClickListener {
            val options = ArrayList<String>()

            for(i in spinners_array){
                options += i.selectedItem.toString()
            }

            val intent = Intent(this, MainActivity::class.java)
            intent.putStringArrayListExtra("options",options)
            setResult(Activity.RESULT_OK,intent)
            finish()
        }
    }
}