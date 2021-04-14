package com.chani.mylibrarykt.data

//class HistoryRepository @Inject constructor(
//    private val historyDao: HistoryDao
//) {
//    fun getHistories(): Flow<PagingData<List<History>>> {
//        return Pager(
//            config = PagingConfig(pageSize = 20),
//            pagingSourceFactory = { historyDao.getLastHistory() }
//        ).flow
//    }
//}