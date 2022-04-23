package com.example.moviesapp.feature.signup

import android.app.Dialog
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentSignUpBinding
import com.example.moviesapp.utils.setupUI
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.*

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var textInputLayoutEmail: TextInputLayout
    private lateinit var editTextEmail: TextInputEditText
    private lateinit var textInputLayoutPassword: TextInputLayout
    private lateinit var editTextPassword: TextInputEditText
    private lateinit var textInputLayoutRepeatPassword: TextInputLayout
    private lateinit var editTextRepeatPassword: TextInputEditText
    private lateinit var progressBar: ProgressBar
    private val signUpViewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI(binding.parent)
        textInputLayoutEmail = binding.textInputLayoutEmail
        textInputLayoutPassword = binding.textInputLayoutPassword
        textInputLayoutRepeatPassword = binding.textInputLayoutRepeatPassword

        editTextEmail = binding.editTextEmail
        editTextPassword = binding.editTextPassword
        editTextRepeatPassword = binding.editTextRepeatPassword

        progressBar = binding.progressBar

        binding.txtLogin.setOnClickListener {
            val action = SignUpFragmentDirections.actionSignUpFragmentToLoginFragment(null, null)
            findNavController().navigate(action)
        }

        binding.btnSignUp.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            val checkEmail = isValidEmail()
            val checkPassword = isValidPassword()
            val checkRepeatPassword = isValidRepeatPassword()
            val checkAcceptTermAndCondition = isCheckedTermAndCondition()

            progressBar.visibility = View.VISIBLE

            if (checkEmail && checkPassword && checkRepeatPassword && checkAcceptTermAndCondition) {
                lifecycleScope.launch(Dispatchers.IO) {
                    val authResponse = signUpViewModel.createAccount(email, password)
                    if (!authResponse.error) {
                        withContext(Dispatchers.Main) {
                            progressBar.visibility = View.INVISIBLE
                            showDialog(
                                "Sign Up Successful",
                                "You signed up successful, Let's login and experience our app."
                            ) {
                                val action =
                                    SignUpFragmentDirections.actionSignUpFragmentToLoginFragment(
                                        email,
                                        password
                                    )
                                findNavController().navigate(action)
                            }
                            resetUi()
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            progressBar.visibility = View.INVISIBLE
                            showDialog(
                                "Sign Up Failed",
                                authResponse.message
                            ) {}
                        }
                    }
                }
            }
        }
    }

    private fun isValidEmail(): Boolean {
        if (editTextEmail.text?.length == 0) {
            textInputLayoutEmail.isErrorEnabled = true
            textInputLayoutEmail.error = "Email Address isn't empty."
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(editTextEmail.text.toString()).matches()) {
            textInputLayoutEmail.isErrorEnabled = true
            textInputLayoutEmail.error = "Email Address InCorrect."
            return false
        }
        textInputLayoutEmail.isErrorEnabled = false
        return true
    }

    private fun isValidPassword(): Boolean {
        if (editTextPassword.text?.length ?: 0 < 8) {
            textInputLayoutPassword.isErrorEnabled = true
            textInputLayoutPassword.error = "Password is at least 8 character."
            return false
        }
        textInputLayoutPassword.isErrorEnabled = false
        return true
    }

    private fun isValidRepeatPassword(): Boolean {
        if (editTextPassword.text.toString() != editTextRepeatPassword.text.toString()) {
            textInputLayoutRepeatPassword.isErrorEnabled = true
            textInputLayoutRepeatPassword.error =
                "Repeat password have to be the same to password."
            return false
        } else if (editTextRepeatPassword.text.isNullOrEmpty()) {
            textInputLayoutRepeatPassword.isErrorEnabled = true
            textInputLayoutRepeatPassword.error =
                "Repeat password have to not empty."
            return false
        }
        textInputLayoutRepeatPassword.isErrorEnabled = false
        return true
    }

    private fun isCheckedTermAndCondition(): Boolean {
        val checkBox = binding.checkboxTermAndCondition
        if (!checkBox.isChecked) {
            binding.txtErrorTermAndCondition.visibility = View.VISIBLE
            return false
        }
        binding.txtErrorTermAndCondition.visibility = View.INVISIBLE
        return true
    }

    private fun showDialog(title: String, content: String, function: () -> Unit) {
        val dialog = Dialog(requireContext(), R.style.DialogStyle)
        dialog.setContentView(R.layout.fragment_dialog_notice)
        val txtTitle = dialog.findViewById<TextView>(R.id.txt_title)
        val txtContent = dialog.findViewById<TextView>(R.id.txt_content)
        val btnConfirm = dialog.findViewById<Button>(R.id.btn_confirm)
        txtTitle.text = title
        txtContent.text = content
        dialog.window?.setBackgroundDrawableResource(R.drawable.bg_white_color)
        dialog.show()

        btnConfirm.setOnClickListener {
            function()
            dialog.dismiss()
        }
    }

    private fun resetUi() {
        editTextEmail.setText("")
        editTextPassword.setText("")
        editTextRepeatPassword.setText("")
        binding.checkboxTermAndCondition.isChecked = false
    }
}