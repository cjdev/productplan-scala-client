package com.cj.productplan.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown=true)
case class Roadmap(
    lanes:Seq[SwimLane],
    parkinglot:Seq[SwimLane],
    roadmap:RoadmapProperties
)