package com.ascri.koinsample.utils.di

import com.ascri.koinsample.data.dataSources.remote.CatAPI
import com.ascri.koinsample.data.repositories.CatRepository
import com.ascri.koinsample.ui.main.MainViewModel
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonSetter
import com.fasterxml.jackson.annotation.Nulls
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

const val CAT_API_BASE_URL = "https://api.thecatapi.com/v1/"

val appModules = module {
    single {
        createWebService<CatAPI>(
            okHttpClient = createHttpClient(),
            factory = RxJava2CallAdapterFactory.create(),
            baseUrl = CAT_API_BASE_URL,
            objectMapper = createJackson()
        )
    }
    factory { CatRepository(catApi = get()) }
    viewModel {
        MainViewModel(catRepository = get())
    }
}

fun createJackson(): ObjectMapper {
    return ObjectMapper()
        .setSerializationInclusion(JsonInclude.Include.NON_DEFAULT)
        .setDefaultSetterInfo(JsonSetter.Value.forValueNulls(Nulls.SKIP))
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .registerModule(KotlinModule(nullisSameAsDefault = true))
}

/* Returns a custom OkHttpClient instance with interceptor. Used for building Retrofit service */
fun createHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .writeTimeout(30, TimeUnit.SECONDS)
        .callTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()
}

/* function to build our Retrofit service */
inline fun <reified T> createWebService(
    okHttpClient: OkHttpClient,
    factory: CallAdapter.Factory,
    baseUrl: String,
    objectMapper: ObjectMapper
): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(JacksonConverterFactory.create(objectMapper))
        .addCallAdapterFactory(factory)
        .client(okHttpClient)
        .build()
    return retrofit.create(T::class.java)
}