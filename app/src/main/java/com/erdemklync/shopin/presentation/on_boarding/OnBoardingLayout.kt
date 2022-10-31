package com.erdemklync.shopin.presentation.on_boarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class OnBoardingLayout : Fragment() {
    private var layout: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            layout = it.getInt("layout")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(layout, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(layout: Int) =
            OnBoardingLayout().apply {
                arguments = Bundle().apply {
                    putInt("layout", layout)
                }
            }
    }
}