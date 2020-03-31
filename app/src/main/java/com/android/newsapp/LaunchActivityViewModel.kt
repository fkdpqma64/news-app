package com.android.newsapp

import android.content.Context
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.distinctUntilChanged
import common.lib.livedata.CustomViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject


class LaunchActivityViewModel @Inject constructor(
    private val context: Context
) : CustomViewModel() {

    private val mViewData = MediatorLiveData<List<Any>>()
    val viewData = mViewData.distinctUntilChanged()
    private val job: MutableList<Job> = mutableListOf()

    init {

    }
}