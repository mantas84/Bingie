package eu.oncreate.bingie.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onCompletion
import timber.log.Timber

class NetworkStatusManager(applicationContext: Context) {

    sealed class Status {
        class Connected(isMetered: Boolean) : Status()
        class Losing(isMetered: Boolean) : Status()
        object IsLost : Status()
        object Unavailable : Status()
        object Unknown : Status()
    }

    private var _status: MutableStateFlow<Status> = MutableStateFlow(Status.Unknown)

    val status: StateFlow<Status> = _status

    fun isOnline(): Boolean {
        return when (_status.value) {
            is Status.Connected -> true
            is Status.Losing -> true
            is Status.IsLost -> false
            is Status.Unavailable -> false
            is Status.Unknown -> false
        }
    }

    private val connectivityManager =
        applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val callback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            Timber.d("Network status Connected, metered ${isMetered()}")
            _status.value = Status.Connected(isMetered())
        }

        override fun onLosing(network: Network, maxMsToLive: Int) {
            super.onLosing(network, maxMsToLive)
            Timber.d("Network status Losing, metered ${isMetered()}")
            _status.value = Status.Losing(isMetered())
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            Timber.d("Network status IsLost")
            _status.value = Status.IsLost
        }

        override fun onUnavailable() {
            super.onUnavailable()
            Timber.d("Network status Unavailable")
            _status.value = Status.Unavailable
        }
    }

    private fun register() {
        connectivityManager.registerDefaultNetworkCallback(callback)
    }

    private fun unregister() {
        connectivityManager.unregisterNetworkCallback(callback)
    }

    init {
        register()
        _status
            .onCompletion { unregister() }
    }

    private fun isMetered(): Boolean {
        return connectivityManager.isActiveNetworkMetered
    }
}
