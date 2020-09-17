package ro.crxapps.addresserly

import android.app.Application
import io.realm.Realm
import ro.crxapps.addresserly.core.di.*
import ro.crxapps.addresserly.core.di.application.*
import ro.crxapps.addresserly.core.network.NetworkStateMonitor
import javax.inject.Inject

class AddresserlyApp : Application() {

    private lateinit var appComponent: AppComponent

    @Inject
    lateinit var networkStateMonitor: NetworkStateMonitor

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        getAppComponent().inject(this)
        networkStateMonitor.registerNetworkCallback()
    }

    fun getAppComponent() : AppComponent{
        if(!::appComponent.isInitialized) {
            appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .apiServicesModule(ApiServicesModule())
                .repositoriesModule(RepositoriesModule())
                .viewModelModule(ViewModelModule())
                .build()
        }

        return appComponent
    }

}