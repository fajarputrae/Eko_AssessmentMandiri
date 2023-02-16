package com.example.pilottestmandirieko.view.base

import android.content.DialogInterface
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel: ViewModel() {
    val isLoading = MutableLiveData<Boolean>().apply { value = false }
    val openDialog: MutableLiveData<HashMap<Int, DialogInterface.OnClickListener>> =
        MutableLiveData()
    val toastMessage: MutableLiveData<Any> = MutableLiveData()
    val snackbarMessage: MutableLiveData<Any> = MutableLiveData()
    val networkError: MutableLiveData<Any> = MutableLiveData()
    val showLoading: MutableLiveData<Boolean> = MutableLiveData()
}