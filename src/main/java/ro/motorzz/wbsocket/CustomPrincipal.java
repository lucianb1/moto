package ro.motorzz.wbsocket;

import javax.security.auth.Subject;
import java.security.Principal;

public class CustomPrincipal implements Principal{

    private String name;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean implies(Subject subject) {
        return false;
    }

    public CustomPrincipal setName(String name) {
        this.name = name;
        return this;
    }
}
