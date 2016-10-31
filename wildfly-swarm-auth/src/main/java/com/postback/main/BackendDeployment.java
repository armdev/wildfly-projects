package com.postback.main;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.ClassLoaderAsset;
import org.wildfly.swarm.jaxrs.JAXRSArchive;

public class BackendDeployment {

    public static JAXRSArchive createDeployment() throws Exception {
        JAXRSArchive deployment = ShrinkWrap.create(JAXRSArchive.class);
        deployment.addPackage(Start.class.getPackage());
        deployment.addPackage("com.postback.main");
        deployment.addPackage("com.postback.cdi");
        deployment.addPackage("com.postback.entities");
        deployment.addPackage("com.postback.facades");
        deployment.addPackage("com.postback.resources");
        deployment.addPackage("com.postback.utils");
        deployment.addPackage("com.postback.dto");      
        deployment.addAsWebInfResource(
                new ClassLoaderAsset("META-INF/persistence.xml", Start.class.getClassLoader()), "classes/META-INF/persistence.xml");
        deployment.addAllDependencies();
        return deployment;
    }
}
