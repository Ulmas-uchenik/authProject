package com.example.myapplication.ui.menu

sealed class StateMainActivity {
    data object SignByUid : StateMainActivity()
    data object Register : StateMainActivity()
    data object LoadState : StateMainActivity()
}