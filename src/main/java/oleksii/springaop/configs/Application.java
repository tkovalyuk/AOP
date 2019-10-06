package oleksii.springaop.configs;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletSecurityElement;

public class Application implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(SpringConfigs.class);
        DispatcherServlet servlet = new DispatcherServlet(context);
        ServletRegistration.Dynamic springAOP = servletContext.addServlet("SpringAOP", servlet);
        springAOP.setLoadOnStartup(1);
        springAOP.addMapping("/");
    }
}
