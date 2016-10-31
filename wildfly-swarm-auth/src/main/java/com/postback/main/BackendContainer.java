package com.postback.main;

import org.wildfly.swarm.container.Container;
import org.wildfly.swarm.datasources.DatasourcesFraction;
import org.wildfly.swarm.jpa.mysql.MySQLJPAFraction;

public class BackendContainer {

    private static final String MYSQL_OPTIONS = "autoReconnect=true&useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    public static Container newContainer() throws Exception {
        Container container = new Container();
        System.setProperty("swarm.http.port", "11000");        
        container.fraction(new DatasourcesFraction()
                .jdbcDriver("mysql", (d) -> {
                    d.driverClassName("com.mysql.jdbc.Driver");
                    d.xaDatasourceClass("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
                    d.driverModuleName("com.mysql");
                })
                .dataSource("MyDS", (ds) -> {
                    ds.driverName("mysql");
                    ds.connectionUrl("jdbc:mysql://localhost:3306/postback?" + MYSQL_OPTIONS);
                    ds.userName("root");
                    ds.password("root");                   
                })
        );
        container.fraction(new MySQLJPAFraction()
                .inhibitDefaultDatasource()
                .defaultDatasource("jboss/datasources/MyDS")
        );
        return container;
    }
}
