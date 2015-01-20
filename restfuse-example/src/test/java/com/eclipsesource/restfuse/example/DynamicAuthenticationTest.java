package com.eclipsesource.restfuse.example;

import static com.eclipsesource.restfuse.Assert.assertUnauthorized;
import static com.eclipsesource.restfuse.AuthenticationType.BASIC;

import org.junit.Rule;
import org.junit.runner.RunWith;

import com.eclipsesource.restfuse.Destination;
import com.eclipsesource.restfuse.HttpJUnitRunner;
import com.eclipsesource.restfuse.Method;
import com.eclipsesource.restfuse.Response;
import com.eclipsesource.restfuse.annotation.Context;
import com.eclipsesource.restfuse.annotation.HttpTest;

@RunWith(HttpJUnitRunner.class)
public class DynamicAuthenticationTest {
	@Rule
	public Destination restfuse = new Destination(this, "https://eclipsesource.com");

	@Context
	private Response response;

	@HttpTest(method = Method.GET, path = "/blogs/wp_admin")
	public void testAuthentication() {
		assertUnauthorized(response);
	}

	@HttpTest(method = Method.GET, path = "/blogs/wp_admin")
	public void testAuthenticationWithInvalidCredentials() {
		restfuse.getRequestContext().setAuthentication("invalid", "invalid", BASIC);
		assertUnauthorized(response);
	}

}
