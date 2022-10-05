package com.example.lr1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

interface OnSelectedButtonListener {
    fun onButtonSelected(buttonIndex: Int)
}

class KeyboardFragment : Fragment(), View.OnClickListener, View.OnLongClickListener {

    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var button3: Button
    private lateinit var button4: Button
    private lateinit var button5: Button
    private lateinit var button6: Button
    private lateinit var button7: Button
    private lateinit var button8: Button
    private lateinit var button9: Button
    private lateinit var button0: Button
    private lateinit var buttonDel: Button
    private lateinit var buttonDot: Button


    //private lateinit var meow: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_keyboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button1 = view.findViewById(R.id.button1)
        button2 = view.findViewById(R.id.button2)
        button3 = view.findViewById(R.id.button3)
        button4 = view.findViewById(R.id.button4)
        button5 = view.findViewById(R.id.button5)
        button6 = view.findViewById(R.id.button6)
        button7 = view.findViewById(R.id.button7)
        button8 = view.findViewById(R.id.button8)
        button9 = view.findViewById(R.id.button9)
        button0 = view.findViewById(R.id.button0)
        buttonDel = view.findViewById(R.id.button_del)
        buttonDot = view.findViewById(R.id.button_dot)

        button1.setOnClickListener(this)
        button2.setOnClickListener(this)
        button3.setOnClickListener(this)
        button4.setOnClickListener(this)
        button5.setOnClickListener(this)
        button6.setOnClickListener(this)
        button7.setOnClickListener(this)
        button8.setOnClickListener(this)
        button9.setOnClickListener(this)
        button0.setOnClickListener(this)
        buttonDel.setOnClickListener(this)
        buttonDot.setOnClickListener(this)
        buttonDel.setOnLongClickListener(this)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            KeyboardFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }




    private fun translateIdToIndex(id: Int): Int {
        var index = -1
        when (id) {
            R.id.button1 -> index = 1
            R.id.button2 -> index = 2
            R.id.button3 -> index = 3
            R.id.button4 -> index = 4
            R.id.button5 -> index = 5
            R.id.button6 -> index = 6
            R.id.button7 -> index = 7
            R.id.button8 -> index = 8
            R.id.button9 -> index = 9
            R.id.button0 -> index = 0
            R.id.button_dot -> index = 10
            R.id.button_del -> index = 11
        }
        return index
    }

    override fun onClick(v: View?) {
        val buttonIndex = translateIdToIndex(v!!.id)
        val listener = activity as OnSelectedButtonListener?
        listener?.onButtonSelected(buttonIndex)
    }

    override fun onLongClick(v: View?): Boolean {
        val buttonIndex = translateIdToIndex(v!!.id) + 1
        val listener = activity as OnSelectedButtonListener?
        listener?.onButtonSelected(buttonIndex)
        return true
    }
}