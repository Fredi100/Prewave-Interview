package com.github.fredi100.prewave.db

import com.github.fredi100.prewave.data.EdgeDto
import com.github.fredi100.prewave.exception.RecursiveEdgeException
import com.github.fredi100.prewave.jooq.tables.records.EdgeRecord
import com.github.fredi100.prewave.jooq.tables.references.EDGE
import org.jooq.DSLContext
import org.jooq.Result
import org.springframework.stereotype.Repository

@Repository
class EdgeRepository(private val dsl: DSLContext) {

    // https://stackoverflow.com/questions/75954320/recursive-tree-query-in-postgresql
    // https://www.geeksforgeeks.org/postgresql/postgresql-recursive-query-using-ctes/
    fun isRecursive(fromId: Int, toId: Int): Boolean {
        // Direct self-loop
        if (fromId == toId) return true

        val query = """
            WITH RECURSIVE path_check AS (
                SELECT from_id, to_id FROM edge WHERE from_id = ?
                UNION
                SELECT e.from_id, e.to_id 
                FROM edge e
                JOIN path_check p ON e.from_id = p.to_id
                WHERE e.from_id != ?
            )
            SELECT COUNT(*) > 0 as would_create_cycle
            FROM path_check
            WHERE to_id = ?
        """.trimIndent()

        val result = this.dsl.fetchOne(query, toId, fromId, fromId)
        return result?.get(0, Boolean::class.java) ?: false
    }

    fun createEdge(edge: EdgeDto): Int {
        if (isRecursive(edge.fromId, edge.toId)) {
            throw RecursiveEdgeException("Edge from ${edge.fromId} to ${edge.toId} would create a cycle within the tree")
        }

        return this.dsl.insertInto(EDGE)
            .set(EDGE.FROM_ID, edge.fromId)
            .set(EDGE.TO_ID, edge.toId)
            .execute()
    }

    fun deleteEdge(edge: EdgeDto): Int {
        return this.dsl.deleteFrom(EDGE)
            .where(EDGE.FROM_ID.eq(edge.fromId).and(EDGE.TO_ID.eq(edge.toId)))
            .execute()
    }

    fun getAllEdges(): Result<EdgeRecord?> {
        return this.dsl.fetch(EDGE)
    }

    // I tried doing it directly with dsl, but it was way more complicated than necessary.
    fun getTreeFromNode(nodeId: Int): Result<EdgeRecord> {
        val query = """
            WITH RECURSIVE tree AS (
                SELECT from_id, to_id FROM edge WHERE from_id = ?
                UNION ALL
                SELECT e.from_id, e.to_id 
                FROM edge e
                JOIN tree t ON e.from_id = t.to_id
            )
            SELECT from_id, to_id FROM tree
        """.trimIndent()

        return this.dsl.fetch(query, nodeId).into(EDGE)
    }
}