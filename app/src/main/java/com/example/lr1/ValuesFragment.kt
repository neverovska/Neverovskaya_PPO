package com.example.lr1

import android.R.attr.label
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.getSystemService

import androidx.fragment.app.Fragment
import com.example.lr1.FactoryMethod.Companion.unit
import java.math.RoundingMode


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

    private lateinit var textFrom: EditText
    private lateinit var textTo: EditText

    private lateinit var spinArray: Array<String>

    private lateinit var converter: Converter

    private lateinit var adapter: ArrayAdapter<String>

    private var toPos = 0
    private var fromPos = 0

    private var textF = StringBuilder()
    private var textT = StringBuilder()

    private var position = 0

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

        textFrom .showSoftInputOnFocus = false

        currencyButton.setOnClickListener(this)
        weightButton.setOnClickListener(this)
        distanceButton.setOnClickListener(this)
        volumeButton.setOnClickListener(this)

        copyButton1.setOnClickListener(this)
        copyButton2.setOnClickListener(this)

        revButton.setOnClickListener(this)

        fromSpinner.onItemSelectedListener = this
        toSpinner.onItemSelectedListener = this

//        fromSpinner.setSelection(fromPos)
//        toSpinner.setSelection(toPos)
        if (savedInstanceState != null) {
            selItem(view)
        }
    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (textF.isNotEmpty()) {
            textT = StringBuilder(
                converter.convertFromTo(
                    fromSpinner.selectedItem.toString(),
                    toSpinner.selectedItem.toString(), textF.toString()
                )
            )
            var buf = textT.toString()
            buf = zeroDel(buf)

            textT = StringBuilder("$buf")
            textTo.setText(textT.toString())
        }
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
                        val clipboard =
                            activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
                        val clip = ClipData.newPlainText("text", textF.toString())
                        clipboard?.setPrimaryClip(clip)
                        fromPos = fromSpinner.selectedItemPosition
                        toPos = toSpinner.selectedItemPosition
                        Toast.makeText(context, "Value is copied", Toast.LENGTH_SHORT).show()
                    } else Toast.makeText(context, "Enter value to copy", Toast.LENGTH_SHORT).show()
                }
                copyButton2.id -> {
                    if (!textT.equals("")) {
                        val clipboard =
                            activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
                        val clip = ClipData.newPlainText("text", textT.toString())
                        clipboard?.setPrimaryClip(clip)
                        fromPos = fromSpinner.selectedItemPosition
                        toPos = toSpinner.selectedItemPosition
                        Toast.makeText(context, "Value is copied", Toast.LENGTH_SHORT).show()
                    } else Toast.makeText(context, "Enter value to copy", Toast.LENGTH_SHORT).show()
                }
                revButton.id -> {
                    var buf = textT.toString()
                    buf = zeroDel(buf)

                    textF = StringBuilder("$buf")
                    textFrom.setText(textF.toString())

                    fromPos = toSpinner.selectedItemPosition
                    toPos = fromSpinner.selectedItemPosition

                }
            }
            selItem(v)
        }
    }


    fun selItem(view: View) {
        adapter = ArrayAdapter(view.context, android.R.layout.simple_spinner_item, spinArray)
        fromSpinner.adapter = adapter
        toSpinner.adapter = adapter
        fromSpinner.setSelection(fromPos)
        toSpinner.setSelection(toPos)
    }


    fun setFromText(buttonIndex: Int) {
        position = textFrom.selectionStart
        if (!(spinArray.contentEquals(resources.getStringArray(R.array.currencies)) && textF.length -
                    textF.indexOf(".") > 2 && textF.contains(".") && buttonIndex !=11
                    && buttonIndex != 12) && !(textF.contains(".") && textF.length -
            textF.indexOf(".") > 15 && !(spinArray.contentEquals(resources.getStringArray(R.array.currencies))))
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
                        if (textF.isEmpty()){
                            textF.append("0.")
                            position += 1
                        }
                        else
                            textF.insert(position, ".")
                    }
                    else {
                        Toast.makeText(context, "Double dot isn't allowed", Toast.LENGTH_SHORT)
                            .show()
                        return
                    }
                }
                0 -> {
                    if (!(textF.isNotEmpty() && textF[0] == '0' && !textF.contains("."))) {
                        textF.insert(position, "0")
                    } else Toast.makeText(context, "Double zero isn't allowed", Toast.LENGTH_SHORT)
                        .show()
                }
                11 -> {
                    if (position >= 1) {
                        textF = textF.deleteCharAt(position-1)
                        position -= 2
                    }
                    else return
                }
                12 -> {
                    textF.clear()
                    position = 0
                    textFrom.text.clear()
                    textTo.text.clear()
                    return
                }
            }

            textFrom.setText(textF.toString())
            textT = if (textF.isNotEmpty()) {
                StringBuilder(
                    converter.convertFromTo(
                        fromSpinner.selectedItem.toString(),
                        toSpinner.selectedItem.toString(), textF.toString()
                    )
                )

            } else StringBuilder("")
            var buf = textT.toString()
            buf = zeroDel(buf)

            textT = StringBuilder("$buf")
            textTo.setText(textT.toString())
            textTo.setText(textT.toString())
            position += 1
            textFrom.setSelection(position)
        }
        else Toast.makeText(context, "fuck you", Toast.LENGTH_SHORT).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putSerializable("converter", converter)
        outState.putInt("fromSpin", fromSpinner.selectedItemPosition)
        outState.putInt("toSpin", toSpinner.selectedItemPosition)
        outState.putString("from", textF.toString())
        outState.putStringArray("array", spinArray)
    }

    private fun zeroDel(str: String ) : String{
        var buf = str
        while (true)
            if ("." in buf && buf.last() == '0' && buf[buf.length - 2] != '.') {
                buf = buf.dropLast(1)
            } else {
                break
            }
        return buf
    }
}