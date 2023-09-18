package com.example.lahagorapracticaltask.network

import com.example.lahagorapracticaltask.model.DashboardModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET(ApiConstants.DASHBOARD_DATA)
    suspend fun getDashboardData(): Response<DashboardModel>
}