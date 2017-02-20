package group.mylun;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

import static org.hamcrest.Matchers.equalTo;

import java.util.Map;

//import org.hamcrest.Matchers.*;
import org.junit.Test;

public class AppTest {
    static final String API_ENDPOINT_URL = "www.testdomain.com";
    static final ContentType CONTENT_TYPE = null ;
    static String sJSONBody;
    static final String USERNAME = "foo";
    static final String PASSWORD = "bar";
    static final String NewSize = "5Gi";
    static String id = "1000";
    
	  @Test
	  public void getListOfLuns() {
		  given().
		  expect().
		  	statusCode(200).
		  when().
		  	get("/lun");	  
      }

	  @Test
	  public void getListOfLunsWithWrongToken() {
		  given().
          	authentication().preemptive().basic("WrongName", "WrongPassword").
          expect().
          	statusCode(400).
          when().
          	get("/lun");	  
      }
	  
	  @Test	 
	  public void addALun() {
		  given().
		  	parameters("id", id, "size", NewSize).
		  expect().
		  	body(equalTo("{\"id\": id}")).
		  when().
		  	put("/lun");      
	  }

	  @Test
	  //id contain illegal character
	  public void addALunWithIlegalID() {
		  given().
		  	parameters("id", "ID_HAS_ILLEGAL_CHARACTER", "size", "theSize").
		  expect().
          	statusCode(400).
		  when().
		  	put("/lun");      
		  }

	  @Test
	  //Below zero, lager than the limit, larger than left size in the pool
	  public void addALunWithIlegalSize() {
		  given().
		  parameters("id", "NEW_ID", "size", "The_Size").
		  expect().
          statusCode(400).
		  when().
		  put("/lun");      
		  }

	  @Test
	  //id exist already
	  public void addAExistedLun() {
		  given().
		  parameters("id", "ID_IN_USE", "size", "The_Size").
		  expect().
		  	statusCode(200).
		  when().
		  	put("/lun");	  
      }

//records.json
//  {
//   "luns" :
//      [
//        { "id" : "ID1",  "size" :  "SIZE1" },
//        { "id" : "ID2",  "size" :  "SIZE2" }
//      ]
//   }	  
	  @Test
	  public void addMultipleLuns() {
		  final byte[] bytes = IOUtils.toByteArray(getClass().getResourceAsStream("/path/records.json"));
		  given().
			content(bytes).
		  expect().
		  	statusCode(200).
		  when().
		  	put("/lun/batch");	  
      }



	  @Test
	  //fail if part of id illegal or the overall size overhead
	  public void addMultipleLunsShouldFail() {
		  final byte[] bytes = IOUtils.toByteArray(getClass().getResourceAsStream("/path/wrong_records.json"));
		  given().
			content(bytes).
		  expect().
		  	statusCode(400).
		  when().
		  	put("/lun/batch");	  
      }

	  @Test
	  public void resizeLun() {
	        given().
            formParam("size", NewSize).
            expect().
            body("size", equalTo(NewSize)).
            when().
            put("/lun/THE_ID");      
      }

	  @Test
	  //fail if size illeagal or size overhead
	  public void resizeLunShouldFail() {
	        given().
            	formParam("size", NewSize).
            expect().
            	status(400).
            when().
            	put("/lun/THE_ID");      
      }

	  @Test
	  public void removeExistedLun() {
		  given().
		  expect().
		  	statusCode(200).
		  when().
		  	delete("/lun/existed_id");	  
      }

	  @Test
	  public void removeNonExistedLun() {
		  given().
		  expect().
		  	statusCode(404).
		  when().
		  	delete("/lun/non_existed_id");	  
      }
	  
	  @Test
	  public void removeInUseLun() {
		  given().
		  expect().
		  	statusCode(400).
		  when().
		  	delete("/lun/id_in_use");	  
      }

	  @Test
	  public void getLunInfo() {
		  given().
		  expect().
		  	statusCode(200).
		  	body("size", equalTo(NewSize)).
		  when().
		  	get("/lun/id");	  
      }
	  
	  @Test
	  public void getLunInfoWithWrongID() {
		  given().
		  expect().
		  	statusCode(400).
		  when().
		  	get("/lun/WRONG_ID");	  
      }

	  @Test
	  // concurrency read should succeed
	  // concurrency read on write, the read operation should wait for write operation
	  // concurrency writes should fast fail.
	  public void testConcurrency(){
		  
	  }
	  
	  @Test
	  // test after serialization and de-serialization, the data is the same
	  public void verifyPersistance() {
	  
      }	  
}


	  

