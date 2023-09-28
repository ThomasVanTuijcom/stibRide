package stib.model.dto;

public class FavoriDto extends Dto<String>{
    private Integer origin;
    private Integer destination;

    public FavoriDto(String key, Integer origin, Integer destination){
        super(key);
        this.origin = origin;
        this.destination = destination;
    }


    public Integer getOrigin() {
        return origin;
    }

    public Integer getDestination() {
        return destination;
    }


    @Override
    public String toString() {
        return super.getKey();
    }
}
