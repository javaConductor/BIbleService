package org.swordexplorer.bible

/**
 *
 */
interface BibleService {
    VerseRange verseSpecToVerses(String verseSpec)
    Verse getVerse(String verseId)
    List<Verse> getVerses(List<String> verseIds)
    List<Book> getBooks()
    Book getBook(int bookId)
}
