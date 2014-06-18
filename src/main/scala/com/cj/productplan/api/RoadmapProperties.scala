package com.cj.productplan.api

import org.joda.time.YearMonthDay
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import org.joda.time.Instant
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown=true)
case class RoadmapProperties (
    id:Int,
    name:String,
    is_snapshot: Boolean,
    description: String,
    @JsonDeserialize(using=classOf[YearMonthDayDeSerializer])
    start_date:YearMonthDay,
    @JsonDeserialize(using=classOf[YearMonthDayDeSerializer])
    end_date:YearMonthDay,
    @JsonDeserialize(using=classOf[InstantDeSerializer])
    created_at:Instant,
    @JsonDeserialize(using=classOf[InstantDeSerializer])
    updated_at:Instant,
    url_code: String,
    user_id: Int,
    @JsonDeserialize(using=classOf[InstantDeSerializer])
    snapshot_created_at: Instant,
    public_url_code:String
)