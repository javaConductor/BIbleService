package org.swordexplorer.crossref.db

import com.mongodb.BasicDBObject
import com.mongodb.DBObject
import com.mongodb.WriteResult
import groovyjarjarantlr.collections.List
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import org.swordexplorer.bible.BibleService
import org.swordexplorer.crossref.domain.VerseRelationship

/**
 * Created by lcollins on 10/17/2014.
 */
@Service("xrefDb")
class XRefDb {
    BibleService bibleService
    MongoTemplate mongo

    XRefDb(BibleService bibleService, MongoTemplate mongo) {
        this.mongo =mongo
        this.bibleService = bibleService
    }

    VerseRelationship createRelationship(VerseRelationship verseRelationship) {
        mongo.save(verseRelationship)
        verseRelationship
    }/// createRelationship

    boolean removeRelationship(relationshipId) {
        WriteResult wr = mongo.remove(new Query(Criteria.where('id').is(relationshipId)), VerseRelationship)
        wr.n > 0
    }/// removeRelationship

    VerseRelationship updateRelationship(VerseRelationship verseRelationship) {
        verseRelationship.comments = verseRelationship.comments ?: ""
        mongo.save(verseRelationship);
        verseRelationship
    }/// updateRelationship

    VerseRelationship getRelationship(ObjectId id) {
        mongo.findById ( id, VerseRelationship )
    }/// updateRelationship

    List allRelationships() {
        List l = mongo.findAll(VerseRelationship) as List
      l
    }/// allRelationships

    DBObject dbObject(VerseRelationship verseRelationship) {
        def tmp = [
                "relationshipType": verseRelationship.relationshipType,
                "comments"        : verseRelationship.comments,
                "verseRange"        :  verseRelationship.verseRange,
                "relatedRange"      :  verseRelationship.relatedRange
        ]
        if (verseRelationship.id)
            tmp.id = new ObjectId(verseRelationship.id);
        tmp as BasicDBObject;
    }
}
