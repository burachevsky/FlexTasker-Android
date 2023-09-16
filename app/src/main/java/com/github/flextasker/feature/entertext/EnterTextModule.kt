package com.github.flextasker.feature.entertext

import com.github.flextasker.core.ui.constant.NavArg
import com.github.flextasker.core.ui.ext.parcelable
import com.github.flextasker.core.ui.text.ParcelableTxt
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class EnterTextModule(fragment: EnterTextDialogFragment) {
    private val args = fragment.requireArguments()

    @Provides
    @Named(TITLE_ARG)
    fun provideTitle(): ParcelableTxt = args.parcelable(NavArg.TITLE)!!

    @Provides
    @Named(INIT_TEXT_ARG)
    fun provideInitText(): ParcelableTxt? = args.parcelable(NavArg.INIT_TEXT)

    @Provides
    fun provideActionId(): Int = args.getInt(NavArg.ACTION_ID)
}

const val TITLE_ARG = "TITLE_ARG"
const val INIT_TEXT_ARG = "INIT_TEXT_ARG"
