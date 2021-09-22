package com.miracle.authorization.api;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

/**
 * @author Administrator
 * Created at 2021/9/22 14:07
 **/
public interface DimensionProvider {


    Flux<? extends DimensionType> getAllType();


    Flux<? extends Dimension> getDimensionByUserId(String userId);


    Mono<? extends Dimension> getDimensionById(DimensionType type, String id);


    Flux<String> getUserIdByDimensionId(String dimensionId);


    default Flux<? extends Dimension> getDimensionsById(DimensionType type, Collection<String> idList) {

        return Flux.fromIterable(idList)
                   .flatMap(id -> this.getDimensionById(type, id));
    }




}
