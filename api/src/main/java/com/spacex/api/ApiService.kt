package com.spacex.api

import com.spacex.api.model.Launcher
import io.reactivex.Single
import retrofit2.http.GET

interface ApiService {
    @GET("launches")
    fun getLauncherList(): Single<List<Launcher>>

    // makes http get request with dynamic endpoint and header
   /* @GET("dummy/{userId}/list")
    fun doDummyGetRequestVariablePathCall(
        @Path("userId") userId: String,
        @Header("some-header-key") someHeader: String
    ): Single<DummyResponse>*/
}