package org.swordexplorer.kjv

import org.swordexplorer.bible.AbstractService
import org.swordexplorer.bible.BibleData
import org.swordexplorer.bible.BibleService
import org.swordexplorer.bible.Book
import org.swordexplorer.bible.Verse
import org.swordexplorer.bible.VerseRange

/**
 * Created by lcollins on 5/11/2014.
 */
class KjvService extends  AbstractService {

    def bibleData
    KjvService(){
        super("/kjv.json")
    }



}
