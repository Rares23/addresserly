package ro.crxapps.addresserly.core.activities

import androidx.appcompat.app.AppCompatActivity
import ro.crxapps.addresserly.AddresserlyApp
import ro.crxapps.addresserly.core.di.application.AppComponent
import ro.crxapps.addresserly.core.di.presentation.DaggerPresentationComponent
import ro.crxapps.addresserly.core.di.presentation.PresentationComponent
import ro.crxapps.addresserly.core.di.presentation.PresentationModule

open class BaseActivity : AppCompatActivity() {
    private fun getAppComponent(): AppComponent {
        return (application as AddresserlyApp).appComponent
    }

    fun getPresentationComponent(): PresentationComponent {
        return DaggerPresentationComponent.builder()
            .presentationModule(PresentationModule(this))
            .appComponent(getAppComponent())
            .build()
    }
}