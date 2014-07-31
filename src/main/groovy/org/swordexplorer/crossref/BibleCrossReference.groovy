package org.swordexplorer.crossref

import org.swordexplorer.bible.BibleService

/**
 * Created by lcollins on 7/31/2014.
 */
class BibleCrossReference {

    def BibleCrossReference(BibleService service, List initialXref) {
        if (initialXref) {
            xref = initialXref
        }

    }
    def xref = []
    def relationshipDefs = []
    def sample = [
            [subjectVerses   : [verseSpec: 'Gen 1:1', verseIds: ['01001001']],
             relatedVerses   : [verseSpec: 'Gen 9:9', verseIds: ['02002002']],
             relationshipType: 'Same As', notes: 'This verse refers to the same time as Gen 1:1'
            ], [subjectVerses   : [verseSpec: 'Gen 1:1', verseIds: ['01001001']],
                relatedVerses   : [verseSpec: 'Gen 9:9', verseIds: ['02002002']],
                relationshipType: 'Same As', notes: 'This verse refers to the same time as Gen 1:1'
            ]];

    def addVerseRelationship(subjectVerses, relationshipType, relatedVerses) {
        xref.add([
                subjectVerses   : subjectVerses,
                relatedVerses   : relatedVerses,
                relationshipType: relationshipType])

    }

    def findVerseRelationshipsBySubjectVerse(subjectVerses) {
        xref.findAll { vr ->
            matchVerses(subjectVerses, vr.subjectVerses)
        }
    }

    def findVerseRelationshipsByRelatedVerse(relatedVerses) {
        xref.findAll { vr ->
            matchVerses(relatedVerses, vr.relatedVerses)
        }
    }

    def matchVerses(List verseIdList1, List verseIdList2) {
        verseIdList1.intersect(verseIdList2).size() == verseIdList1.size() ||
                verseIdList2.intersect(verseIdList1).size() == verseIdList2.size()
    }

    def verseRelationships() {
        xref
    }

    def relationships() {
        relationshipDefs
    }
}
