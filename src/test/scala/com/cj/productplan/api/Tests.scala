package com.cj.productplan.api

import org.junit.Test
import org.joda.time.Instant
import scala.beans.BeanProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.junit.Assert
import org.joda.time.DateTime
import org.joda.time.DateTimeZone

case class TestObject(
       @JsonDeserialize(using=classOf[InstantDeSerializer]) 
       val when:Instant
    )

class Tests {
    val jackson = new ObjectMapper(){
      registerModule(DefaultScalaModule)
    }
    
    @Test
    def testInstantDeserializer(){
      // given
      val json = """{"when":"2014-03-11T16:02:05-07:00"}"""
        
      // when
      val result = jackson.readValue(json, classOf[TestObject])
      
      // then
      Assert.assertEquals(
          new DateTime(2014, 3, 11, 16, 2, 5, 0, DateTimeZone.forOffsetHours(-7)).toInstant(), 
          result.when)
    }
    
    @Test
    def commentDeserialization(){
      // given
      val json = """{"id":3311,"body":"Great idea!","user_id":3223,"user_name":"Joey Schmoey","created_at":"2014-03-11T16:02:05-07:00"}"""
      
      // when
      val result = jackson.readValue(json, classOf[Comment])
      
      // then
      val expected = Comment(
            id=3311, 
            body="Great idea!", 
            user_id=3223, 
            user_name="Joey Schmoey", 
            created_at=new DateTime(2014, 3, 11, 16, 2, 5, 0, DateTimeZone.forOffsetHours(-7)).toInstant())
        
      Assert.assertEquals(expected, result)
    }
}