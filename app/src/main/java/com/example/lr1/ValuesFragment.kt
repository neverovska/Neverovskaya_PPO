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

    var textF: String = ""
    var textT: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            converter = savedInstanceState.getSerializable("converter") as Converter
            textF = savedInstanceState.getString("from").toString()
            spinArray = savedInstanceState.getStringArray("array") as Array<String>
            //fromPos = savedInstanceState.getInt("fromSpin")
            //toPos = savedInstanceState.getInt("toSpin")


        } else {
            println("fuck................................")
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
        if(savedInstanceState != null){
            selItem(view)
        }
    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (textF != "") {
            textT = converter.convertFromTo(
                fromSpinner.selectedItem.toString(),
                toSpinner.selectedItem.toString(), textF
            )
            textTo.setText(textT)
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
                    if (textF != "") {
                        val clipboard =
                            activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
                        val clip = ClipData.newPlainText("text", textF)
                        clipboard?.setPrimaryClip(clip)
                    }
                }
                copyButton2.id -> {
                    if (textT != "") {
                        val clipboard =
                            activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
                        val clip = ClipData.newPlainText("text", textT)
                        clipboard?.setPrimaryClip(clip)
                    }
                }
                revButton.id -> {
                    val textB = textT
                    textT = textF
                    textF = textB

                    textTo.setText(textT)
                    textFrom.setText(textF)


                    fromPos = toSpinner.selectedItemPosition
                    toPos = fromSpinner.selectedItemPosition

                }
            }
            selItem(v)
        }
    }

    fun selItem(view: View){
        adapter = ArrayAdapter(view.context, android.R.layout.simple_spinner_item, spinArray)
        fromSpinner.adapter = adapter
        toSpinner.adapter = adapter
        fromSpinner.setSelection(fromPos)
        toSpinner.setSelection(toPos)
    }


    fun setFromText(buttonIndex: Int) {
        when (buttonIndex) {
            1 -> textF += "1"
            2 -> textF += "2"
            3 -> textF += "3"
            4 -> textF += "4"
            5 -> textF += "5"
            6 -> textF += "6"
            7 -> textF += "7"
            8 -> textF += "8"
            9 -> textF += "9"
            10 -> {
                if (!textF.contains(".")) {
                    textF += if (textF.isNullOrEmpty()) {
                        "0."
                    } else {
                        "."
                    }
                }
            }
            0 -> {
                if (!(textF.isNotEmpty() && textF[0] == '0' && !textF.contains("."))) {
                    textF += "0"
                }

            }
            11 -> textF = textF.dropLast(1)
            12 -> textF = ""
        }
        textFrom.setText(textF)
        textT = if (textF != "") {
            converter.convertFromTo(
                fromSpinner.selectedItem.toString(),
                toSpinner.selectedItem.toString(), textF
            )
        } else ""
        textTo.setText(textT)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putSerializable("converter", converter)
        outState.putInt("fromSpin", fromSpinner.selectedItemPosition )
        outState.putInt("toSpin", toSpinner.selectedItemPosition )
        outState.putString("from", textF)
        outState.putStringArray("array", spinArray)
    }
}