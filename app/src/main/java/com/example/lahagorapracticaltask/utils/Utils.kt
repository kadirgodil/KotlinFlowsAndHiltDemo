package com.example.lahagorapracticaltask.utils

import com.example.lahagorapracticaltask.model.DashboardModel

class Utils {
    companion object {
        var isListEmpty = SingleLiveEvent<Boolean>()
        var isFilterApplied = false
        var isSearchActive = true
        var isFilterRemoved = false
    }
}