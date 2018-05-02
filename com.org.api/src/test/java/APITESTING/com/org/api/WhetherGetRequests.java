package APITESTING.com.org.api;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.apache.http.HttpStatus;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import static io.restassured.RestAssured.*;

public class WhetherGetRequests {


	

	//Simple Get Request for getting Whether request by City Name
	//Statuscode = 200.

	@Test( priority = 1 )
 	public void Test_01(){

		Response res=	when().get("http://api.openweathermap.org/data/2.5/weather?q=London&appid=e1083e90516fcdc932d8b2f515e10923");
		System.out.println(res.getStatusCode());
		Assert.assertEquals(res.getStatusCode(), HttpStatus.SC_OK);
	}


	//Statuscode = 401.
	@Test( priority = 2 )
	public void Test_02(){

		Response res=	when().
				get("http://api.openweathermap.org/data/2.5/weather?q=London&appid=e1083e90516fcdc932d8b2f515e10");
		System.out.println(res.getStatusCode());
		Assert.assertEquals(res.getStatusCode(), 401);
	}


	//How to user parameters with rest assured
	@Test( priority = 3 )
	public void Test_03(){

		Response res= given().
				param("q", "London").
				param("appid", "e1083e90516fcdc932d8b2f515e10923").
				when().get("http://api.openweathermap.org/data/2.5/weather");
		System.out.println(res.getStatusCode());
		Assert.assertEquals(res.getStatusCode(), HttpStatus.SC_OK);
	}


	//Assert our testcase in Rest Assured api
	@Test( priority = 4 )
	public void Test_04(){

		given().
		param("q", "London").
		param("appid", "e1083e90516fcdc932d8b2f515e10923").
		when().get("http://api.openweathermap.org/data/2.5/weather").then().assertThat().statusCode(HttpStatus.SC_OK);

	}


	@Test( priority = 5 )
	public void Test_05(){

		Response res = given().
				param("q", "London").
				param("appid", "e1083e90516fcdc932d8b2f515e10923").
				when().get("http://api.openweathermap.org/data/2.5/weather");

		System.out.println(res.asString());

	}


	@Test( priority = 6 )
	public void Test_06(){


		Response resp = given().param("id", "2172797").param("appid", "e1083e90516fcdc932d8b2f515e10923").
				when().get("http://api.openweathermap.org/data/2.5/weather");		
		Assert.assertEquals(resp.getStatusCode(), HttpStatus.SC_OK);
		System.out.println(resp.asString());

	}

	//Make jsonpath to extract information

	@Test( priority = 7 )
	public void Test_07(){

		Response resp  = given().param("id", "2172797").param("appid", "e1083e90516fcdc932d8b2f515e10923").
				when().get("http://api.openweathermap.org/data/2.5/weather");

		String actualWetherReport = resp.
				then().contentType(ContentType.JSON).and().extract().path("weather[0].description");

		String expectedWeatherReport = null;

		if(actualWetherReport.equalsIgnoreCase(expectedWeatherReport)){
			System.out.println("TestCase Pass");
		}
		else {
			System.out.println("TestCase Fail");
		}

		System.out.println("weatherReport description is " + actualWetherReport);

	}


	@Test( priority = 8 )
	public void Test_08(){

		String weatherReport = given().param("id", "2172797").param("appid", "e1083e90516fcdc932d8b2f515e10923").
				when().get("http://api.openweathermap.org/data/2.5/weather").
				then().contentType(ContentType.JSON).and().extract().path("weather[0].description");



		System.out.println("weatherReport description is " + weatherReport);

	}

	
	//Transferming API response to Other API
	
	@Test( priority = 9 )
	public void Test_09(){
		Response resp = given().
				param("id", "2172797").
				param("appid", "e1083e90516fcdc932d8b2f515e10923").
				when().
				get("http://api.openweathermap.org/data/2.5/weather");

		String reportbyID = resp.then().
				contentType(ContentType.JSON).
				extract().
				path("weather[0].description");

		System.out.println("Wether description by Id "+ reportbyID);
		String lon = String.valueOf(resp.then().
				     contentType(ContentType.JSON).
				     extract().path("coord.lon"));
		System.out.println("longtude is :"+ lon);
		
		String lat = String.valueOf(resp.then().
			     contentType(ContentType.JSON).
			     extract().path("coord.lat"));
	System.out.println("latitude is :"+ lat);
	
	
	String  reportbyCoordinates = given().
			param("lat", lat).
			param("lon", lon).
			param("appid", "e1083e90516fcdc932d8b2f515e10923").
			when().
			get("http://api.openweathermap.org/data/2.5/weather").then().contentType(ContentType.JSON).
			extract().
			path("weather[0].description");

	System.out.println("Report by coordinates"+ reportbyCoordinates );
	
	Assert.assertEquals(reportbyID, reportbyCoordinates);
	
	
	
	}




}
