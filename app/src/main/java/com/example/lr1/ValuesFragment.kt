package com.example.lr1

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.GestureDetector.SimpleOnGestureListener
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.lr1.FactoryMethod.Companion.unit
import java.time.LocalTime
import java.time.format.DateTimeFormatter


class ValuesFragment : Fragment(), View.OnClickListener, AdapterView.OnItemSelectedListener {
    private lateinit var currencyButton: Button
    private lateinit var distanceButton: Button
    private lateinit var weightButton: Button
    private lateinit var volumeButton: Button

    private lateinit var copyButton1: Button
    private lateinit var copyButton2: Button

    private lateinit var revButton: Button

    private lateinit var fromSpinner: Spinner
    private lateinit var toSpinner: Spinner

    //   companion object{

    lateinit var textFrom: EditText
    lateinit var textTo: EditText

    lateinit var spinArray: Array<String>

    lateinit var converter: Converter

    lateinit var adapter: ArrayAdapter<String>

    var toPos = 0
    var fromPos = 0


    var textT = StringBuilder()


    private lateinit var time: LocalTime

    var clipboard: ClipboardManager? = null
    lateinit var clipData: ClipData
    var textF = StringBuilder()
    var position = 0
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            converter = savedInstanceState.getSerializable("converter") as Converter
            textF = java.lang.StringBuilder(savedInstanceState.getString("from").toString())
            spinArray = savedInstanceState.getStringArray("array") as Array<String>
            //fromPos = savedInstanceState.getInt("fromSpin")
            //toPos = savedInstanceState.getInt("toSpin")


        } else {
            converter = unit(Unit.Currency)
            spinArray = resources.getStringArray(R.array.currencies)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_values, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        time = LocalTime.now()
        clipboard =
            activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        currencyButton = view.findViewById(R.id.button_c)
        weightButton = view.findViewById(R.id.button_w)
        distanceButton = view.findViewById(R.id.button_d)
        volumeButton = view.findViewById(R.id.button_v)

        copyButton1 = view.findViewById(R.id.button_copy1)
        copyButton2 = view.findViewById(R.id.button_copy2)

        revButton = view.findViewById(R.id.button_rev)

        fromSpinner = view.findViewById(R.id.spinner1)
        toSpinner = view.findViewById(R.id.spinner2)

        textFrom = view.findViewById(R.id.editText1)
        textTo = view.findViewById(R.id.editText2)

        textFrom.showSoftInputOnFocus = false
        registerForContextMenu(textFrom)

        currencyButton.setOnClickListener(this)
        weightButton.setOnClickListener(this)
        distanceButton.setOnClickListener(this)
        volumeButton.setOnClickListener(this)


        copyButton1.setOnClickListener(this)
        copyButton2.setOnClickListener(this)

        revButton.setOnClickListener(this)

        fromSpinner.onItemSelectedListener = this
        toSpinner.onItemSelectedListener = this

        textFrom.customSelectionActionModeCallback = object : ActionMode.Callback {
            override fun onCreateActionMode(p0: ActionMode?, p1: Menu?): Boolean {
                return false
            }

            override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?): Boolean {
                return false
            }

            override fun onActionItemClicked(p0: ActionMode?, p1: MenuItem?): Boolean {
                return false
            }

            override fun onDestroyActionMode(p0: ActionMode?) {

            }
        }
//        fromSpinner.setSelection(fromPos)
//        toSpinner.setSelection(toPos)
        if (savedInstanceState != null) {
            selItem(view)
        }
    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        conversion()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }


    override fun onClick(v: View?) {

        if (v != null) {

            when (v.id) {
                currencyButton.id -> {
                    spinArray = resources.getStringArray(R.array.currencies)
                    converter = unit(Unit.Currency)
                    fromPos = 0
                    toPos = 0
                }
                weightButton.id -> {
                    spinArray = resources.getStringArray(R.array.weights)
                    converter = unit(Unit.Weight)
                    fromPos = 0
                    toPos = 0
                }
                distanceButton.id -> {
                    spinArray = resources.getStringArray(R.array.distances)
                    converter = unit(Unit.Distance)
                    fromPos = 0
                    toPos = 0
                }
                volumeButton.id -> {
                    spinArray = resources.getStringArray(R.array.volumes)
                    converter = unit(Unit.Volume)
                    fromPos = 0
                    toPos = 0
                }
                copyButton1.id -> {
                    if (!textF.equals("")) {

                        clipData = ClipData.newPlainText("text", textF.toString())
                        clipboard!!.setPrimaryClip(clipData)
                        fromPos = fromSpinner.selectedItemPosition
                        toPos = toSpinner.selectedItemPosition
                        if (toastTime(time))
                            Toast.makeText(
                                context,
                                "Value is copied",
                                Toast.LENGTH_SHORT
                            ).show()
                        time = LocalTime.now()
                    } else {
                        if (toastTime(time))
                            Toast.makeText(context, "Enter value to copy", Toast.LENGTH_SHORT)
                                .show()
                        time = LocalTime.now()
                    }
                }
                copyButton2.id -> {
                    if (!textT.equals("")) {


                        clipData = ClipData.newPlainText("text", textT.toString())
                        clipboard!!.setPrimaryClip(clipData)
                        fromPos = fromSpinner.selectedItemPosition
                        toPos = toSpinner.selectedItemPosition

                        if (toastTime(time))
                            Toast.makeText(
                                context,
                                "Value is copied",
                                Toast.LENGTH_SHORT
                            ).show()
                        time = LocalTime.now()
                    } else {
                        if (toastTime(time))
                            Toast.makeText(context, "Enter value to copy", Toast.LENGTH_SHORT)
                                .show()
                        time = LocalTime.now()
                    }
                }
                revButton.id -> {

                    var buf = textT.toString()
                    buf = zeroDel(buf)

                    textF = StringBuilder(buf)
                    textFrom.setText(textF.toString())

                    fromPos = toSpinner.selectedItemPosition
                    toPos = fromSpinner.selectedItemPosition


                }
            }
            selItem(v)
        }
    }


    private fun selItem(view: View) {
        adapter = ArrayAdapter(view.context, android.R.layout.simple_spinner_item, spinArray)
        fromSpinner.adapter = adapter
        toSpinner.adapter = adapter
        fromSpinner.setSelection(fromPos)
        toSpinner.setSelection(toPos)
    }


    fun setFromText(buttonIndex: Int) {
        position = textFrom.selectionStart
        textF = StringBuilder(textFrom.text.toString())

        if (!(spinArray.contentEquals(resources.getStringArray(R.array.currencies)) && textF.length -
                    textF.indexOf(".") > 2 && textF.contains(".") && buttonIndex != 11
                    && buttonIndex != 12) && !(textF.contains(".") && textF.length -
                    textF.indexOf(".") > 15 && buttonIndex != 11
                    && buttonIndex != 12 && !(spinArray.contentEquals(resources.getStringArray(R.array.currencies))))
        ) {

            when (buttonIndex) {
                1 -> textF.insert(position, "1")
                2 -> textF.insert(position, "2")
                3 -> textF.insert(position, "3")
                4 -> textF.insert(position, "4")
                5 -> textF.insert(position, "5")
                6 -> textF.insert(position, "6")
                7 -> textF.insert(position, "7")
                8 -> textF.insert(position, "8")
                9 -> textF.insert(position, "9")
                10 -> {
                    if (!textF.contains(".")) {
                        if (textF.isEmpty()) {
                            textF.append("0.")
                            position += 1
                        } else
                            textF.insert(position, ".")
                    } else {
                        if (toastTime(time))
                            Toast.makeText(context, "Double dot isn't allowed", Toast.LENGTH_SHORT)
                                .show()
                        time = LocalTime.now()
                        return

                    }
                }
                0 -> {
                    if (!(textF.isNotEmpty() && textF[0] == '0' && !textF.contains("."))) {
                        textF.insert(position, "0")
                    } else {
                        if (toastTime(time))
                            Toast.makeText(context, "Double zero isn't allowed", Toast.LENGTH_SHORT)
                                .show()
                        time = LocalTime.now()
                        return

                    }
                }
                11 -> {
                    if (position >= 1) {
                        if (!(position == 1 && textF.indexOf(".") == 1)) {
                            textF = textF.deleteCharAt(position - 1)
                            position -= 2
                        } else {
                            textF.clear()
                            position = 0
                            textFrom.text!!.clear()
                            textTo.text!!.clear()
                            return
                        }
                    } else return
                }
                12 -> {
                    textF.clear()
                    textT.clear()
                    position = 0
                    textFrom.text!!.clear()
                    textTo.text!!.clear()
                    return
                }
            }

            textFrom.setText(textF.toString())
            conversion()
            position += 1

        } else {
            if (toastTime(time))
                Toast.makeText(context, "Too many numbers after dot", Toast.LENGTH_SHORT).show()
            time = LocalTime.now()
        }
        textFrom.setSelection(position)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putSerializable("converter", converter)
        outState.putInt("fromSpin", fromSpinner.selectedItemPosition)
        outState.putInt("toSpin", toSpinner.selectedItemPosition)
        outState.putString("from", textF.toString())
        outState.putStringArray("array", spinArray)


    }

    private fun zeroDel(str: String): String {
        var buf = str
        while (true)
            if ("." in buf && buf.last() == '0' && buf[buf.length - 2] != '.') {
                buf = buf.dropLast(1)
            } else {
                break
            }
        return buf
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        val menuInflater = MenuInflater(context)
        menuInflater.inflate(R.menu.c_menu, menu)
    }


    private fun paste() {
        position = textFrom.selectionStart
        val bufTxt: String

        try {
            bufTxt = clipboard!!.primaryClip?.getItemAt(0)?.text.toString()
            val err = bufTxt.toBigDecimal()
        } catch (e: Exception) {
            Toast.makeText(context, "Copying is not successful", Toast.LENGTH_SHORT).show()
            return
        }

        if (!(textF.contains(".") && bufTxt.contains("."))
        ) {
            if (!(spinArray.contentEquals(resources.getStringArray(R.array.currencies)) && textF.length -
                        textF.indexOf(".") > 2 && textF.contains(".") && textF.indexOf(".") < position) && !(textF.contains(
                    "."
                ) && textF.length -
                        textF.indexOf(".") > 15 && textF.indexOf(".") < position && !(spinArray.contentEquals(
                    resources.getStringArray(R.array.currencies)
                )))
            ) {
                textF.insert(position, bufTxt)
                textFrom.setText(textF.toString())
                position += bufTxt.length
                textFrom.setSelection(position)
            } else {
                if (toastTime(time))
                    Toast.makeText(context, "Too many numbers after dot", Toast.LENGTH_SHORT)
                        .show()
                time = LocalTime.now()
            }
        } else {
            if (toastTime(time))
                Toast.makeText(context, "Double dot isn't allowed", Toast.LENGTH_SHORT).show()
            time = LocalTime.now()
        }
        conversion()

    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.paste) {
            paste()
            return true
        }
        return false
    }

    private fun conversion() {
        if (textF.isNotEmpty()) {
            textT = StringBuilder(
                converter.convertFromTo(
                    fromSpinner.selectedItem.toString(),
                    toSpinner.selectedItem.toString(), textF.toString()
                )
            )
            var buf = textT.toString()
            buf = zeroDel(buf)

            textT = StringBuilder(buf)
            textTo.setText(textT.toString())
        }
    }

    private fun toastTime(time: LocalTime): Boolean {
        return (LocalTime.now().second -
                time.second >= 2 && LocalTime.now().minute - time.minute == 0)
                || LocalTime.now().minute - time.minute != 0
    }
}