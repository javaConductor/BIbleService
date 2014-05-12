package org.swordexplorer.bible

import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
/**
 * Created by lcollins on 5/11/2014.
 */
@Component
class BibleData {
    def bibleData
    BibleData(jsonFilename){
        load(jsonFilename)
    }
    def load(jsonFilename){
        def stream = getClass().getResourceAsStream(jsonFilename)
        bibleData = new JsonSlurper().parseText( stream.text)
    }

    def data() {
        bibleData
    }

}
