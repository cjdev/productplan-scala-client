package com.cj.productplan.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown=true)
case class SwimLaneBarWrapper( 
    comments:Seq[Comment], 
    bar:SwimLaneBarDetails,
    lane_position:Int,
    min_duration:Int, 
    container_bars:Seq[SwimLaneBarDetails],
    links:Seq[String],
    legend_id:Int)