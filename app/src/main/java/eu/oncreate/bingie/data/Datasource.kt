package eu.oncreate.bingie.data

// class Datasource @Inject constructor(
//    private val local: LocalSource,
//    private val fanartApi: FanartApi,
//    private val tmdbApi: TmdbApi,
//    private val traktApi: TraktApi
// ) {
//
// //    private var configuration: Configuration? = null
// //    private var configuration: Configuration =  GlobalScope.async {
// //        doSomethingUsefulOne()
// //    }
//
// //    fun <T> lazyPromise(block: suspend CoroutineScope.() -> T, scope:CoroutineScope=GlobalScope): Lazy<Deferred<T>> {
// //        return lazy {
// //            scope.async(start = CoroutineStart.LAZY) {
// //                block.invoke(this)
// //            }
// //        }
// //    }
// //
// //    val configuration by lazyPromise({local.getConfiguration()})
//
//    private val configuration: Configuration by lazy {
//        // todo
//        runBlocking {
//            val localConfiguration = local.getConfiguration()
//            val now = Instant.now().epochSecond
//            val interval = 3600 * 24 * 3
//            val configurationTime = localConfiguration.firstOrNull()?.timestamp ?: 0L
//            return@runBlocking if (configurationTime + interval > now) {
//                localConfiguration.first()
//            } else {
//                getLocal(tmdbApi.getConfiguration())
//            }
//        }
//    }
//
// //    private val configurationSingle = local.getConfiguration()
// //        .flatMap { localConfiguration ->
// //            val now = Instant.now().epochSecond
// //            val interval = 3600 * 24 * 3
// //            val configurationTime = localConfiguration.firstOrNull()?.timestamp ?: 0L
// //            if (configurationTime + interval > now) {
// //                Single.just(localConfiguration.first())
// //            } else {
// //                tmdbApi.getConfiguration().map { getLocal(it) }
// //            }
// //        }.doOnSuccess {
// //            local.insertConfiguration(it)
// //            configuration = it
// //        }
//
//    suspend fun search(query: String): List<SearchResultItem> {
//        return try {
//            traktApi
//                .search(query)
//                .map { remoteList ->
//                    SearchResultItem.toLocal(remoteList)
//                }
//        } catch (e: Exception) {
//            local.searchItems(query)
//        }
//    }
//
//    suspend fun getImages(item: SearchResultItem, forceUpdate: Boolean = false): ShowWithImages {
//
//        // todo concurrency
//        val tmdbImages = getTmdbImages(item.show.ids.tmdb, forceUpdate)
//        val fanartImages = getFanartPicture(item.show.ids.tvdb, forceUpdate)
//
//        return ShowWithImages(
//            item,
//            tmdbImages.takeUnless { it == TmdbImages.EMPTY },
//            configuration.images,
//            fanartImages.takeUnless { it == FanartImages.EMPTY })
//    }
//
//    private suspend fun getFanartPicture(tvdbId: Int?, forceUpdate: Boolean = false): FanartImages {
//
//        val fanartFallback = FanartImages.EMPTY
//
//        return when (tvdbId) {
//            null -> fanartFallback
//            else -> {
//                val localFanart = local.getFanart(tvdbId)
//                if (localFanart.isNotEmpty() && !forceUpdate) {
//                    localFanart.first()
//                } else {
//                    try {
//                        val images = fanartApi.getImages(tvdbId)
//                        local.insertFanart(images)
//                        local.getFanart(tvdbId).firstOrNull() ?: fanartFallback
//                    } catch (e: Exception) {
//                        fanartFallback
//                    }
//                }
//            }
//        }
//    }
//
//    private suspend fun getTmdbImages(tmdbId: Int?, forceUpdate: Boolean = false): TmdbImages {
//
//        val tmdbFallback = TmdbImages.EMPTY
//
//        return when (tmdbId) {
//            null -> tmdbFallback
//            else -> {
//                val localImages = local.getTmdbImages(tmdbId)
//                if (localImages.isNotEmpty() && !forceUpdate) {
//                    localImages.first()
//                } else {
//                    try {
//                        val images = tmdbApi.getImages(tmdbId)
//                        local.insertTmdbImages(images)
//                        local.getTmdbImages(tmdbId).firstOrNull() ?: tmdbFallback
//                    } catch (e: Exception) {
//                        tmdbFallback
//                    }
//                }
//            }
//        }
//    }
//
//    suspend fun getSeasons(traktId: Int, forceUpdate: Boolean = false): List<SeasonsItem> {
//        val localSeasons = local.getSeasons(traktId)
//        return if (localSeasons.isNotEmpty() && !forceUpdate) {
//            localSeasons
//        } else {
//            val seasons = traktApi.showSeasons(traktId)
//            local.insertSeasons(seasons, traktId)
//            local.getSeasons(traktId)
//        }
//    }
//
//    suspend fun getShow(traktId: Int, forceUpdate: Boolean = false): SearchResultItem {
//        val localShow = local.getShow(traktId)
//        return if (localShow.isNotEmpty() && !forceUpdate) {
//            localShow.first()
//        } else {
//            val show = traktApi.searchLookUp(traktId)
//            local.insertShow(show)
//            local.getShow(traktId).first()
//        }
//    }
// }
