package com.kana_tutor.spinnerdemo

import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.AppCompatSpinner
import kotlin.random.Random


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    companion object {
        private var isDarkTheme = false
        private var currentDropdownSelection = -1
    }

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
            R.layout.spinner_dropdown_item
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
    private fun selectDarkDisplayTheme (selectDarkTheme:Boolean) {
        val curThemeIsDark = (resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK
                ) == Configuration.UI_MODE_NIGHT_YES
        if (curThemeIsDark != selectDarkTheme) {
            AppCompatDelegate.setDefaultNightMode(
                if (selectDarkTheme) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
        }
        isDarkTheme = selectDarkTheme
    }
    val categoryTitles = listOf(
        "apple", "strawberry", "blackberry", "lemon meringue",
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val s = createSpinner(R.id.layout_spinner, "activity spinner %02d")!!
        // set initial selection to the fifth element (= 06)
        // set initial selection to the fifth element (= 06)
        selectDarkDisplayTheme(isDarkTheme)
        if (currentDropdownSelection < 0) {
            currentDropdownSelection = Random.nextInt(categoryTitles.lastIndex)
        }
        s.setSelection(5)
    }

    var setInitial = true
    private lateinit var optionMenu : Menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        optionMenu = menu
        menu.findItem(R.id.dark_theme_state).title =
            if (isDarkTheme) getString(R.string.select_light_theme)
            else getString(R.string.select_dark_theme)
        val menuItem = menu.findItem(R.id.kanji_type_select)
        val spinner = menuItem.actionView as AppCompatSpinner
        val adapter = ArrayAdapter(this, R.layout.menu_spinner_textview, categoryTitles)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        spinner.adapter = adapter
        // first run after install, init to a random value.
        if (setInitial) {
            spinner.setSelection(currentDropdownSelection)
        }
        spinner.onItemSelectedListener = object:
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?,
                position: Int, id: Long
            ) {
                Toast.makeText(
                    applicationContext,
                    "onItemSelected:${categoryTitles[position]}",
                    Toast.LENGTH_LONG
                ).show()
                currentDropdownSelection = position // static to save over day/night change.
            }
            override fun onNothingSelected(parent: AdapterView<*>?) { /* empty */ }

        }
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        val selectedItem = optionMenu.findItem(id)
        Toast.makeText(applicationContext,
            "menu evemt: id = 0x%08x, title = %s".format(id, selectedItem.title),
            Toast.LENGTH_LONG).show()
        if (id == R.id.dark_theme_state) {
            val curThemeDark = selectedItem.title == getString(R.string.select_light_theme)
            selectedItem.title =
                if (curThemeDark) getString(R.string.select_light_theme)
                else getString(R.string.select_dark_theme)
            selectDarkDisplayTheme(!curThemeDark)
        }
        return super.onOptionsItemSelected(item)
    }
}
