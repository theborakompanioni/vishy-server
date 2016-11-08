package org.tbk.vishy.verticle;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.RoutingContext;

import static com.google.common.base.Preconditions.checkArgument;

public class HelloVerticle extends AbstractVerticle {

    private int httpPort;

    private static class HelloHandler implements Handler<RoutingContext> {
        @Override
        public void handle(RoutingContext routingContext) {
            routingContext.response().end("{\n" +
                    "  \"message\": \"Hello world\"\n" +
                    "}");
        }

        public static Handler<RoutingContext> create() {
            return new HelloHandler();
        }
    }

    public HelloVerticle(int httpPort) {
        checkArgument(httpPort > 0);
        this.httpPort = httpPort;
    }

    @Override
    public void start() throws Exception {
        Router router = Router.router(vertx);

        router.route("/hello")
                .method(HttpMethod.GET)
                .method(HttpMethod.POST)
                .handler(HelloHandler.create());

        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(this.httpPort);
    }
}
