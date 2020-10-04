package com.example.bnettask.web

import com.example.bnettask.data.NoteResponse
import com.example.bnettask.data.SessionResponse
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface ApiService {

    @POST(".")
    @FormUrlEncoded
    fun getSessionId(@Field("a") method: String
    ): Single<SessionResponse>

    @POST(".")
    @FormUrlEncoded
    fun getNotes(
        @Field("session") sessionId: String,
        @Field("a") method: String
    ): Observable<NoteResponse>

    @POST(".")
    @FormUrlEncoded
    fun addNote(
        @Field("session") sessionId: String,
        @Field("a") method: String,
        @Field("body") body: String
    ): Completable

    @POST(".")
    fun editNote(
        @Field("session") sessionId: String,
        @Field("id") noteId: String,
        @Field("a") method: String,
        @Field("body") body: String
    ): Completable
}
