package com.example.football.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData



open class BaseViewModel(app: Application) : AndroidViewModel(app) {

    /* current loading  */
    private val _loading = MutableLiveData(true)
    val loading  : MutableLiveData<Boolean> get() = _loading

    /* to display error messages */
    val showErrorMessage: MutableLiveData<String?> = MutableLiveData<String?>()

    /* to display toast messages */
    val showToast: MutableLiveData<String?> = MutableLiveData<String?>()

    /* resetError to avoid reShowError with diff lifecycle */
    fun resetErrorMsg(){ showErrorMessage.value = null }

    /* resetToast to avoid toasting with diff lifecycle */
    fun resetToast(){ showToast.value = null}

}