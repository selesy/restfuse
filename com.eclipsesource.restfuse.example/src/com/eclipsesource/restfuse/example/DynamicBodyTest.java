/*******************************************************************************
 * Copyright (c) 2013 Andreas Mihm.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Andreas Mihm - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.restfuse.example;

import static com.eclipsesource.restfuse.Assert.assertOk;

import org.junit.Rule;
import org.junit.runner.RunWith;

import com.eclipsesource.restfuse.Destination;
import com.eclipsesource.restfuse.Method;
import com.eclipsesource.restfuse.RequestContext;
import com.eclipsesource.restfuse.Response;
import com.eclipsesource.restfuse.annotation.Context;
import com.eclipsesource.restfuse.annotation.HttpTest;
import com.eclipsesource.restfuse.example.junit.OrderedHttpJUnitRunner;

/**
 * CAUTION: The way how this class uses restfuse to be able to send dynamic
 * request bodies is a workaround which conflicts a bit with some principles of
 * junit usage. So its an example how to approach this problem, but the final
 * solution in restfuse should be to provide a annotaion free way to use
 * restfuse.
 * 
 * @author mihm
 * 
 */
@RunWith(OrderedHttpJUnitRunner.class)
public class DynamicBodyTest {

	@Rule
	public Destination restfuse = getDestination();

	@Context
	private Response response;

	// static variale to store the requestBody to be sent
	private static String requestBody;

	@HttpTest(method = Method.GET, path = "/v1/items")
	public void a_getItems() {
		// assertOk( response );
		String jsonResponse = response.getBody();

		// prepare next test method
		requestBody = "{\"name\":\"testitem\", \"description\":\"a new item\"}";
	}

	@HttpTest(method = Method.POST, path = "/v1/items")
	public void b_addItem() {
		// assertOk( response );
		String jsonResponse = response.getBody();
	}

	private Destination getDestination() {
		Destination destination = new Destination(this, "http://localhost");
		RequestContext context = destination.getRequestContext();
		if (requestBody != null) {
			context.setDynamicBody(requestBody);
		}
		return destination;
	}
}
