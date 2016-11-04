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

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

// This class is responsible to "decode" the received String 
// from clients in a ServiceLocationDecoder object 
public class ServiceLocationDecoder implements Decoder.Text<ServiceLocation> {

    // create a ServiceLocation object from JSON
    @Override
    public ServiceLocation decode(String msg) throws DecodeException {
        System.out.println("Decoding: " + msg);
        // It uses the JSON-P API to parse JSON content
        
        JsonReader reader = Json.createReader(new StringReader(msg));
        JsonObject jsonObject = reader.readObject();
        JsonNumber lat = null;
		JsonNumber lng = null;
		String userName = null;
		String command = null;
		String address = null;
		String comment = null;
		Integer rating = null;
		Double costService = null;
		String telefono = null;
        try{
	    	 command = jsonObject.isNull("command") ? "" : jsonObject.getString("command");
        }catch (Exception e) {};
        try{
	    	 lat = jsonObject.isNull("lat") ? null : jsonObject.getJsonNumber("lat");
    }catch (Exception e) {};
    try{
		        lng = jsonObject.isNull("lng") ? null : jsonObject.getJsonNumber("lng");
}catch (Exception e) {};
try{
	    	userName = jsonObject.isNull("userName") ? "" : jsonObject.getString("userName");
	    }catch (Exception e) {};
	    try{
	        telefono = jsonObject.isNull("phone") ? "" : jsonObject.getString("phone");
	    }catch (Exception e) {};
	    try{
	        address = jsonObject.isNull("address") ? "" : jsonObject.getString("address");
	    }catch (Exception e) {};
	    try{
	        comment = jsonObject.isNull("comment") ? "" : jsonObject.getString("comment");
	    }catch (Exception e) {};
	    try{
	        rating =  (Integer) (jsonObject.isNull("rating") ? "" : jsonObject.getInt("rating"));
	    }catch (Exception e) {};
	    try{
	        costService = (Double) (jsonObject.isNull("costService") ? JsonNumber.NULL : jsonObject.getInt("costService"));
	    }catch (Exception e) {};
 
    	ServiceLocation serv = new ServiceLocation(command,userName,lat,lng,address,comment,rating,costService);
    	serv.setTelefono(telefono);
        return serv;
    }

    @Override
    public boolean willDecode(String msg) {
        return true;
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(EndpointConfig config) {
    }
}
