package org.wildfly.swarm.it.jsf;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.wildfly.swarm.it.AbstractIntegrationTest;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Bob McWhirter
 */
@RunWith(Arquillian.class)
public class JSFApplicationIT extends AbstractIntegrationTest {

    @Drone
    WebDriver browser;

    @Test
    public void testIt() {
        browser.navigate().to("http://localhost:8080/index.xhtml");
        assertThat(browser.getPageSource()).contains("WildFly Swarm Facelet");
        assertThat(browser.getPageSource()).contains("Hello from JSF");
        assertThat(browser.getPageSource()).contains("Powered by WildFly Swarm");
    }
}
