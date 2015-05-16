/*******************************************************************************
 * Copyright (c) 2011 EclipseSource and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Holger Staudacher - initial API and implementation
 ******************************************************************************/
package com.eclipsesource.restfuse.internal;

import com.eclipsesource.restfuse.MediaType;
import com.github.kevinsawicki.http.HttpRequest;
import org.junit.Before;
import org.junit.Test;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ResponseContentType_TestNull {

    private ResponseImpl response;

    @Before
    public void setUp() throws MalformedURLException {
        HttpRequest httpRequest = mockRequest();
        response = new ResponseImpl( httpRequest );
    }

    private HttpRequest mockRequest() throws MalformedURLException {
        HttpRequest httpRequest = mock( HttpRequest.class );
        HttpURLConnection connection = mock( HttpURLConnection.class );
        when( connection.getURL() ).thenReturn( new URL( "http://test.com" ) );
        when( httpRequest.getConnection() ).thenReturn( connection );
        when( httpRequest.code() ).thenReturn( 200 );
        when( httpRequest.contentType() ).thenReturn( null);

        return httpRequest;
    }


    @Test
    public void testGetMediaTypeNullNoException() {
        response.getType();
    }

}
