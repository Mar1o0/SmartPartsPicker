package com.vlad.sharaga.core.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.afterTextChanged(afterTextChanged: (chars: Editable?) -> Unit = { _ -> }) {
    addTextChangedListener(object : TextWatcher {
        private var isEdit: Boolean = false
        override fun afterTextChanged(s: Editable?) {
            if (isEdit) return
            isEdit = true
            try {
                afterTextChanged(s)
            } finally {
                isEdit = false
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

    })
}

fun EditText.beforeTextChanged(beforeTextChanged: (chars: CharSequence?, start: Int, count: Int, after: Int) -> Unit = { _, _, _, _ -> }) {
    addTextChangedListener(object : TextWatcher {
        private var isEdit: Boolean = false
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            if (isEdit) return
            isEdit = true
            try {
                beforeTextChanged(s, start, count, after)
            } finally {
                isEdit = false
            }
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

    })
}

fun EditText.onTextChanged(onTextChanged: (chars: CharSequence?, start: Int, count: Int, after: Int) -> Unit = { _, _, _, _ -> }) {
    addTextChangedListener(object : TextWatcher {
        private var isEdit: Boolean = false
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (isEdit) return
            isEdit = true
            try {
                onTextChanged(s, start, before, count)
            } finally {
                isEdit = false
            }
        }

    })
}