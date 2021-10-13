package com.digicode.dodobattery.utils.extensions

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

fun Fragment.popBackStack() {
    getNavController().popBackStack().let {
        if (!it) {
            parentFragment?.popBackStack()
        }
    }
}

fun Fragment.getNavController(): NavController = NavHostFragment.findNavController(this)