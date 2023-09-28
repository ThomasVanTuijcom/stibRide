package stib.model.dto;

import stib.model.Pair;

import java.util.Map;

public class StopsDto extends Dto<Pair<Integer, Integer>>{
    private Integer order;

    public StopsDto(Pair<Integer, Integer> key, Integer order){
        super(key);
        this.order = order;
    }

    public Integer getOrder() {
        return order;
    }
}
