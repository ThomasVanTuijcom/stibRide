package stib.model.dto;

import java.util.Objects;

public class Dto<K> {
    private K key;

    public Dto(K key){
        if (key == null){
            throw new IllegalArgumentException("La clé est absente");
        }
        this.key = key;
    }

    public K getKey(){
        return this.key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dto<?> dto = (Dto<?>) o;
        return Objects.equals(key, dto.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
