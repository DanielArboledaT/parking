package co.com.poli.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterAuth {

    private static final String PATH = "auth/";

    @Bean
    public RouterFunction<ServerResponse> helloRoute(AuthHandler authHandler, Handler handler) {
        return RouterFunctions.route()
                .POST(PATH + "signup", authHandler::signUp)
                .POST(PATH + "login", authHandler::logIn)
                .POST("park", handler::park)
                .POST("removecar", handler::removeCar)
                .GET("getcar/{idpark}/{registration}", handler::getCar)
                .GET("listcars", handler::listCars)
                .PUT("", handler::getCar)
                .build();
    }

}
