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
    ): Observable<EntryResponse>

    @POST(".")
    @FormUrlEncoded
    fun addEntry(
        @Field("session") sessionId: String,
        @Field("a") method: String,
        @Field("body") body: String
    ): Completable

    @POST(".")
    @FormUrlEncoded
    fun editEntry(
        @Field("session") sessionId: String,
        @Field("a") method: String,
        @Field("id") entryId: String,
        @Field("body") body: String
    ): Completable

    @POST(".")
    @FormUrlEncoded
    fun removeEntry(
        @Field("session") sessionId: String,
        @Field("a") method: String,
        @Field("id") entryId: String
    ): Completable
}
