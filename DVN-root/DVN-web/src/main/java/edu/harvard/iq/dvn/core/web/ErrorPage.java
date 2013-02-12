/*
   Copyright (C) 2005-2012, by the President and Fellows of Harvard College.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

   Dataverse Network - A web application to share, preserve and analyze research data.
   Developed at the Institute for Quantitative Social Science, Harvard University.
   Version 3.0.
*/
/*
 * ErrorPage.java
 *
 * Created on March 29, 2007, 3:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package edu.harvard.iq.dvn.core.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import edu.harvard.iq.dvn.core.web.common.VDCBaseBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ellen Kraffmiller
 * (Leonid: added mechanism for returning HTTP status codes)
 */
@ViewScoped
@Named("ErrorPage")
public class ErrorPage extends VDCBaseBean implements java.io.Serializable  {
    private Integer errorCode = 0;
    
    /** Creates a new instance of ErrorPage */
    public ErrorPage() {
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer ec) {
        errorCode = ec;
    }

    public void init() {
        super.init();

        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse response = (javax.servlet.http.HttpServletResponse) context.getExternalContext().getResponse();

        if (errorCode !=null && errorCode != 0) {
            response.setStatus(errorCode);
        }
    }
    
    public List getMessages() {
        List msgs = new ArrayList();
        FacesContext context = FacesContext.getCurrentInstance();
        for (Iterator it = context.getMessages(); it.hasNext();) {
            FacesMessage elem = (FacesMessage) it.next();
            msgs.add(elem.getSummary());
        }
        return msgs;
    }
}
