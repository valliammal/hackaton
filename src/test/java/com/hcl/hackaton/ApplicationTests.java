package com.hcl.hackaton;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.hcl.hackaton.model.Acct;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		
		String rootUrl = "http://localhost:" + port;
		System.out.println("Root Url " + rootUrl);
		return rootUrl;
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void testGetAllAccts() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/Accts",
				HttpMethod.GET, entity, String.class);
		System.out.println("Acct Details ");
		System.out.println(response.toString());
		Assert.assertNotNull(response.getBody());
	}

	@Test
	public void testGetAcctById() {
		Acct Acct = restTemplate.getForObject(getRootUrl() + "/Accts/1", Acct.class);

		System.out.println("Acct Details 1");
		System.out.println(Acct.toString());

		Assert.assertNotNull(Acct);
	}

	@Test
	public void testCreateAcct() {
		Acct Acct = new Acct();
		Acct.setEmail("admin@gmail.com");
		Acct.setFirstName("admin");
		Acct.setLastName("admin");
		Acct.setCreatedBy("admin");
		Acct.setUpdatedBy("admin");

		ResponseEntity<Acct> postResponse = restTemplate.postForEntity(getRootUrl() + "/Accts", Acct, Acct.class);
		System.out.println(postResponse.toString());
		
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());
	}

	@Test
	public void testUpdatePost() {
		int id = 1;
		Acct Acct = restTemplate.getForObject(getRootUrl() + "/Accts/" + id, Acct.class);
		Acct.setFirstName("admin1");
		Acct.setLastName("admin2");

		restTemplate.put(getRootUrl() + "/Accts/" + id, Acct);

		Acct updatedAcct = restTemplate.getForObject(getRootUrl() + "/Accts/" + id, Acct.class);
		System.out.println(" the acct details " + updatedAcct.toString());
		Assert.assertNotNull(updatedAcct);
	}

	@Test
	public void testDeletePost() {
		int id = 2;
		Acct Acct = restTemplate.getForObject(getRootUrl() + "/Accts/" + id, Acct.class);
		Assert.assertNotNull(Acct);

		restTemplate.delete(getRootUrl() + "/Accts/" + id);

		try {
			Acct = restTemplate.getForObject(getRootUrl() + "/Accts/" + id, Acct.class);
		} catch (final HttpClientErrorException e) {
			Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}

}
