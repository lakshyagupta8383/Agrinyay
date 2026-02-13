package com.example.agrinyay
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.agrinyay.navigation.RootNavGraph
import com.example.agrinyay.ui.theme.AgriNyayTheme

class AgriNyayAppActivity:ComponentActivity(){
    override fun onCreate(savedInstanceState:Bundle?){
        super.onCreate(savedInstanceState)
        setContent{
            AgriNyayTheme{
                RootNavGraph()
            }
        }
    }
}
