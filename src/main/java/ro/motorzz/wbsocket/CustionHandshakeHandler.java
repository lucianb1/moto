package ro.motorzz.wbsocket;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import ro.motorzz.security.TokenAuthentication;

import java.security.Principal;
import java.util.Map;

public class CustionHandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
                                      Map<String, Object> attributes) {
        ((TokenAuthentication) SecurityContextHolder.getContext().getAuthentication()).getToken();
        CustomPrincipal principal = new CustomPrincipal();
        principal.setName(((TokenAuthentication) SecurityContextHolder.getContext().getAuthentication()).getToken());
        return principal;
    }
}
