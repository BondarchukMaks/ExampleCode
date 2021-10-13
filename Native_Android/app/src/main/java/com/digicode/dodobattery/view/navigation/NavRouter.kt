package com.digicode.dodobattery.view.navigation

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log.e
import androidx.annotation.IdRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.digicode.dodobattery.R
import com.digicode.dodobattery.utils.Constants
import com.digicode.dodobattery.utils.Utils
import com.digicode.dodobattery.view.ui.base.fragment.BaseFragment
import com.digicode.dodobattery.view.ui.dialog.FreeWidgetDialog
import com.digicode.dodobattery.view.ui.dialog.RateUsDialog
import com.digicode.dodobattery.view.ui.dialog.ShareDialog
import com.digicode.dodobattery.view.ui.main.MainFragment
import com.digicode.dodobattery.view.ui.menu.MenuFragment
import com.digicode.dodobattery.view.ui.zodiacs.ZodiacsDialog
import org.w3c.dom.Text


object NavRouter {

    //Menu
    fun startMenuScreen(fragment: MainFragment) {
        navigateToAction(fragment, R.id.action_mainFragment_to_menuFragment)
    }

    //Tutorial
    fun startTutorialScreen(fragment: MenuFragment) {
        navigateToAction(fragment, R.id.action_menuFragment_to_tutorialFragment)
    }

    fun startTutorialScreen(fragment: MainFragment) {
        navigateToAction(fragment, R.id.action_mainFragment_to_tutorialFragment)
    }

    //Terms
    fun startTermsScreen(fragment: MenuFragment) {
        navigateToAction(fragment, R.id.action_menuFragment_to_termsFragment)
    }

    //Privacy
    fun startPrivacyScreen(fragment: MenuFragment) {
        navigateToAction(fragment, R.id.action_menuFragment_to_privacyPolicyFragment)
    }

    //Dialog fragment
    fun showZodiacsDialog(baseFragment: BaseFragment, items: IntArray?, selected: Int) {
        val zodiacDialog = ZodiacsDialog.newInstance(items, selected)
        showDialog(baseFragment, zodiacDialog)
    }

    fun showRateUsDialog(baseFragment: BaseFragment) : RateUsDialog{
        val rateUsDialog = RateUsDialog.newInstance()
        showDialog(baseFragment, rateUsDialog)
        return rateUsDialog
    }

    fun showFreeWidgetDialog(baseFragment: BaseFragment, isNewUser: Boolean) {
        val freeWidgetDialog = FreeWidgetDialog.newInstance(isNewUser)
        showDialog(baseFragment, freeWidgetDialog)
    }

    fun showShareDialog(baseFragment: BaseFragment, deepLink: String) :ShareDialog {
        val shareDialog = ShareDialog.newInstance(deepLink)
        showDialog(baseFragment, shareDialog)
        return shareDialog
    }

    fun showAlertErrorDialog(baseFragment: BaseFragment, message : String) {
        val builder = AlertDialog.Builder(baseFragment.context)
        builder.setTitle("Error")
        builder.setMessage(message)
        builder.setCancelable(true)

        builder.setPositiveButton(
            "Ok"
        ) { dialog, id -> dialog.cancel() }

        val alert = builder.create()
        alert.show()
    }

    private fun showDialog(baseFragment: BaseFragment, dialogFragment: DialogFragment) {
        showDialog(baseFragment.childFragmentManager, dialogFragment)
    }

    private fun showDialog(
        fragmentManager: FragmentManager,
        dialogFragment: DialogFragment
    ) {
        try {
            fragmentManager.beginTransaction().let { ft ->
                val tag = Utils.generateTag(dialogFragment.javaClass.kotlin)

                val oldFragment = fragmentManager.findFragmentByTag(tag)

                if (oldFragment != null) {
                    ft.remove(oldFragment)
                }

                ft.add(dialogFragment, tag)
                ft.commit()
            }
        } catch (exception: NullPointerException) {
            e("DoDo", "exception: $exception")
        } catch (exception: IllegalStateException) {
            e("DoDo", "exception: $exception")
        }
    }

    private fun navigateToAction(fragment: Fragment, @IdRes resId: Int) {
        navigateToAction(fragment, resId, null)
    }

    private fun navigateToAction(fragment: Fragment, @IdRes resId: Int, args: Bundle?) {
        navigateToAction(getController(fragment), resId, args)
    }

    private fun navigateToAction(navController: NavController, @IdRes resId: Int, args: Bundle?) {
        try {
            val graph = navController.currentDestination

            val action = graph?.getAction(resId)

            val xmlNavOptions = action?.navOptions

            val navOptions = buildNavOptions(xmlNavOptions)

            navController.navigate(resId, args, navOptions)

        } catch (exception: IllegalArgumentException) {
            e("Error", exception.toString())
        }
    }

    private fun getController(fragment: Fragment): NavController {
        return fragment.findNavController()
    }

    private fun buildNavOptions(xmlNavOptions: NavOptions?): NavOptions {
        return if (xmlNavOptions == null) {
            buildDefNavOptions()
        } else {
            buildNewNavOptions(xmlNavOptions)
        }
    }

    private fun buildDefNavOptions(): NavOptions {
        val builder = NavOptions.Builder()
        builder.setEnterAnim(R.anim.fragment_fade_in)
        builder.setExitAnim(R.anim.fragment_fade_out)
        builder.setPopEnterAnim(R.anim.fragment_fade_in)
        builder.setPopExitAnim(R.anim.fragment_fade_out)
        return builder.build()
    }

    private fun buildNewNavOptions(xmlNavOptions: NavOptions): NavOptions {
        val builder = NavOptions.Builder()
        builder.setLaunchSingleTop(xmlNavOptions.shouldLaunchSingleTop())

        builder.setEnterAnim(
            if (xmlNavOptions.enterAnim == Constants.EMPTY_INT_VAL) {
                R.anim.fragment_fade_in
            } else {
                xmlNavOptions.enterAnim
            }
        )

        builder.setExitAnim(
            if (xmlNavOptions.exitAnim == Constants.EMPTY_INT_VAL) {
                R.anim.fragment_fade_out
            } else {
                xmlNavOptions.exitAnim
            }
        )

        builder.setPopEnterAnim(
            if (xmlNavOptions.popEnterAnim == Constants.EMPTY_INT_VAL) {
                R.anim.fragment_fade_in
            } else {
                xmlNavOptions.popEnterAnim
            }
        )

        builder.setPopExitAnim(
            if (xmlNavOptions.popExitAnim == Constants.EMPTY_INT_VAL) {
                R.anim.fragment_fade_out
            } else {
                xmlNavOptions.popExitAnim
            }
        )

        if (xmlNavOptions.popUpTo != Constants.EMPTY_INT_VAL) {
            builder.setPopUpTo(xmlNavOptions.popUpTo, xmlNavOptions.isPopUpToInclusive)
        }

        return builder.build()
    }
}