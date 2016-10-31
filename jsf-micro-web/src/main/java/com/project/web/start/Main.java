package com.project.web.start;

import com.project.web.auth.LoginBean;
import com.project.web.auth.MainDataBean;
import com.project.web.auth.PostBean;
import com.project.web.handlers.SessionContext;
import com.project.web.model.UserModel;
import com.project.web.rest.UserRESTClient;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.wildfly.swarm.ejb.EJBFraction;
import org.wildfly.swarm.undertow.WARArchive;
import org.wildfly.swarm.config.ejb3.AsyncService; 
import org.wildfly.swarm.config.ejb3.Cache; 
import org.wildfly.swarm.config.ejb3.StrictMaxBeanInstancePool; 
import org.wildfly.swarm.config.ejb3.ThreadPool; 
import org.wildfly.swarm.config.ejb3.TimerService; 
import org.wildfly.swarm.config.ejb3.service.FileDataStore; 
import org.wildfly.swarm.container.Container; 
 
import java.util.HashMap; 
import java.util.Map; 

public class Main {

    public static void main(String[] args) throws Exception {      
      
         final ClassLoader classLoader = Main.class.getClassLoader();
         System.out.println(" classLoader.toString() " + classLoader.toString());
         
        Container container = new Container();
        
        
        container.fraction(customDefaultFraction());
        
       //System.setProperty("swarm.http.port", ConfigResolver.getProjectStageAwarePropertyValue("httpPort"));
        System.setProperty("swarm.http.port", "9090");
        System.setProperty("swarm.https.port", "4444");
        System.setProperty("swarm.context.path", "/micro-web");
        System.setProperty("swarm.bind.address", "0.0.0.0");
        System.setProperty("swarm.port.offset", "0");
        System.setProperty("swarm.deployment.timeout", "100");        
        
        WARArchive deployment = ShrinkWrap.create(WARArchive.class);        
        deployment.addClass(UserModel.class);
        deployment.addClass(UserRESTClient.class);
        deployment.addClass(PostBean.class);
        deployment.addClass(MainDataBean.class);      
        deployment.addClass(LoginBean.class);      
        deployment.addClass(SessionContext.class);            
        
        deployment.staticContent();
        
        deployment.addPackage("com.project.web.auth");
        deployment.addPackage("com.project.web.model");
        deployment.addPackage("com.project.web.rest");
        deployment.addPackage("com.project.web.handlers");
        deployment.addPackage("com.project.web.properties");
        
        System.out.println();
       

        deployment.addAsWebResource(
                new ClassLoaderAsset("../webapp/index.xhtml", Main.class.getClassLoader()), "../webapp/index.xhtml");

        deployment.addAsWebResource(
                new ClassLoaderAsset("login.xhtml", Main.class.getClassLoader()), "login.xhtml");
        
        deployment.addAsWebResource(
                new ClassLoaderAsset("registration.xhtml", Main.class.getClassLoader()), "registration.xhtml");
        
          deployment.addAsWebResource(
                new ClassLoaderAsset("pages/profile.xhtml", Main.class.getClassLoader()), "pages/profile.xhtml");         
          

        deployment.addAsDirectory("contracts").addAllDependencies();
        deployment.addAsLibrary("contracts").addAllDependencies();
        deployment.addAsDirectory("pages").addAllDependencies();
        deployment.addAsLibrary("pages").addAllDependencies();

        deployment.addAsDirectory("contracts/default").addAllDependencies();
        deployment.addAsLibrary("contracts/default").addAllDependencies();

        deployment.addAsDirectory("contracts/public").addAllDependencies();
        deployment.addAsLibrary("contracts/public").addAllDependencies();

        deployment.addAsDirectory("WEB-INF/layout").addAllDependencies();
        deployment.addAsLibrary("WEB-INF/layout").addAllDependencies();

        deployment.addAsWebResource(
                new ClassLoaderAsset("WEB-INF/layout/content.xhtml", Main.class.getClassLoader()), "WEB-INF/layout/content.xhtml");

        deployment.addAsWebResource(
                new ClassLoaderAsset("WEB-INF/layout/footer.xhtml", Main.class.getClassLoader()), "WEB-INF/layout/footer.xhtml");

        deployment.addAsWebResource(
                new ClassLoaderAsset("WEB-INF/layout/footer_includes.xhtml", Main.class.getClassLoader()), "WEB-INF/layout/footer_includes.xhtml");

        deployment.addAsWebResource(
                new ClassLoaderAsset("WEB-INF/layout/header.xhtml", Main.class.getClassLoader()), "WEB-INF/layout/header.xhtml");

        deployment.addAsWebResource(
                new ClassLoaderAsset("WEB-INF/layout/left.xhtml", Main.class.getClassLoader()), "WEB-INF/layout/left.xhtml");
        
          deployment.addAsWebResource(
                new ClassLoaderAsset("WEB-INF/layout/right.xhtml", Main.class.getClassLoader()), "WEB-INF/layout/right.xhtml");

        deployment.addAsWebResource(
                new ClassLoaderAsset("WEB-INF/layout/nav.xhtml", Main.class.getClassLoader()), "WEB-INF/layout/nav.xhtml");

        deployment.addAsWebResource(
                new ClassLoaderAsset("contracts/default/template.xhtml", Main.class.getClassLoader()), "contracts/default/template.xhtml");
        deployment.addAsWebResource(
                new ClassLoaderAsset("contracts/public/template.xhtml", Main.class.getClassLoader()), "contracts/public/template.xhtml");

        deployment.addAsLibrary("resources").addAllDependencies();       

        deployment.addAsWebInfResource(
                new ClassLoaderAsset("WEB-INF/web.xml", Main.class.getClassLoader()), "web.xml");

        deployment.addAsWebInfResource(
                new ClassLoaderAsset("META-INF/context.xml", Main.class.getClassLoader()), "context.xml");

        deployment.addAsWebInfResource(
                new ClassLoaderAsset("WEB-INF/faces-config.xml", Main.class.getClassLoader()), "faces-config.xml");
        deployment.addAsWebInfResource(
                new ClassLoaderAsset("WEB-INF/beans.xml", Main.class.getClassLoader()), "beans.xml");

        deployment.addAllDependencies();

        container.start().deploy(deployment);

    }
    
    
    private static EJBFraction customDefaultFraction() {  
        Map threadPoolSettings = new HashMap<>(); 
        threadPoolSettings.put("time", "100"); 
        threadPoolSettings.put("unit", "MILLISECONDS"); 
 
        EJBFraction fraction = new EJBFraction(); 
        EJBFraction threadPool = fraction.defaultStatefulBeanAccessTimeout(5000L) 
                .defaultSingletonBeanAccessTimeout(5000L)
                .defaultSfsbCache("simple")
                .defaultSecurityDomain("other")
                .defaultMissingMethodPermissionsDenyAccess(true)
                .logSystemExceptions(true)
                //.defaultResourceAdapterName("${ejb.resource-adapter-name:activemq-ra.rar}")
                .strictMaxBeanInstancePool(new StrictMaxBeanInstancePool("slsb-strict-max-pool")
                        .maxPoolSize(50)
                        .timeout(5L)
                        .timeoutUnit(StrictMaxBeanInstancePool.TimeoutUnit.MINUTES))
                .strictMaxBeanInstancePool(new StrictMaxBeanInstancePool("mdb-strict-max-pool")
                        .maxPoolSize(50)
                        .timeout(5L)
                        .timeoutUnit(StrictMaxBeanInstancePool.TimeoutUnit.MINUTES))
                .cache(new Cache("simple"))
                .asyncService(new AsyncService().threadPoolName("default"))
                .timerService(new TimerService()
                        .threadPoolName("default")
                        .defaultDataStore("default-file-store")
                        .fileDataStore(new FileDataStore("default-file-store")
                                .path("timer-service-data")
                                .relativeTo("jboss.server.data.dir")))
                .threadPool(new ThreadPool("default")
                        .maxThreads(10)
                        .keepaliveTime(threadPoolSettings)); 
 
        return fraction; 
 
    } 
}


//http://www.programcreek.com/java-api-examples/index.php?source_dir=wildfly-swarm-master/ejb/api/src/main/java/org/wildfly/swarm/ejb/EJBFraction.java


