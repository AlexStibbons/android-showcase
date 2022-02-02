package com.alexstibbons.feature.profile.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.alexstibbons.feature.profile.R
import com.alexstibbons.feature.profile.databinding.ActivityProfileBinding
import com.alexstibbons.feature.profile.injectFeature
import com.alexstibbons.feature.profile.presentation.dialog.InputDialog
import com.alexstibbons.feature.profile.presentation.dialog.InputDialogData
import com.alexstibbons.showcase.exhaustive
import com.alexstibbons.showcase.navigator.NavigateTo
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

internal class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private val viewModel: ProfileViewModel by viewModel()
    private val auth: FirebaseAuth by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        injectFeature()

        viewModel.observeState().observe(this, Observer { state ->
            state ?: return@Observer

            renderState(state)
        })

        with(binding) {
            back.setOnClickListener { onBackPressed() }

            logout.setOnClickListener {
                val dialogData = InputDialogData(R.string.logout_data) { viewModel.onLogout(doDeleteUser = false) }

                startDialog(dialogData)
            }

            deleteData.setOnClickListener {
                val dialogData = InputDialogData(R.string.delete_data) { viewModel.onLogout(doDeleteUser = true) }

                startDialog(dialogData)
            }
        }
    }

    private fun startDialog(dialogData: InputDialogData) {
        val dialog = InputDialog.newInstance(dialogData).apply { isCancelable = false }
        dialog.show(supportFragmentManager, "Input Dialog")
    }

    private fun renderState(state: ProfileViewModel.ViewState) = when (state) {
        ProfileViewModel.ViewState.Loading -> showLoading()
        is ProfileViewModel.ViewState.LogoutSuccess -> {
            hideLoading()
            if (!state.doDeleteUser) openLogin() else startDeleteUser()
        }
    }.exhaustive

    private fun startDeleteUser() {
        //TODO remove all films saved in remote database, then
        onDeleteUserData()
    }

    private fun hideLoading() = with(binding) {}

    private fun showLoading() = with(binding) {}

    private fun onDeleteUserData() {
        val user = auth.currentUser!!

        user.delete()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    openLogin()
                }
            }
    }

    private fun openLogin() {
        startActivity(NavigateTo.login(this@ProfileActivity))
        finishAffinity()
    }
}