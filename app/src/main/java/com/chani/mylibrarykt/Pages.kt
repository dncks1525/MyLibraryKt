package com.chani.mylibrarykt

import com.chani.mylibrarykt.data.PageItem

class Pages {
    class NewPage() : PageItem(PageType.NEW)
    class SearchPage() : PageItem(PageType.SEARCH)
    class BookmarkPage() : PageItem(PageType.BOOKMARK)
    class HistoryPage() : PageItem(PageType.HISTORY)
}