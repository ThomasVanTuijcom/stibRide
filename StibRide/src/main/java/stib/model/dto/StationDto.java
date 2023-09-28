package stib.model.dto;

import java.util.ArrayList;
import java.util.List;

public class StationDto extends Dto<Integer>{
    private String name;

    public StationDto(Integer key, String name){
        super(key);
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
