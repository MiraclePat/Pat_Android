package com.pat.data.di

import android.content.Context
import com.pat.data.repository.image.GalleryRepositoryImpl
import com.weit.domain.repository.image.GalleryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class GalleryModule {
    @ActivityScoped
    @Provides
    fun provideGalleryRepository(@ActivityContext context: Context): GalleryRepository =
        GalleryRepositoryImpl(context)
}
