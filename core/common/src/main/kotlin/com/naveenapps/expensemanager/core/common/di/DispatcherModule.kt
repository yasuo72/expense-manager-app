package com.naveenapps.expensemanager.core.common.di

import com.naveenapps.expensemanager.core.common.utils.AppCoroutineDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

    @Provides
    fun provideDispatcher(): AppCoroutineDispatchers {
        return AppCoroutineDispatchers(
            Dispatchers.Main,
            Dispatchers.IO,
            Dispatchers.Default,
        )
    }
}
