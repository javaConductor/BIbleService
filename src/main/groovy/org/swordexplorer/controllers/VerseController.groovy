package org.swordexplorer.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

/**
 * Created by lee on 6/11/17.
 */
import org.swordexplorer.bible.BibleService
import org.swordexplorer.bible.Verse

@RestController
@RequestMapping("/verses")
class VerseController {
    BibleService bibleService

    @Autowired
    VerseController(BibleService bibleService) {
        this.bibleService = bibleService
    }

    @RequestMapping("/{id}")
    Verse index(@PathVariable("id") String id) {
        bibleService.getVerse(id)
    }

    @RequestMapping(path = "/fromSpec/{verseSpec}", method = RequestMethod.GET)
    List<Verse> fromVerseSpec(@PathVariable("verseSpec") String verseSpec) {
        println("/verses/$verseSpec")
        bibleService.getVerses(verseSpec)
    }
}
