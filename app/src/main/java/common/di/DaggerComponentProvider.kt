package common.di


import android.app.Activity
import androidx.fragment.app.Fragment

interface DaggerComponentProvider {
    val component: AppComponent
}

val Activity.injector get() = (application as DaggerComponentProvider).component
