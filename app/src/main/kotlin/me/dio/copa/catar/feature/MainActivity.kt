package me.dio.copa.catar.feature

import dagger.hilt.android.AndroidEntryPoint
import me.dio.copa.catar.notification.scheduler.extensions.NotificationMatcherWoker
import me.dio.copa.catar.ui.theme.Copa2022Theme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import me.dio.copa.catar.extensions.observe

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeActions()
        setContent {
            Copa2022Theme {
                val state by viewModel.state.collectAsState()
                MainScreen(matches = state.matches, viewModel::toggleNotification)
            }
        }
    }

    private fun observeActions() {
        viewModel.action.observe(this) { action ->
            when (action) {
                is MainUiAction.MatchesNotFound -> TODO()
                MainUiAction.Unexpected -> TODO()
                is MainUiAction.DisableNotification ->
                    NotificationMatcherWoker.cancel(applicationContext, action.match)
                is MainUiAction.EnableNotification ->
                    NotificationMatcherWoker.start(applicationContext, action.match)
            }
        }
    }
}