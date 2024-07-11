package com.example.feature_authorization.ui.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.authorization_data.repozitory.Repozitory
import com.example.authorization_data.repozitory.ResponseResult
import com.example.authorization_data.repozitory.TypeAuthotization
import com.example.authorization_data.repozitory.User
import com.example.feature_authorization.ui.events.EventsLogin
import com.example.feature_authorization.ui.events.EventsSignIn
import com.example.feature_authorization.utils.ValidationData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class ViewModelAuthorization @Inject constructor(val repozitory: Repozitory) : ViewModel() {
    private val _stateRequest: MutableStateFlow<StateRequest> = MutableStateFlow(StateRequest.None)
    val stateRequest: StateFlow<StateRequest> = _stateRequest

    private val _stateDataSign: MutableStateFlow<StateDataSignIn> =
        MutableStateFlow(StateDataSignIn())
    val stateDataSignIn: StateFlow<StateDataSignIn> = _stateDataSign

    private val _stateDataLogin: MutableStateFlow<StateDataLogin> =
        MutableStateFlow(StateDataLogin())
    val stateDataLogin: StateFlow<StateDataLogin> = _stateDataLogin

    val _stateReg:MutableState<Boolean?> = mutableStateOf(null)
   init {

       viewModelScope.launch {
           _stateReg.value= repozitory.requestSignUser()
       }

   }
    private var job: Job = Job()



    private fun response(responseResult: ResponseResult) {

        when (responseResult) {
            is ResponseResult.Successfully<*> -> {
                _stateRequest.update { StateRequest.Successful }
            }

            is ResponseResult.Error -> {
                _stateRequest.update { StateRequest.Error(message = responseResult.message) }
            }
        }
    }

    fun finishUiRequest() = _stateRequest.update { StateRequest.None }
    fun onEventSign(eventsSignIn: EventsSignIn) {

        when (eventsSignIn) {

            EventsSignIn.SignIn -> {
                _stateRequest.update {
                    StateRequest.Loading
                }
                job.cancel()
                job = viewModelScope.launch {
                    delay(1000)
                    response(with(stateDataSignIn.value) {
                        repozitory.requestAuthorization(
                            User(id = 0, password = password.value, email = email.value),
                            typeAuthorization = TypeAuthotization.SignIn
                        )
                    }

                    )
                }
            }

            is EventsSignIn.ChangeEmail -> {
                _stateDataSign.update {
                    it.copy(
                        isValid = ValidationData.allData(
                            password = it.password.value,
                            email = eventsSignIn.value,
                            confirmPassword = it.confirmPassword.value
                        ),
                        email = InputField(
                            eventsSignIn.value,
                            isValid = ValidationData.isValidEmail(eventsSignIn.value)
                        )
                    )
                }
            }

            is EventsSignIn.ChangePassword -> {
                _stateDataSign.update {
                    it.copy(
                        isValid = ValidationData.allData(
                            password = eventsSignIn.value,
                            email = it.email.value,
                            confirmPassword = it.confirmPassword.value
                        ),
                        password = InputField(
                            eventsSignIn.value,
                            isValid = ValidationData.isValidPassword(eventsSignIn.value)
                        )
                    )
                }
            }

            is EventsSignIn.ConfirmPassword -> {
                _stateDataSign.update {
                    it.copy(
                        isValid = ValidationData.allData(
                            password = it.password.value,
                            email = it.email.value,
                            confirmPassword = eventsSignIn.value
                        ),
                        confirmPassword = InputField(
                            eventsSignIn.value,
                            isValid = it.password.value == eventsSignIn.value
                        )
                    )
                }
            }
        }
    }

    fun onEventLogin(eventsLogin: EventsLogin) {
        when (eventsLogin) {
            EventsLogin.Login -> {
                _stateRequest.update {
                    StateRequest.Loading
                }
                job.cancel()
                job = viewModelScope.launch {
                    delay(1000)
                    response(with(stateDataLogin.value) {
                        repozitory.requestAuthorization(
                            User(id = 0, password = password.value, email = email.value),
                            typeAuthorization = TypeAuthotization.Login
                        )
                    }
                    )
                }
            }

            is EventsLogin.ChangeEmail -> {
                _stateDataLogin.update {
                    it.copy(
                        isValid = eventsLogin.value.isNotBlank()&&it.password.value.isNotBlank(),
                        email = InputField(value = eventsLogin.value)
                    )
                }
            }

            is EventsLogin.ChangePassword -> {
                _stateDataLogin.update {
                    it.copy(
                        isValid = eventsLogin.value.isNotBlank()&&it.email.value.isNotBlank(),
                        password = InputField(value = eventsLogin.value)
                    )
                }
            }
        }
    }
}