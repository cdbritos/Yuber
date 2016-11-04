/*
 * JBoss, Home of Professional Open Source
 * Copyright 2014, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yuber.server;

import java.io.StringWriter;

import javax.json.Json;
import javax.json.JsonNumber;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;


// This class is responsible to encode the Bidding object in a String
// that will be sent to clients
public class ServiceLocationEncoder implements Encoder.Text<ServiceLocation> {

    @Override
    public void destroy() {

    }

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public String encode(ServiceLocation serviceLocation) throws EncodeException {
        JsonObjectBuilder json = Json.createObjectBuilder().
        add("respuesta", Json.createObjectBuilder()
        		.add( "command", serviceLocation.getCommand()).
        		add( "username", serviceLocation.getUserName() == null ? "" : serviceLocation.getUserName() ).
        		add( "address", serviceLocation.getAddress() == null ? "" : serviceLocation.getAddress() ).
        		add( "phone", serviceLocation.getTelefono() == null ? "" : serviceLocation.getTelefono() ).
        		add( "lat", serviceLocation.getLat() == null ? JsonNumber.NULL : serviceLocation.getLat() ).
        		add( "comment", serviceLocation.getComment() == null ? "" : serviceLocation.getComment() ).
        		add( "rating", serviceLocation.getRating() == null ? 0 : serviceLocation.getRating() ).
        		add( "costService", serviceLocation.getCostService() == null ? 0 : serviceLocation.getCostService() ).
        		add( "lng", serviceLocation.getLng() == null ? JsonNumber.NULL : serviceLocation.getLng() )
        		.build());
        StringWriter stWriter = new StringWriter();
        JsonWriter jsonWriter = Json.createWriter(stWriter);
        jsonWriter.writeObject(json.build());
        jsonWriter.close();
        //System.out.println("codificando json:"+json  );
        return stWriter.toString();
    }


}
