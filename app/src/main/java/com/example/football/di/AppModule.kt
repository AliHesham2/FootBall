package com.example.football.di

import android.content.Context
import com.example.football.BuildConfig
import com.example.football.model.api.FootBallApi
import com.example.football.model.db.MatchesDB
import com.example.football.util.Util
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private fun provideMoshiConverter(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    private fun apiKeyAsQuery(chain: Interceptor.Chain) = chain.proceed(
        chain.request()
            .newBuilder()
            .addHeader("X-Auth-Token",BuildConfig.FOOT_BALL_API_KEY)
            .build())

    @Provides
    @Singleton
    fun provideRetrofitInstance(): FootBallApi =
        Retrofit.Builder()
            .baseUrl(Util.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(provideMoshiConverter()))
            .client(OkHttpClient.Builder().addInterceptor { chain -> apiKeyAsQuery(chain) }.build())
            .build()
            .create(FootBallApi::class.java)


    @Provides
    @Singleton
    fun provideDataBaseInstance(@ApplicationContext context: Context): MatchesDB {
        return MatchesDB.getInstance(context)
    }

}