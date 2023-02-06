package com.example.football.view

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.football.R
import com.example.football.base.BaseViewModel
import com.example.football.model.api.ErrorObject
import com.example.football.model.api.FootBallApiDetailsObject
import com.example.football.model.api.asDataBaseModel
import com.example.football.repository.FootBallRepo
import com.google.gson.GsonBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

/** notice
 * if there is no Data in db and there is no internet the loading indicator will keep loading
 * if the internet is back the data didn't load automatically
 * refresh button inCase data not retrieved yet from db */

@HiltViewModel
class SharedViewModel @Inject constructor(val app: Application, private val repo:FootBallRepo) : BaseViewModel(app) {

    /* hold FootBall list */
    private val _data  = MutableLiveData<List<FootBallApiDetailsObject>>()
    val data  : MutableLiveData<List<FootBallApiDetailsObject>> get() = _data

    /* hold Offline Data */
    val offlineMovies: LiveData<List<FootBallApiDetailsObject>> = repo.offlineMatches

    /* load Offline Data */
    val askForOfflineData  = MutableLiveData(false)


    init {
        doCall()
    }

    private fun doCall() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                requestApiCall()
            }catch (e:Exception){
                handleException(e)
                /* Load Offline   */
                askForOfflineData()
            }
        }
    }

    private suspend fun requestApiCall() {
       val result =  repo.getMatchesList()
        if (result.isSuccessful){
            result.body()?.let { whenSuccess(it.matches) }
        }else{
           whenFail(result.errorBody()?.charStream()?.readText())
            /* Load Offline   */
            askForOfflineData()
        }
    }


    private suspend fun whenSuccess(matches: List<FootBallApiDetailsObject>) {
        withContext(Dispatchers.Main) {
            launch (Dispatchers.IO){ repo.saveMatchesIntoDB(matches.asDataBaseModel().reversed().toTypedArray()) }
            launch (Dispatchers.Main){  _data.value = matches.reversed() }
        }
    }


    /*  Handle wrong requests or apiKey expired  etc..  */
    private suspend fun whenFail(error: String?) {
        withContext(Dispatchers.Main){
            val pojo =  GsonBuilder().create().fromJson(error!!, ErrorObject::class.java)
            showErrorMessage.value = pojo.error
        }
    }
    
    
    /*  Handle network fail or exceptions   */
    private suspend fun handleException(e: Exception) {
        withContext(Dispatchers.Main){
            if (e is IOException){
                showErrorMessage.value = app.getString(R.string.NETWORK_ERROR)
            }else{
                showErrorMessage.value = app.getString(R.string.SOMETHING_WRONG)
            }
        }
    }

    private suspend fun askForOfflineData() {
        withContext(Dispatchers.Main){
            askForOfflineData.value = true
        }
    }

    fun loadOfflineData(){
        if (askForOfflineData.value == true && data.value == null){
            _data.value = offlineMovies.value
        }
        loading.value = false
   }


}


