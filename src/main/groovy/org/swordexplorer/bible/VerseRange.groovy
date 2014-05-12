package org.swordexplorer.bible

/**
 *
 */
class VerseRange {
    def verseSpec
    List<Verse>  verses

    VerseRange(String verseSpec, List<Verse> verses){
        this.verseSpec = verseSpec
        this.verses =verses
    }

}
