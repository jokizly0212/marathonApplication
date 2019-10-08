package fi.metropolia.marathon.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import fi.metropolia.marathon.R
import fi.metropolia.marathon.model.User
import fi.metropolia.marathon.viewmodel.RegisterViewModel
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment: Fragment() {


    private lateinit var viewModel: RegisterViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerButton.setOnClickListener {

            viewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)
            val user = User(userNameText.text.toString(), ageText.text.toString().toInt(), weightText.text.toString().toDouble(), heightText.text.toString().toDouble(), addressText.text.toString())
            viewModel.registerUser(user)
            val action =
                RegisterFragmentDirections.actionRegisterFragmentToRequestsFragment2()
            Navigation.findNavController(it).navigate(action)
        }
    }


}