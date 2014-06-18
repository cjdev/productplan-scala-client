package com.cj.productplan.api

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.joda.time.YearMonthDay

@JsonIgnoreProperties(ignoreUnknown=true)
case class SwimLaneBarDetails(id:Int, name:String, container:Boolean, container_id:Int,
    description:String,
    duration:Int,
    @JsonDeserialize(using=classOf[YearMonthDayDeSerializer])
    start_date:YearMonthDay,
    notes:String,
    parked:Boolean,
    is_contained:Boolean)
{
  def whenProjectedComplete():YearMonthDay =  start_date.plusDays(duration)
}