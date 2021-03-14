package com.github.xuqplus.hi.tomcat.a;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.servlets.DefaultServlet;
import org.apache.catalina.startup.Tomcat;
import org.junit.jupiter.api.Test;

@Slf4j
public class ATomcatTest {

    @Test
    void a() throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.getConnector();

        Context contextA = tomcat.addWebapp("/a", "./a");
        Context contextB = tomcat.addWebapp("/b", "./b");
        tomcat.addServlet("/a", "aServlet", new AServlet());
//        tomcat.addServlet("/b", "bServlet", new BServlet()); // equals to below
        Tomcat.addServlet(contextB, "bServlet", new BServlet());
        contextA.addServletMappingDecoded("", "aServlet");
        contextB.addServletMappingDecoded("", "bServlet");

        DefaultServlet defaultServlet = new DefaultServlet();
        Context contextC = tomcat.addContext("/c", "./c");
        Tomcat.addServlet(contextC, "defaultServlet", defaultServlet);
        contextC.addServletMappingDecoded("/", "defaultServlet");
        contextC.getServletContext();

        tomcat.init();
        tomcat.start();
        tomcat.getServer().await();
    }
}
