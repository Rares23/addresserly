package ro.crxapps.addresserly.core.fragments

import androidx.fragment.app.Fragment
import ro.crxapps.addresserly.core.activities.BaseActivity
import ro.crxapps.addresserly.core.di.presentation.ActivityComponent

abstract class BaseFragment: Fragment() {
    protected fun getActivityComponent(): ActivityComponent {
        return (activity as BaseActivity).getActivityComponent()
    }
}