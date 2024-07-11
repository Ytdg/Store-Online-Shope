package com.example.feature_authorization.ui.models

import javax.annotation.concurrent.Immutable


sealed class StateRequest {
    data class Error(val message: String) : StateRequest()
    object None : StateRequest()
    object Loading : StateRequest()
    object  Successful:StateRequest()
}
@Immutable
data class StateDataSignIn(
    val isValid:Boolean=false,
    val email:InputField=InputField(),
    val password:InputField=InputField(),
    val confirmPassword: InputField=InputField()
)
@Immutable
data class  StateDataLogin(
    val isValid:Boolean=false,
    val email:InputField=InputField(),
    val password:InputField=InputField(),
)
@Immutable
data class  InputField(
    val value:String=String(),
    val isValid: Boolean?=null
)
