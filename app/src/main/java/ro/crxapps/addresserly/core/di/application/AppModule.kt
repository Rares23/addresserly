package ro.crxapps.addresserly.core.di.application

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {
    @Singleton
    @Provides
    fun provideApplication(): Application {
        return application
    }
}