package com.github.fredi100.prewave.db

import com.github.fredi100.prewave.data.EdgeDto
import com.github.fredi100.prewave.jooq.tables.references.EDGE
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

    fun createEdge(edge: EdgeDto): Int {
        return this.create.insertInto(EDGE)
            .set(EDGE.FROM_ID, edge.fromId)
            .set(EDGE.TO_ID, edge.toId)
            .execute()
    }

    fun deleteEdge(edge: EdgeDto): Int {
        return this.create.deleteFrom(EDGE)
            .where(EDGE.FROM_ID.eq(edge.fromId).and(EDGE.TO_ID.eq(edge.toId)))
            .execute()
    }
}