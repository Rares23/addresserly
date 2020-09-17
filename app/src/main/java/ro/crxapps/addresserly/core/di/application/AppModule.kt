package ro.crxapps.addresserly.core.di.application

import android.app.Application
import android.os.Handler
import android.os.Looper
import dagger.Module
import dagger.Provides
import ro.crxapps.addresserly.core.data.uitls.UniqIdGenerator
import ro.crxapps.addresserly.core.network.NetworkStateMonitor
import ro.crxapps.addresserly.locations.utils.DistanceCalculator
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {
    @Singleton
    @Provides
    fun provideApplication(): Application {
        return application
    }

    @Singleton
    @Provides
    fun provideDistanceCalculator() : DistanceCalculator {
        return DistanceCalculator()
    }

    @Singleton
    @Provides
    fun provideUniqIdGenerator(): UniqIdGenerator {
        return UniqIdGenerator()
    }

    @Singleton
    @Provides
    fun provideNetworkStateMonitor(application: Application, handler: Handler): NetworkStateMonitor {
        return NetworkStateMonitor(application, handler)
    }

    @Singleton
    @Provides
    fun provideMainHandler(): Handler {
        return Handler(Looper.getMainLooper())
    }
}