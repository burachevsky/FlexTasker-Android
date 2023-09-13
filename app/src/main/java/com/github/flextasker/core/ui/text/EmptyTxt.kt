package com.github.flextasker.core.ui.text

import android.content.Context
import kotlinx.parcelize.Parcelize

@Parcelize
object EmptyTxt : ParcelableTxt {
    override fun get(context: Context): CharSequence {
        return ""
    }
}