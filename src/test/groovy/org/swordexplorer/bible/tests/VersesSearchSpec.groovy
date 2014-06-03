package org.swordexplorer.bible.tests

import org.swordexplorer.kjv.KjvService
import spock.lang.Specification


/**
 * Created by lcollins on 5/11/2014.
 */
class VersesSearchSpec extends Specification {

    def bookService = new KjvService()

    void setup() {
    }

    def "service should find 'holy'"() {
        when:
        def verses = bookService.getVerseWithAllWords(["holy"])

        then:
        verses.size() > 0
        println "Count=${verses.size()}"
    }

    def "service should find 'Jeshurun'"() {
        when:
        def verses = bookService.getVerseWithAllWords(["Jeshurun"])

        then:
        verses.size() > 0
        verses.each { v -> println "Verse: ${v.verseId}:${v.verseText}" }
        println "Count=${verses.size()}"
    }

    def "service should find 'Jeshurun and salvation'"() {
        when:
        def verses = bookService.getVerseWithAllWords(["Jeshurun", "Salvation"])
        then:
        verses.size() == 1
        verses[0].verseId == '05032015'

    }

    def "service should find 'Jeshurun or salvation'"() {
        when:
        def verses = bookService.getVerseWithAnyWords(["Jeshurun", "Salvation"])
        then:
        verses.size() == 160
    }

}