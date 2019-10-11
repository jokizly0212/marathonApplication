package fi.metropolia.marathon.view

import fi.metropolia.marathon.model.Route

/*interface direction to start and finish direction*/
interface NavigationListener {
    fun onDirectionFinderStart()
    fun onDirectionFinderSuccess(routeList: List<Route>)
}