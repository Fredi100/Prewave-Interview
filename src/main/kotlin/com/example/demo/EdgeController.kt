package com.example.demo

import com.example.demo.dto.EdgeDto
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/example")
class EdgeController {

    final val edgeRepository: EdgeRepository

    constructor(edgeRepository: EdgeRepository) {
        this.edgeRepository = edgeRepository
    }

    // TODO: Have to do some more validation and also better Exception handling
    @PostMapping("/create")
    fun createEdge(@RequestBody edge: EdgeDto): String {
        edgeRepository.createEdge(edge)
        return "Created Edge"
    }

    @PostMapping("/delete")
    fun deleteEdge(@RequestBody edge: EdgeDto): String {
        edgeRepository.deleteEdge(edge)
        return "Deleted Edge"
    }
}