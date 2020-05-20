package com.chani.mylibrarykt

class Pages {
    class NewPage() : PageItem(PageType.NEW)
    class SearchPage() : PageItem(PageType.SEARCH)
    class BookmarkPage() : PageItem(PageType.BOOKMARK)
    class HistoryPage() : PageItem(PageType.HISTORY)
}