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
        val dataAdapter = ArrayAdapter(
            this
            , android.R.layout.simple_spinner_item
            , categories
        )
        /*
         * The code below works fine but you don't get the radio button
         * on the dropdown menu.  Changing from
         *  android.R.layout.simple_spinner_dropdown_item
         * to
         *  android.R.layout.select_dialog_singlechoice
         * gives the radio button.
        dataAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item);
            */
        // use android.R.layout.select_dialog_singlechoice for
        // radio button in dropdown.
        dataAdapter.setDropDownViewResource(
            android.R.layout.select_dialog_singlechoice
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
        val s = createSpinner(R.id.layout_spinner, "activity spinner %02d")
        // set initial selection to the fifth element (= 06)
        // set initial selection to the fifth element (= 06)
        s!!.setSelection(5)

    }
}
