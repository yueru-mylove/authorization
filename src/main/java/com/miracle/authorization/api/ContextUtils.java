package com.miracle.authorization.api;


import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Administrator
 * Created at 2021/9/22 15:35
 **/
public class ContextUtils {

    private static final ThreadLocal<Context> CONTEXT_THREAD_LOCAL = ThreadLocal.withInitial(MapContext::new);


    public static Context currentContext() {
        return CONTEXT_THREAD_LOCAL.get();
    }


    public static Mono<Context> reactiveContext() {
        return Mono.deferContextual(Mono::just)
                   .<Context>handle((context, sink) -> {
                       if (context.hasKey(Context.class)) {
                           sink.next(context.get(Context.class));
                       } else {
                           sink.complete();
                       }
                   })
                   .contextWrite(acceptContext(ctx -> {

                   }));
    }


    public static Function<reactor.util.context.Context, reactor.util.context.Context> acceptContext(Consumer<Context> consumer) {
        return context -> {
            if (!context.hasKey(Context.class)) {
                context = context.put(Context.class, new MapContext());
            }

            consumer.accept(context.get(Context.class));
            return context;
        };

    }
}
