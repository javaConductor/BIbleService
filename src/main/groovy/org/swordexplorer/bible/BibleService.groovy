package org.swordexplorer.bible

/**
 *
 */
interface BibleService {
    def chapterVerseCount(bkId, chapter)

    def parseVerseSpec(String verseSpec)

    VerseRange verseSpecToVerses(String verseSpec)

    def bookNameToBook(bkName)

    Verse getVerse(String verseId)
    List<Verse> getVerses(List<String> verseIds)

    VerseRange getChapter(int book, int chapter)

    List getVersesWithPhrase(words)
    List getVersesWithAnyWords(words)
    List getVersesWithAllWords(words)

    List<Book> getBooks()
    Book getBook(int bookId)
    String verseSpecFromVerseId(vid)
}
