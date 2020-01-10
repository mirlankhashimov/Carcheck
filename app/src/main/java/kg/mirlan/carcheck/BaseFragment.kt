package kg.mirlan.carcheck

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment(@LayoutRes val layoutRes: Int) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        rootView: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(layoutRes, rootView, false)
    }
}