package com.example.lahagorapracticaltask.repository

import android.content.Context
import com.example.lahagorapracticaltask.model.DashboardModel
import com.example.lahagorapracticaltask.model.Resource
import com.example.lahagorapracticaltask.network.ApiService
import com.example.lahagorapracticaltask.network.BaseApiResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HomeFragmentRepository @Inject constructor(
    val service: ApiService,
    val dispatcherIO: CoroutineDispatcher,
    @ApplicationContext val context: Context
) {

    suspend fun getDashboardData(): Flow<Resource<DashboardModel>> {
        return flow<Resource<DashboardModel>> {
            emit(Resource.loading())
            emit(BaseApiResponse.safeApiCall { service.getDashboardData() })
        }.flowOn(dispatcherIO)
    }

}