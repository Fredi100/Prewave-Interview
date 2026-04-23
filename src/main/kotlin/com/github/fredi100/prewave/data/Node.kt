package com.github.fredi100.prewave.data

data class Node(
    val id: Int,
    val depth: Int,
    val children: List<Node> = emptyList()
)
