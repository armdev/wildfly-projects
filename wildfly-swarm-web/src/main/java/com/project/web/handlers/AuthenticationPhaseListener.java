package com.project.web.handlers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.NumberFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;
import javax.faces.FacesException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class AuthenticationPhaseListener implements PhaseListener {

    private static final long serialVersionUID = 1L;
    private static HashMap<String, String> pagePermissionMapping = null;
    //private Logger log = Logger.getLogger(AuthenticationPhaseListener.class);
//MyCDIStuff stuff = context.getApplication().evaluateExpressionGet(context, "#{stuff}", MyCDIStuff.class);
    //http://mcatr.blogspot.am/2009/12/jsf-phaselisteners-with-annotations.html
    private void pagePermissionMapping() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (pagePermissionMapping == null) {
            pagePermissionMapping = new HashMap();
            try {
                ResourceBundle bundle = facesContext.getApplication().getResourceBundle(facesContext, "accessProp");
                if (bundle != null) {
                    Enumeration e = bundle.getKeys();
                    while (e.hasMoreElements()) {
                        String key = (String) e.nextElement();
                        String value = bundle.getString(key);
                        pagePermissionMapping.put(key, value);
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void afterPhase(PhaseEvent event) {
    }

    @Override
    public synchronized void beforePhase(PhaseEvent event) {
        FacesContext context = event.getFacesContext();
        ExternalContext ex = context.getExternalContext();
     //  System.out.println("START PHASE " + event.getPhaseId());
        try {
            if(event.getPhaseId().equals(PhaseId.RENDER_RESPONSE)){
               this.calculateStateSize(context);
            }
            //pagePermissionMapping();
           // this.memoryStatus();

            String viewId = "/index.xhtml";

            if (context.getViewRoot() != null && context.getViewRoot().getViewId() != null) {
                viewId = context.getViewRoot().getViewId();

            }
           // String permissions = pagePermissionMapping.get(viewId);
            //UserSessionController sessionContext = context.getApplication().evaluateExpressionGet(context, "#{userSessionController}", UserSessionController.class);
            // UserSessionController sessionContext = (UserSessionController) ex.getSessionMap().get("sessionContext");

            //if (sessionContext.getUser().getId() == null && !viewId.contains("index.xhtml")) {
            //  FacesContext.getCurrentInstance().getExternalContext().redirect("/tfm-system/index.jsf?illegalAccess");
            //}
            //if (permissions != null && permissions.contains("PUBLIC")) {
            //return;
            //}
            //System.out.println("sessionContext.getUser() " + sessionContext.getUser());
            //System.out.println("permissions " + permissions);
//            if (permissions != null && sessionContext.getUser().getId() == null && permissions.contains("LOGGED")
//                    && !viewId.contains("index.xhtml")) {
//                //    System.out.println("olala? " + permissions);
//                FacesContext.getCurrentInstance().getExternalContext().redirect("/tfm-system/index.jsf?tryagain=true");
//                //redirect(context, "#{request.contextPath}/index.jsf?illegalAccess");
//            }
        } catch (Exception ex1) {
            redirect(context, "/index.jsf?illegalError");
            ex1.printStackTrace();
        }
    }

    public void calculateStateSize(FacesContext context) {
        //FacesContext context = FacesContext.getCurrentInstance();
        Object state = context.getApplication().getStateManager().saveView(context);

        if (state != null) {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                    oos.writeObject(state);
                }
               // context.getAttributes().put("stateSize",
                 //      Integer.toString(baos.toByteArray().length));
                
              System.out.println("State size: "+Integer.toString(baos.toByteArray().length)+" bytes");
            } catch (IOException e) {
                //no op
                context.getAttributes().put("stateSize", "ERROR");
            }
        } else {
            context.getAttributes().put("stateSize", "0");
        }
    }

    private static void redirect(FacesContext facesContext, String url) {
        try {
            facesContext.getExternalContext().redirect(url);
        } catch (IOException e) {
            throw new FacesException("Cannot redirect to " + url + " due to IO exception.", e);
        }
    }

    public void memoryStatus() {
        Runtime runtime = Runtime.getRuntime();

        NumberFormat format = NumberFormat.getInstance();

        StringBuilder sb = new StringBuilder();
        long maxMemory = runtime.maxMemory();
        long allocatedMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();

        //sb.append("Free memory: " + format.format(freeMemory / 1024 / 1024) + "<br/>");
        //sb.append("Allocated memory: " + format.format(allocatedMemory / 1024 / 1024) + "<br/>");
        //sb.append("Max memory: " + format.format(maxMemory / 1024 / 1024) + "<br/>");
        //sb.append("Total free memory: " + format.format((freeMemory + (maxMemory - allocatedMemory)) / 1024 / 1024) + "<br/>");
      //  System.out.println("AvailableProcessors " + runtime.availableProcessors());

       // System.out.println("Free memory: " + format.format(freeMemory / 1024 / 1024) + " MB");
      //  System.out.println("Allocated memory: " + format.format(allocatedMemory / 1024 / 1024) + " MB");
       // System.out.println("Max memory: " + format.format(maxMemory / 1024 / 1024) + " MB");
        System.out.println("Total free memory: " + format.format((freeMemory + (maxMemory - allocatedMemory)) / 1024 / 1024) + " MB");
       // System.out.println("___________________________________________________");
       // long free = freeMemory / 1024 / 1024;
        // if (free < 670) {
        // System.out.println("Calling GC ");
        //  System.out.println("Memory before  " + free);
        //  runtime.gc();
        // System.out.println("memory after  " + free);
        //}
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }
}
