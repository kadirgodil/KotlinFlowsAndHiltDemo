package com.example.lahagorapracticaltask.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lahagorapracticaltask.R
import com.example.lahagorapracticaltask.model.DashboardModel
import com.example.lahagorapracticaltask.model.Resource
import com.example.lahagorapracticaltask.preference.Preferences
import com.example.lahagorapracticaltask.repository.HomeFragmentRepository
import com.example.lahagorapracticaltask.utils.NetworkHelper
import com.example.lahagorapracticaltask.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val homeFragmentRepository: HomeFragmentRepository,
    private val networkHelper: NetworkHelper,
    private val preferences: Preferences,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private var _dashboardApiResponse = MutableLiveData<Resource<DashboardModel>>()
    val dashboardApiResponse: LiveData<Resource<DashboardModel>> get() = _dashboardApiResponse
    var errorMessage = SingleLiveEvent<String>()

    fun getDashboardList() {
        if (networkHelper.isNetworkConnected()) {

            viewModelScope.launch {
                homeFragmentRepository.getDashboardData().collect() {
                    _dashboardApiResponse.value = it
                }
            }
        } else {
            //no internet
            setError(context.getString(R.string.no_internet))
        }

    }

    fun setError(mssg: String) {
        errorMessage.value = mssg
    }
}