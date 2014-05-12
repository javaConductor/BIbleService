package org.swordexplorer.bible
/**
 * Created by lcollins on 5/11/2014.
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

    @Override
    VerseRange verseSpecToVerses(String verseSpec) {

        /// break the string into parts starting with Bookname
        ///  Gen 5:2-7, 8-12

        def parts = verseSpec.split(' ',2)
        def bkName =  parts[0]

        /// get the book
        def book = bookNameToBook(bkName)
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
            verses << verse
        }
        new VerseRange(verseSpec, verses)
    }

    def bookNameToBook(bkName){
        def ret
        // find the exact match

        ret = bookList.find { bk ->
            bk.title == bkName
        }

        if (!ret) {

            def list = bookList.findAll { bk ->
                bk.title.startsWith(bkName)
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

        return null
    }

    @Override
    List<Book> getBooks() {
        bookList
    }

    @Override
    Book getBook(int bookId) {
        bookList[bookId-1]
    }

}
