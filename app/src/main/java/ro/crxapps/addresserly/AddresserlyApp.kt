package ro.crxapps.addresserly

import android.app.Application
import io.realm.Realm
import ro.crxapps.addresserly.core.di.*
import ro.crxapps.addresserly.core.di.application.*

class AddresserlyApp : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
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