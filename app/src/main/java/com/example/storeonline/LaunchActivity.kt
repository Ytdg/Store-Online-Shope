package com.example.storeonline

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.feature_authorization.AuthorizationActivity
import com.example.feature_products.MainActivity
import com.example.storeonline.init.LocalUserReg
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LaunchActivity : ComponentActivity() {

    @Inject
    lateinit var localUserReg: LocalUserReg

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(applicationContext, MainActivity::class.java))
        finish()
    }

    @Composable
    fun test() {
        val state = remember {
            mutableStateOf(false)
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column {
                Text(
                    text = state.value.toString(),
                    color = Color.Black,
                    modifier = Modifier.clickable { state.value = !state.value })
                Button(onClick = { state.value }) {
                    if (state.value) {
                        Text(text = "VEE")
                    }
                }
            }
        }
        d(state)
    }

    @Composable
    fun d(state: State<Boolean>) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            if (state.value) {
                Text(text = "Pressed")
            }
        }
    }


    private fun existsLocalUser() {
        val res = localUserReg.requestLocalUser()
        if (res) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            startActivity(Intent(this, AuthorizationActivity::class.java))
        }
    }
}
