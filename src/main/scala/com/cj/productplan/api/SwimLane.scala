package com.cj.productplan.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown=true)
case class SwimLane(
    id:Int, 
    name:String, 
    bars:Seq[SwimLaneBarWrapper])
