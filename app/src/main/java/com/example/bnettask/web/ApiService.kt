package com.example.bnettask.web

import com.example.bnettask.data.EntryResponse
import com.example.bnettask.data.SessionResponse
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*


interface ApiService {

    @POST(".")
    @FormUrlEncoded
    fun getSessionId(@Field("a") method: String
    ): Single<SessionResponse>

    @POST(".")
    @FormUrlEncoded
    fun getEntries(
        @Field("session") sessionId: String,
        @Field("a") method: String
    ): Single<EntryResponse>

    @POST(".")
    @FormUrlEncoded
    fun addEntry(
        @Field("session") sessionId: String,
        @Field("a") method: String,
        @Field("body") body: String
    ): Completable

    @POST(".")
    fun editEntry(
        @Field("session") sessionId: String,
        @Field("id") noteId: String,
        @Field("a") method: String,
        @Field("body") body: String
    ): Completable
}
