package nauvalatmaja.learning;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.runtime.annotations.RegisterForReflection;

@ApplicationScoped
@Named("greeting")
@RegisterForReflection(fields = false)
public class GreetingBean {
    @ConfigProperty(name = "greeting.message")
    String message;

    public String greet() {
        return message;
    }
}
