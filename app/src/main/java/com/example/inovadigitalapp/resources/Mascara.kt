package com.example.inovadigitalapp.resources

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.NumberFormat
import java.util.Locale

class Mascara {

fun aplicarMascaraData(editText: EditText) {
    editText.addTextChangedListener(object : TextWatcher {
        private var isUpdating = false
        private val mascara = "##/##/####"

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            if (isUpdating) return

            isUpdating = true

            val str = s.toString().filter { it.isDigit() }
            val formatted = StringBuilder()

            var i = 0
            for (m in mascara.toCharArray()) {
                if (m != '#' && str.length > i) {
                    formatted.append(m)
                } else if (str.length > i) {
                    formatted.append(str[i])
                    i++
                }
            }

            editText.setText(formatted.toString())
            editText.setSelection(formatted.length)

            isUpdating = false
        }
    })
}

    fun aplicarMascaraMonetaria(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            private var isUpdating = false
            private val locale = Locale("pt", "BR")

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (isUpdating) return

                isUpdating = true

                val cleanString = s.toString().replace("[R$,.\\s]".toRegex(), "")
                val parsed = cleanString.toDoubleOrNull() ?: 0.0
                val formatted = NumberFormat.getCurrencyInstance(locale).format(parsed / 100)

                editText.setText(formatted)
                editText.setSelection(formatted.length)

                isUpdating = false
            }
        })
    }


}
