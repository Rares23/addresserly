package ro.crxapps.addresserly.core.activities

import androidx.appcompat.app.AppCompatActivity
import ro.crxapps.addresserly.AddresserlyApp
import ro.crxapps.addresserly.core.di.application.AppComponent
import ro.crxapps.addresserly.core.di.presentation.ActivityComponent
import ro.crxapps.addresserly.core.di.presentation.ActivityModule
import ro.crxapps.addresserly.core.di.presentation.DaggerActivityComponent

open class BaseActivity : AppCompatActivity() {
    private lateinit var activityComponent: ActivityComponent

    private fun getAppComponent(): AppComponent {
        return (application as AddresserlyApp).getAppComponent()
    }

    fun getActivityComponent(): ActivityComponent {
        if(!::activityComponent.isInitialized) {
            activityComponent = DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .appComponent(getAppComponent())
                .build()
        }
        return activityComponent
    }
}