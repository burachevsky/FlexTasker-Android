package com.github.flextasker.core.ui.text

import android.content.Context

class ListTxt(
    private val list: List<Txt>,
    private val separator: String = ", "
): Txt {
    override fun get(context: Context): CharSequence {
        return list.joinToString(separator) { it.get(context) }
    }
}