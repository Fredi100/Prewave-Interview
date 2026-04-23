package com.example.demo

import com.example.demo.dto.EdgeDto
import com.example.demo.jooq.tables.references.EDGE
import org.jooq.DSLContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class EdgeRepository {

    final val create: DSLContext

    @Autowired
    constructor(dslContext: DSLContext) {
        this.create = dslContext;
    }

    fun createEdge(edge: EdgeDto) {
        this.create.insertInto(EDGE)
            .set(EDGE.FROM_ID, edge.fromId)
            .set(EDGE.TO_ID, edge.toId)
            .execute()
    }

    fun deleteEdge(edge: EdgeDto) {
        this.create.deleteFrom(EDGE)
            .where(EDGE.FROM_ID.eq(edge.fromId).and(EDGE.TO_ID.eq(edge.toId)))
            .execute()
    }
}