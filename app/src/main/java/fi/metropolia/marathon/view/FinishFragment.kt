package fi.metropolia.marathon.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import fi.metropolia.marathon.R
import fi.metropolia.marathon.util.DataTemp
import kotlinx.android.synthetic.main.fragment_finish.*

class FinishFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_finish, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nameTextValue.text = DataTemp.userName
        ageTextValue.text= DataTemp.age.toString()
        weightTextValue.text = DataTemp.weight.toString()
        heightTextValue.text = DataTemp.height.toString()
        addressTextValue.text = DataTemp.address

        eventNameValue.text = DataTemp.eventName
        startDateValue.text = DataTemp.startDate
        descriptionTextValue.text = DataTemp.description
        startPointValue.text = DataTemp.startPoint
        endPointValue.text = DataTemp.endPoint
        distanceTextValue.text = DataTemp.distance
        textStepCountValue.text = DataTemp.stepCount.toString()

        back_to_home.setOnClickListener {
            val action = FinishFragmentDirections.actionFinishFragmentToRequestsFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }


}