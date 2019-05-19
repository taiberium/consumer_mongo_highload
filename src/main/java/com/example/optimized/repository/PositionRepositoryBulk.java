package com.example.optimized.repository;

import com.example.optimized.model.Position;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.mongodb.core.BulkOperations;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class PositionRepositoryBulk {

    private static final String ID = "_id";
    private static final String CLASS = "_class";

    private final MongoOperations mongoOperations;
    private final MongoConverter converter;
    private final ConversionService conversionService;

    public PositionRepositoryBulk(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
        this.converter = mongoOperations.getConverter();
        this.conversionService = converter.getConversionService();
    }

    public void saveAll(Iterable<Position> positions) {
        BulkOperations bulkOps = mongoOperations.bulkOps(BulkOperations.BulkMode.UNORDERED, Position.class);

        // add "save" operation for each entity

        for (Position position : positions) {
            boolean isNew = position.getId() == null;
            if (isNew) {
                String id = conversionService.convert(new ObjectId(), String.class); // generate NEW id
                position.setId(id);
                bulkOps.insert(position);

            } else { // --- if EXISTING entity, then UPSERT ---

                Document document = new Document();
                converter.write(position, document);
                document.remove(ID);
                document.remove(CLASS);

                Update update = Update.fromDocument(new Document("$set", document));
                Query query = Query.query(Criteria.where(ID).is(position.getId()));
                bulkOps.upsert(query, update);
            }
        }

        bulkOps.execute();
    }

    public void insert(Iterable<Position> positions) {
        BulkOperations bulkOps = mongoOperations.bulkOps(BulkOperations.BulkMode.UNORDERED, Position.class);

        for (Position position : positions) {
            if (position.getId() == null) {
                String id = conversionService.convert(new ObjectId(), String.class);
                position.setId(id);
            }
            bulkOps.insert(position);
        }

        bulkOps.execute();
    }
}
