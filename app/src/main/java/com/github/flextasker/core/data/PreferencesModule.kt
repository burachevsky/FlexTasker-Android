package com.github.flextasker.core.data

import com.github.flextasker.core.domain.AccountInfo
import dagger.Binds
import dagger.Module

@Module
abstract class PreferencesModule {

    @Binds
    abstract fun bindAccountInfo(impl: AccountInfoImpl): AccountInfo
}