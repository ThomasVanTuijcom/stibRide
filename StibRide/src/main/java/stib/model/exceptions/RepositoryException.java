package stib.model.exceptions;

public class RepositoryException extends Exception{
    public RepositoryException(){
        super();
    }

    public RepositoryException(String msg){
        super(msg);
    }

    public RepositoryException(Exception exception){
        super(exception);
    }
}
