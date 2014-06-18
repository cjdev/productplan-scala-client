package com.cj.productplan.api

import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.client.methods.HttpPost
import org.apache.http.message.BasicNameValuePair
import org.apache.http.client.entity.UrlEncodedFormEntity
import scala.collection.JavaConversions._
import org.apache.http.client.methods.HttpGet
import org.apache.commons.io.IOUtils
import org.apache.http.client.RedirectStrategy
import org.apache.http.impl.client.DefaultRedirectStrategy
import org.apache.http.HttpRequest
import org.apache.http.HttpResponse
import org.apache.http.protocol.HttpContext
import org.apache.http.HttpEntity
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import org.joda.time.YearMonthDay
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import scala.math.Ordering
import org.joda.time.Period
import org.joda.time.DateTime
import org.joda.time.Instant
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext

class ProductPlanClient (email:String, password:String) {
  
  val client = new DefaultHttpClient
  client.setRedirectStrategy(new DefaultRedirectStrategy(){
    override def isRedirectable(method:String) = false 
    override def isRedirected(request:HttpRequest, response:HttpResponse , context:HttpContext) = false
  })
  
  login()
 
  def getAuthenticityToken()={
    val request = new HttpGet("https://app.productplan.com/users/sign_in")
    val response = client.execute(request)
    
    val html = IOUtils.toString(response.getEntity().getContent())
    request.releaseConnection()
    
    val ParamPattern = """<meta content="(.*)" name="csrf-param" />""".r
    val ValuePattern = s"""<meta content="(.*)" name="csrf-token" />""".r
    
    val param = ParamPattern.findFirstMatchIn(html).get.group(1)
    val token = ValuePattern.findFirstMatchIn(html).get.group(1)
    (param, token)
  }
  
  def login(){
    println("login")
    val authenticityTokens = getAuthenticityToken()
    println("  authenticity: " + authenticityTokens)
    val request = new HttpPost("https://app.productplan.com/users/sign_in")
    

    val params = List(new BasicNameValuePair("user[email]", email),
                      new BasicNameValuePair("user[password]", password),
                      new BasicNameValuePair("user[remember_me]", "0"),
                      new BasicNameValuePair(authenticityTokens._1, authenticityTokens._2))
    request.setEntity(new UrlEncodedFormEntity(params));
    
    val response = client.execute(request)
    request.releaseConnection()
    
    println(response.getStatusLine())
  }


  def bodyToString(response:HttpResponse):String = IOUtils.toString(response.getEntity().getContent())
  
  def urlCodeForRoadmap(id:Int):String = {
    println("get roadmap")
    val request = new HttpGet("https://app.productplan.com/roadmaps/" + id)
    val response = client.execute(request)
    println(response.getStatusLine())
    val location = response.getHeaders("Location").map(_.getValue()).mkString("")
    println(location)
    
    val Pattern = "https://app.productplan.com/(.*)".r
    val urlCode = Pattern.findFirstMatchIn(location).get.group(1)
    if(urlCode.contains("sign_in")) throw new Exception("Login failed (told me to go to " + location + ")")
    
    println(s"urlCode was $urlCode")
    
    val content = bodyToString(response)
    request.releaseConnection()
    urlCode
  }
  def roadmap(id:Int):Roadmap = roadmap(urlCodeForRoadmap(id))
  def roadmap(urlCode:String):Roadmap = {
    val request = new HttpGet("https://app.productplan.com/" + urlCode){
      setHeader("Accept", "application/json")
    }
    
    val response = client.execute(request)
    val data = bodyToString(response)
    request.releaseConnection()
    
    val jackson = new ObjectMapper
    jackson.registerModule(DefaultScalaModule)
    
    val roadMap = jackson.readValue(data, classOf[Roadmap])
    roadMap
  }
}