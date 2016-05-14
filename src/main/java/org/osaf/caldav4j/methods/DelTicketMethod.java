/*
 * Copyright 2006 Open Source Applications Foundation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.osaf.caldav4j.methods;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpConnection;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpState;
import org.apache.jackrabbit.webdav.client.methods.DavMethodBase;
import org.osaf.caldav4j.CalDAVConstants;
import org.osaf.caldav4j.util.CaldavStatus;
import org.osaf.caldav4j.util.UrlUtils;

import java.io.IOException;

/**
 * Method that will delete a ticket by id. Need to specify a path and ticket id
 * 
 * @author EdBindl
 * 
 */
public class DelTicketMethod extends DavMethodBase {

	private String ticket = null;


	public DelTicketMethod(String path, String ticket) {
		super(UrlUtils.removeDoubleSlashes(path));
		this.ticket = ticket;
		setPath(path);
	}

	public void setPath(String path){
		super.setPath(UrlUtils.removeDoubleSlashes(path));
	}

	public String getName() {
		return CalDAVConstants.METHOD_DELTICKET;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	/**
	 * Overwritten to add Ticket Request Header
	 */
	protected void addRequestHeaders(HttpState state, HttpConnection conn)
			throws IOException, HttpException {

		addRequestHeader(new Header(CalDAVConstants.TICKET_HEADER, ticket));
		super.addRequestHeaders(state, conn);
	}

	protected boolean isSuccess(int statusCode){
		return statusCode == CaldavStatus.SC_OK;
	}
}
