package com.example.moviesapp.feature.login.view

import android.app.Dialog
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentLoginBinding
import com.example.moviesapp.feature.login.viewmodel.LoginViewModel
import com.example.moviesapp.utils.setupUI
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


private const val TAG = "LOGIN"

class LoginFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentLoginBinding

    // TextField EmailAddress
    private lateinit var textInputLayoutEmail: TextInputLayout
    private lateinit var editTextEmail: TextInputEditText

    // TextField Password
    private lateinit var textInputLayoutPassword: TextInputLayout
    private lateinit var editTextPassword: TextInputEditText
    private lateinit var progressBar: ProgressBar
    private val loginViewModel: LoginViewModel by viewModels()
    private val args by navArgs<LoginFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater)
        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI(binding.parent)
        textInputLayoutEmail = binding.textInputLayoutEmail
        editTextEmail = binding.editTextEmail
        editTextEmail.setText(args.emailAddress)
        textInputLayoutPassword = binding.textInputLayoutPassword
        editTextPassword = binding.editTextPassword
        editTextPassword.setText(args.password)
        progressBar = binding.progressBar

        binding.txtRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        binding.btnLogin.setOnClickListener {
            signIn()
            hideSoftKeyboard()
//            hideSoftKeyboard(requireActivity())
//            closeKeyBoard()
        }
    }

    //    private fun hideSoftKeyboard(view: View) {
//        val manager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        manager.hideSoftInputFromWindow(view.windowToken, 0)
//    }
//    private fun closeKeyBoard(){
//        val view: View? = activity?.currentFocus
//        if(view!=null){
//            val inputMethodManager: InputMethodManager =
//                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//            inputMethodManager.hideSoftInputFromWindow(view.windowToken,0)
//        }
//    }
//    fun hideSoftKeyboard(activity: Activity) {
//        val inputMethodManager = activity.getSystemService(
//            Activity.INPUT_METHOD_SERVICE
//        ) as InputMethodManager
//        if (inputMethodManager.isAcceptingText) {
//            inputMethodManager.hideSoftInputFromWindow(
//                activity.currentFocus!!.windowToken,
//                0
//            )
//        }
//    }

    /*
          ======================================================================
        ||   Trong LoginViewModel có cần 2 function này không? Chắc không cần   ||
          ======================================================================
    */
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

    private fun showDialog(title: String, context: String) {
        val dialog = Dialog(requireContext(), R.style.DialogStyle)
        dialog.setContentView(R.layout.fragment_dialog_notice)
        val txtTitle = dialog.findViewById<TextView>(R.id.txt_title)
        val txtContent = dialog.findViewById<TextView>(R.id.txt_content)
        val btnConfirm = dialog.findViewById<Button>(R.id.btn_confirm)
        txtTitle.text = title
        txtContent.text = context
        dialog.window?.setBackgroundDrawableResource(R.drawable.bg_white_color);
        dialog.show()

        btnConfirm.setOnClickListener {
            dialog.dismiss()
        }
    }

    /* =====================================================================================
     || Câu hỏi: Làm thể nào để xử lý auto Visible/Invisible ProgressBar                    ||
     || Câu hỏi này nhằm mục định cho progressBar update tự động                            ||
     || Còn việc phân chia xử lý code đã phân chia rõ ràng nhờ việc phân chia các class     ||
     ||                           Model <- ViewModel -> View                                ||
     ||                                                                                     ||
     || Ý tưởng nảy ra --> Sử dụng data binding.                                            ||
       ===================================================================================== */
    /***
    #Structure:
    LoginViewModel:
    - private var _isVisibleProgressBar = MutableLiveData<Boolean>(false)
    - val isVisibleProgressBar: LiveData<Boolean> = _isVisibleProgressBar
    - func signIn(){}
    #Handle:
    - Khi sign thì cho _isVisibleProgressBar = true
    fun changeStateProgressBar(progressBar: ProgressBar, state: Boolean?)
     ***/
//    private fun signIn() {
//        val email = editTextEmail.text.toString()
//        val password = editTextPassword.text.toString()
//        progressBar.visibility = View.VISIBLE
//        if (isValidEmail() && isValidPassword()) {
//            loginViewModel.signIn(
//                email,
//                password,
//                { this.findNavController().navigate(R.id.action_loginFragment_to_homeFragment) },
//                {
//                    progressBar.visibility = View.INVISIBLE
//                    showDialog()
//                }
//            )
//        }
//    }
    private fun signIn() {
        val email = editTextEmail.text.toString()
        val password = editTextPassword.text.toString()
        progressBar.visibility = View.VISIBLE
        if (isValidEmail() && isValidPassword()) {
            lifecycleScope.launch(Dispatchers.IO) {
                val authResponse = withContext(Dispatchers.IO) {
                    loginViewModel.signIn(email, password)
                }
                if (!authResponse.error) {
                    withContext(Dispatchers.Main) {
                        this@LoginFragment.findNavController()
                            .navigate(R.id.action_loginFragment_to_homeFragment)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        progressBar.visibility = View.INVISIBLE
                        showDialog("Login Failed", authResponse.message)
                    }
                }
            }
        }
    }
}
fun Fragment.hideSoftKeyboard() {
    activity?.currentFocus?.let {
        val inputMethodManager = ContextCompat.getSystemService(requireContext(), InputMethodManager::class.java)!!
        inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
    }
}