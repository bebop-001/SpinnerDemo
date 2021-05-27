package com.kana_tutor.spinnerdemo

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private fun createSpinner(id: Int, fmtString: String): Spinner? {
        val spinner = findViewById<View>(id) as Spinner
        spinner.setOnItemSelectedListener(this)
        val categories: MutableList<String> = mutableListOf()
        for (i in 0..20) categories.add(String.format(fmtString, i))
        // Supply your own text view to have control over text size and
        // color.  I couldn't get Spinner->android:dropDownWidth wrap content
        // to work but hard-code width seems to work ok.  You can use same text
        // view for drop-down with 0 padding and for Spinner and set spinner
        // padding.  Background image can be changed with android:background
        val dataAdapter = ArrayAdapter(
            this
            , R.layout.spinner_textview
            // , android.R.layout.simple_spinner_item
            , categories
        )
        dataAdapter.setDropDownViewResource(
            R.layout.spinner_textview
        )
        spinner.adapter = dataAdapter
        return spinner
    }
    override fun onItemSelected(
        parent: AdapterView<*>, view: View?, itemIndex: Int,
        itemId: Long
    ) {
        val selectedItem = parent.getItemAtPosition(itemIndex).toString()
        Toast.makeText(
            parent.context
            , "Selected item: \"$selectedItem\""
            , Toast.LENGTH_LONG
        )
            .show()
    }

    override fun onNothingSelected(arg0: AdapterView<*>?) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val s = createSpinner(R.id.layout_spinner, "activity spinner %02d")!!
        // set initial selection to the fifth element (= 06)
        // set initial selection to the fifth element (= 06)
        s.setSelection(5)

    }
}
