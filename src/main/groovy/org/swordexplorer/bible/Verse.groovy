package org.swordexplorer.bible

/**
 * Created by lcollins on 5/10/2014.
 */
class Verse {
    int verse
    int book
    int chapter
    String verseText
    String verseId //BBCCCVVV
    String toString(){
        "$verseId $verseText"
    }
}
