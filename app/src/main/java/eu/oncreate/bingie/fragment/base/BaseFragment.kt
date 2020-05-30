package eu.oncreate.bingie.fragment.base

import android.content.Context
import androidx.navigation.fragment.findNavController
import com.airbnb.mvrx.BaseMvRxFragment
import dagger.android.support.AndroidSupportInjection

abstract class BaseFragment : BaseMvRxFragment() {

    val navigator by lazy { this.findNavController() }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}
