package com.example.lahagorapracticaltask.repository

import android.content.Context
import com.example.lahagorapracticaltask.model.DashboardModel
import com.example.lahagorapracticaltask.model.Resource
import com.example.lahagorapracticaltask.network.ApiService
import com.example.lahagorapracticaltask.network.BaseApiResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.isActive
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

    /**
     * This is the demonstration of using kotlin channels it has hot behaviour it doesnt requiure any collector to start
     * Use this for handeling large stream of data
     */
    suspend fun getDashboardDatawithChannel(): Flow<Resource<DashboardModel>> {
        return channelFlow<Resource<DashboardModel>> {
            while (!isClosedForSend) {
                if (!isActive) {
                    close()
                }
                send(Resource.loading())
                send(BaseApiResponse.safeApiCall { service.getDashboardData() })
            }


        }.flowOn(dispatcherIO)
    }

}