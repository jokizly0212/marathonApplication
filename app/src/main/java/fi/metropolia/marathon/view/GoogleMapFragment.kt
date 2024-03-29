package fi.metropolia.marathon.view

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Geocoder
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import fi.metropolia.marathon.R
import fi.metropolia.marathon.model.Route
import fi.metropolia.marathon.util.DirectionFinder
import kotlinx.android.synthetic.main.fragment_map.*
import java.io.UnsupportedEncodingException
import androidx.navigation.Navigation
import fi.metropolia.marathon.util.MarathonData

//import sun.jvm.hotspot.utilities.IntArray



/*show Google Map, direction, internal sensor: TYPE_STEP_COUNTER*/

class GoogleMapFragment : Fragment(), OnMapReadyCallback, NavigationListener ,
    SensorEventListener {


    private lateinit var mMap: GoogleMap
    private var currentLocation = arrayListOf<Marker>()
    private var destinationMarkers = arrayListOf<Marker>()
    private var polylinePaths = arrayListOf<Polyline>()
    private var destination = ""
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var geoCoder: Geocoder

    private var running = false
    private lateinit var sm: SensorManager



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        sm = activity!!.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        geoCoder = Geocoder(context!!)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                val address = geoCoder.getFromLocation(locationResult.lastLocation.latitude, locationResult.lastLocation.longitude, 1)[0]
                sendRequest(address.getAddressLine(0))
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            destination = GoogleMapFragmentArgs.fromBundle(it).detination
        }
        startLocationUpdates()

        finishButton.setOnClickListener {
            val action = GoogleMapFragmentDirections.actionGoogleMapFragmentToFinishFragment()
            Navigation.findNavController(it).navigate(action)
        }

    }


    private fun sendRequest(origin: String) {
        try {
            DirectionFinder(this, origin, destination).execute()
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
    }

    private fun startLocationUpdates() {
        val locationRequest = LocationRequest.create()
        fusedLocationClient.requestLocationUpdates(locationRequest,
            locationCallback,
            Looper.getMainLooper())
    }

    override fun onDirectionFinderStart() {
        if (currentLocation.isNotEmpty()) {
            for (marker in currentLocation) {
                marker.remove()
            }
        }

        if (destinationMarkers.isNotEmpty()) {
            for (marker in destinationMarkers) {
                marker.remove()
            }
        }

        if (polylinePaths.isNotEmpty()) {
            for (polyline in polylinePaths) {
                polyline.remove()
            }
        }
    }

    override fun onDirectionFinderSuccess(routeList: List<Route>) {
        polylinePaths = ArrayList()
        currentLocation = ArrayList()
        destinationMarkers = ArrayList()

        for (route in routeList) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16f))



            distanceText.text = route.distance?.text



            MarathonData.distance = distanceText.text.toString()


            currentLocation.add(
                mMap.addMarker(
                    MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
                        .title(route.startAddress)
                        .position(route.startLocation!!)
                )
            )
            destinationMarkers.add(
                mMap.addMarker(
                    MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
                        .title(route.endAddress)
                        .position(route.endLocation!!)
                )
            )

            val polylineOptions = PolylineOptions().geodesic(true).color(Color.BLUE).width(10f)

            for (i in route.points!!.indices)
                polylineOptions.add(route.points!![i])

            polylinePaths.add(mMap.addPolyline(polylineOptions))
        }
    }

    override fun onMapReady(p0: GoogleMap?) {
        mMap = p0!!
        mMap.isMyLocationEnabled = true
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onSensorChanged(p0: SensorEvent) {
        if(running) {
            current_step_value.text = p0.values[0].toString()
            MarathonData.stepCount = p0.values[0]
        }
    }

    override fun onPause() {
        super.onPause()
        running = false
        sm.unregisterListener(this)

    }

    override fun onResume() {
        super.onResume()
        running = true
        var stepsSensor = sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (stepsSensor != null) {
            sm.registerListener(this, stepsSensor, SensorManager.SENSOR_DELAY_UI)

        }
    }
}
