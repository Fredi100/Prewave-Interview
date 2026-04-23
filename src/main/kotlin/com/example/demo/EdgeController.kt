package com.example.demo

import com.example.demo.dto.EdgeDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/edge")
class EdgeController {

    final val edgeRepository: EdgeRepository

    @Autowired
    constructor(edgeRepository: EdgeRepository) {
        this.edgeRepository = edgeRepository
    }

    // TODO: Have to do some more validation and also better Exception handling
    @PostMapping("/create")
    fun createEdge(@RequestBody edge: EdgeDto): ResponseEntity<EdgeDto> {
        val result = edgeRepository.createEdge(edge)

        if (result > 0)
            return ResponseEntity.status(HttpStatus.CREATED).body(edge)

        return ResponseEntity.status(HttpStatus.CONFLICT).build()
    }

    @PostMapping("/delete")
    fun deleteEdge(@RequestBody edge: EdgeDto): String {
        edgeRepository.deleteEdge(edge)
        return "Deleted Edge"
    }
}