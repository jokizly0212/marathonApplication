package fi.metropolia.marathon.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import fi.metropolia.marathon.R
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment: Fragment() {
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
            val action =
                RegisterFragmentDirections.actionRegisterFragmentToRequestsFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }
}