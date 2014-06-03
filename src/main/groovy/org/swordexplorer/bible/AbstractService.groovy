package org.swordexplorer.bible
/**
 *
 */
abstract class AbstractService implements BibleService {

    def bibleData, bookList, chapters, verses

    AbstractService(jsonBibleTextFilename){
        init(jsonBibleTextFilename)
    }
    def init(jsonBibleTextFilename){
        bibleData = new BibleData(jsonBibleTextFilename).data()
        bookList = bibleData.bible.books
        chapters = bibleData.bible.chapters
        verses = bibleData.bible.verses
    }

    def chapterVerseCount(bkId, chapter) {
        def chpt = chapters.find { chptInfo ->
            chptInfo.book == bkId &&
                    chptInfo.chapter == chapter
        }
        chpt ? chpt.verses : 0
    }

    @Override
    VerseRange verseSpecToVerses(String verseSpec) {

        /// break the string into parts starting with Bookname
        ///  Gen 5:2-6, 8-12

        def parts = verseSpec.split(' ',2)
        def bkName =  parts[0]

        /// get the book
        def book = bookNameToBook(bkName)

        if (!book)
            throw new IllegalArgumentException("Bad book name: $bkName")

        def  theRest = parts[1]
        def chptAndVerses = theRest.split(':')
        // get the chapter
        def chapter = Integer.parseInt(chptAndVerses[0])

        /// get the verses
        def sets = chptAndVerses[1].split(',')
        def verseList = []

        sets.each { vlist ->
            def start, end
            if (vlist.contains('-')){
                def l = vlist.split('-')
                start = l[0] as int
                end = l[1] as int

            }else{
                end = start = (vlist as int)
            }
            (start..end).each { v ->
                verseList << v
            }
        }

        List<Verse> verses=[]
        verseList.each { v ->
            String vid  = String.format("%02d%03d%03d", book.id,chapter,v)
            def verse = getVerse(vid)
            if (verse)
                verses << verse
        }
        new VerseRange(verseSpec, verses)
    }

    def bookNameToBook(bkName){
        def ret
        /// compare the upper
        bkName = bkName.toUpperCase()
        // find the exact match
        ret = bookList.find { bk ->
            bk.title.toUpperCase() == bkName
        }

        if (!ret) {
            def list = bookList.findAll { bk ->
                bk.title.toUpperCase().startsWith(bkName)
            }
            if (list.size() ==1)
                ret = list[0]
        }
        ret
    }

    @Override
    Verse getVerse(String verseId) {
        verses[verseId]
    }

    @Override
    List<Verse> getVerses(List<String> verseIds) {
        verseIds.collect { vid ->
            getVerse(vid)
        }
    }

    @Override
    List<Book> getBooks() {
        bookList
    }

    @Override
    Book getBook(int bookId) {
        bookList[bookId-1]
    }

    private def verseToSearchResult = { verse ->
        [
                verseId  : verse.verseId,
                verseSpec: verseSpecFromVerseId(verse.verseId),
                verseText: verse.verseText
        ]
    }

    @Override
    String verseSpecFromVerseId(vid) {
        def bkId = vid.substring(0, 2) as int
        if (bkId > 66 || bkId < 1)
            return null;
        def chapter = vid.toString().substring(2, 5) as int
        def verse = vid.substring(5) as int
        def bkName = getBook(bkId)?.title
        "$bkName $chapter:$verse"
    }

    @Override
    List getVersesWithPhrase(phrase) {
        verses.values().findAll { v ->
            v.verseText.toUpperCase().contains(phrase.toUpperCase())
        }.collect(verseToSearchResult)
    }//phrase

    @Override
    List getVersesWithAllWords(words) {
        def wlist = words.split(' ')
        verses.values().findAll { v ->
            wlist.every { w ->
                v.verseText.toUpperCase().contains(w.toUpperCase())
            }
        }.collect(verseToSearchResult)
    }//all

    @Override
    List getVersesWithAnyWords(words) {
        def wlist = words.split(' ')
        verses.values().findAll { v ->
            wlist.any { w ->
                v.verseText.toUpperCase().contains(w.toUpperCase())
            }
        }.collect(verseToSearchResult)
    }//any

}
