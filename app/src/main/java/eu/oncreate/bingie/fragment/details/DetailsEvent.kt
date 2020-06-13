package eu.oncreate.bingie.fragment.details

sealed class DetailsEvent {
    data class StartSeasonChanged(val value: Int) : DetailsEvent()
    data class EndSeasonChanged(val value: Int) : DetailsEvent()
    data class StartEpisodeChanged(val value: Int) : DetailsEvent()
    data class EndEpisodeChanged(val value: Int) : DetailsEvent()
}
