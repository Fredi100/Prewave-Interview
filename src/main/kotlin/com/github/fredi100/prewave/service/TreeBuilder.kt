package com.github.fredi100.prewave.service

import com.github.fredi100.prewave.data.Node
import com.github.fredi100.prewave.db.EdgeRepository
import org.springframework.stereotype.Service

@Service
class TreeBuilder(private val edgeRepository: EdgeRepository) {

    fun buildTree(nodeId: Int): Node {
        val edges = edgeRepository.getTreeFromNode(nodeId)
            .groupBy({ it.fromId!! }, { it.toId!! })

        if (edges.isEmpty()) {
            throw IllegalArgumentException("Node with id $nodeId does not exist")
        }

        return buildNode(nodeId, edges, emptySet(), currentDepth = 0)
    }

    private fun buildNode(
        currentNodeId: Int,
        adjacency: Map<Int, List<Int>>,
        path: Set<Int>,
        currentDepth: Int
    ): Node {
        if (currentNodeId in path || currentDepth > 100) {
            return Node(currentNodeId, currentDepth)
        }

        val nextPath = path + currentNodeId
        val children = adjacency[currentNodeId]
            .orEmpty()
            .distinct()
            .map { childId -> buildNode(childId, adjacency.minus(currentNodeId), nextPath, currentDepth + 1) }

        val maxDepth = children.maxOfOrNull { it.depth } ?: currentDepth

        return Node(currentNodeId, maxDepth, children)
    }
}