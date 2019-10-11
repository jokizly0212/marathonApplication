package fi.metropolia.marathon.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import fi.metropolia.marathon.R
import fi.metropolia.marathon.util.MarathonData
import kotlinx.android.synthetic.main.fragment_stats.*

class StatsFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nameTextValue.text = MarathonData.userName
        ageTextValue.text= MarathonData.age.toString()
        weightTextValue.text = MarathonData.weight.toString()
        heightTextValue.text = MarathonData.height.toString()
        addressTextValue.text = MarathonData.address

        eventNameValue.text = MarathonData.eventName
        startDateValue.text = MarathonData.startDate
        descriptionTextValue.text = MarathonData.description
        startPointValue.text = MarathonData.startPoint
        endPointValue.text = MarathonData.endPoint
        distanceTextValue.text = MarathonData.distance
        textStepCountValue.text = MarathonData.stepCount.toString()

        back_to_home.setOnClickListener {
            val action = StatsFragmentDirections.actionFinishFragmentToRequestsFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }


}