package com.cj.productplan.api

import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.core.JsonParser
import org.joda.time._

class InstantDeSerializer extends JsonDeserializer[Instant]{
    override def deserialize(p:JsonParser, ctx:DeserializationContext) = {
      p.getValueAsString() match {
        case null=> null
        case s:String => new Instant(s)
      }
    }
}

class YearMonthDayDeSerializer extends JsonDeserializer[YearMonthDay]{
    override def deserialize(p:JsonParser, ctx:DeserializationContext) = {
      p.getValueAsString() match {
        case null=> null
        case s:String => new YearMonthDay(s)
      }
    }
}