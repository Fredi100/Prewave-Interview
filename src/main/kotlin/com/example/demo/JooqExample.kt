package com.example.demo

import com.example.demo.dto.EdgeDto
import com.example.demo.jooq.tables.records.EdgeRecord
import com.example.demo.jooq.tables.references.EDGE
import org.jooq.DSLContext
import org.jooq.Result
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class JooqExample {

    final val create: DSLContext

    @Autowired
    constructor(dslContext: DSLContext) {
        this.create = dslContext;
    }

    fun createEdge() {
        this.create.insertInto(EDGE)
            .set(EDGE.FROM_ID, 1)
            .set(EDGE.TO_ID, 2)
            .execute()
    }

    fun deleteEdge() {
        this.create.deleteFrom(EDGE)
            .where(EDGE.FROM_ID.eq(1).and(EDGE.TO_ID.eq(2)))
            .execute()
    }
}