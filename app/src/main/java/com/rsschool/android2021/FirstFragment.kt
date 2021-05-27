package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.Fragment

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private var listener: GenerateButtonClickedListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as GenerateButtonClickedListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        generateButton?.setOnClickListener {
            val min = view.findViewById<TextView>(R.id.min_value)?.text.toString()
            val max = view.findViewById<TextView>(R.id.max_value)?.text.toString()
            if (validateInputParameters(min, max)) {
                listener?.onGenerateButtonClicked(min.toInt(), max.toInt())
            }
        }
    }

    private fun validateInputParameters(min: String, max: String): Boolean {
        val toast = Toast.makeText(context, "Parameters are not correct, try again", Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)
        if (!(min.isDigitsOnly() && max.isDigitsOnly()) || min.isEmpty() || max.isEmpty()) {
            toast.show()
            return false
        }
        if (min.toInt() > max.toInt() || min.toLong() > Int.MAX_VALUE || max.toLong() > Int.MAX_VALUE) {
            toast.show()
            return false
        }
        return true
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }

    interface GenerateButtonClickedListener {
        fun onGenerateButtonClicked(min: Int, max: Int)
    }
}