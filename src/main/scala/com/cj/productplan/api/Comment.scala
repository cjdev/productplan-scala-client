package com.cj.productplan.api

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.joda.time.Instant
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown=true)
case class Comment (
    id:Int, 
    body:String, 
    user_id:Int, 
    user_name:String, 
    @JsonDeserialize(using=classOf[InstantDeSerializer]) 
    created_at:Instant)