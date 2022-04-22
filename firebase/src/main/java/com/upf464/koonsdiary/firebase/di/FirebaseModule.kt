package com.upf464.koonsdiary.firebase.di

import com.google.firebase.messaging.FirebaseMessaging
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object FirebaseModule {

    @Provides
    fun provideMessaging(): FirebaseMessaging = FirebaseMessaging.getInstance()
}
