package fi.metropolia.marathon.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import fi.metropolia.marathon.R
import fi.metropolia.marathon.databinding.FragmentRequestDetailBinding
import fi.metropolia.marathon.util.DataTemp
import fi.metropolia.marathon.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_request_detail.*

class DetailFragment : Fragment() {

    private lateinit var viewModel: DetailViewModel
    private var requestId = 0

    private lateinit var dataBinding: FragmentRequestDetailBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_request_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            requestId = DetailFragmentArgs.fromBundle(it).requestId
        }
        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModel.fetch(requestId)
        observeViewModel()
        acceptButton.setOnClickListener {
            val action = DetailFragmentDirections.actionRequestDetailToGoogleMapFragment()
            action.detination = dataBinding.request!!.endPoint
            Navigation.findNavController(it).navigate(action)
        }
    }

   private fun observeViewModel() {
        viewModel.requestDetail.observe(this, Observer {
            it?.let {
                dataBinding.request = it
                nameText.text= it.name
                startDateText.text = it.startDate
                descriptionText.text = it.description
                amountText.text = it.amount.toString()
                startPointText.text = it.startPoint
                endPointText.text = it.endPoint

                DataTemp.eventName = it.name
                DataTemp.startDate = it.startDate
                DataTemp.description = it.description
                DataTemp.startPoint = it.startPoint
                DataTemp.endPoint = it.endPoint

            }
        })
    }
}