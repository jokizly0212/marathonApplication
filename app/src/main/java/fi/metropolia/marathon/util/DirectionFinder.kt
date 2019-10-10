package fi.metropolia.marathon.util

import android.os.AsyncTask
import com.google.android.gms.maps.model.LatLng
import fi.metropolia.marathon.model.Distance
import fi.metropolia.marathon.model.Duration
import fi.metropolia.marathon.model.Route
import fi.metropolia.marathon.view.DirectionFinderListener
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.UnsupportedEncodingException
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder

class DirectionFinder(internal val listener: DirectionFinderListener, private val origin: String, private val destination: String) {
    private val DIRECTION_URL_API = "https://maps.googleapis.com/maps/api/directions/json?"
    private val GOOGLE_API_KEY = "AIzaSyA3dPLT5lFjTHoK26gjor6DquLwKFyJlnI"

    @Throws(UnsupportedEncodingException::class)
    fun execute() {
        listener.onDirectionFinderStart()
        DownloadRawData().execute(createUrl())
    }

    @Throws(UnsupportedEncodingException::class)
    private fun createUrl(): String {
        val urlOrigin = URLEncoder.encode(origin, "utf-8")
        val urlDestination = URLEncoder.encode(destination, "utf-8")

        return DIRECTION_URL_API + "origin=" + urlOrigin + "&destination=" + urlDestination + "&key=" + GOOGLE_API_KEY
    }

    inner class DownloadRawData: AsyncTask<String, Unit, String>() {
        override fun doInBackground(vararg params: String?): String {
            val link = params[0]
            try {
                val url = URL(link)
                val inputStream = url.openConnection().getInputStream()
                val buffer = StringBuffer()
                val reader = BufferedReader(InputStreamReader(inputStream))

                var line: String?
                do {
                    line = reader.readLine()
                    buffer.append(line + "\n")
                } while (line != null)
                return buffer.toString()
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return ""
        }

        override fun onPostExecute(res: String) {
            try {
                parseJSon(res)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

        @Throws(JSONException::class)
        private fun parseJSon(data: String?) {
            if (data == null)
                return

            val routes = ArrayList<Route>()
            val jsonData = JSONObject(data)
            val jsonRoutes = jsonData.getJSONArray("routes")
            for (i in 0 until jsonRoutes.length()) {
                val jsonRoute = jsonRoutes.getJSONObject(i)
                val route = Route()

                val overviewPolylineJson = jsonRoute.getJSONObject("overview_polyline")
                val jsonLegs = jsonRoute.getJSONArray("legs")
                val jsonLeg = jsonLegs.getJSONObject(0)
                val jsonDistance = jsonLeg.getJSONObject("distance")
                val jsonDuration = jsonLeg.getJSONObject("duration")
                val jsonEndLocation = jsonLeg.getJSONObject("end_location")
                val jsonStartLocation = jsonLeg.getJSONObject("start_location")



                route.distance = Distance(jsonDistance.getString("text"), jsonDistance.getInt("value"))
                route.duration = Duration(jsonDuration.getString("text"), jsonDuration.getInt("value"))
                route.endAddress = jsonLeg.getString("end_address")
                route.startAddress = jsonLeg.getString("start_address")
                route.startLocation =
                    LatLng(jsonStartLocation.getDouble("lat"), jsonStartLocation.getDouble("lng"))
                route.endLocation =
                    LatLng(jsonEndLocation.getDouble("lat"), jsonEndLocation.getDouble("lng"))
                route.points = decodePolyLine(overviewPolylineJson.getString("points"))

                routes.add(route)
            }
            listener.onDirectionFinderSuccess(routes)
        }

        private fun decodePolyLine(poly: String): List<LatLng> {
            val len = poly.length
            var index = 0
            val decoded = ArrayList<LatLng>()
            var lat = 0
            var lng = 0

            while (index < len) {
                var b: Int
                var shift = 0
                var result = 0
                do {
                    b = poly[index++].toInt() - 63
                    result = result or (b and 0x1f shl shift)
                    shift += 5
                } while (b >= 0x20)
                val dLat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
                lat += dLat

                shift = 0
                result = 0
                do {
                    b = poly[index++].toInt() - 63
                    result = result or (b and 0x1f shl shift)
                    shift += 5
                } while (b >= 0x20)
                val dLng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
                lng += dLng

                decoded.add(
                    LatLng(
                        lat / 100000.0, lng / 100000.0
                    )
                )
            }
            return decoded
        }
    }

}