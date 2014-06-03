package org.swordexplorer.kjv

import org.swordexplorer.bible.AbstractService

/**
 * Created by lcollins on 5/11/2014.
 */
class KjvService extends  AbstractService {
    def bibleData
    KjvService(){
        super("/kjv.json")
    }
}
