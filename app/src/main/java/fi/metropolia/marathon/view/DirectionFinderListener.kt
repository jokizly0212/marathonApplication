package fi.metropolia.marathon.view

import fi.metropolia.marathon.model.Route


interface DirectionFinderListener {
    fun onDirectionFinderStart()
    fun onDirectionFinderSuccess(routeList: List<Route>)
}