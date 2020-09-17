package ro.crxapps.addresserly.core.network

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import javax.inject.Inject


class NetworkStateMonitor @Inject constructor(private val application: Application) {

    companion object {
        var isNetworkConnected: Boolean = false
    }

    private val networkConnectionListenerHash: HashSet<NetworkConnectionListener> = HashSet()

    fun registerNetworkCallback() {
        try {
            val connectivityManager =
                application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            connectivityManager.registerDefaultNetworkCallback(object : NetworkCallback() {
                override fun onAvailable(network: Network) {
                    isNetworkConnected = true // Global Static Variable
                    updateConnectionStateListener(isNetworkConnected)
                }

                override fun onLost(network: Network) {
                    isNetworkConnected = false // Global Static Variable
                    updateConnectionStateListener(isNetworkConnected)
                }
            }
            )
            isNetworkConnected = false
        } catch (e: Exception) {
            isNetworkConnected = false
        }
    }

    private fun updateConnectionStateListener(connection: Boolean) {
        networkConnectionListenerHash.forEach {
            it.onConnectionChangedState(connection)
        }
    }

    fun addNetworkConnectionListener(networkConnectionListener: NetworkConnectionListener) {
        networkConnectionListenerHash.add(networkConnectionListener)
    }

    fun removeNetworkConnectionListener(networkConnectionListener: NetworkConnectionListener) {
        networkConnectionListenerHash.remove(networkConnectionListener)
    }

    interface NetworkConnectionListener {
        fun onConnectionChangedState(isConnected: Boolean)
    }
}