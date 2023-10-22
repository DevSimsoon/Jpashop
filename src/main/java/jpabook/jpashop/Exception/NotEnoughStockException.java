package jpabook.jpashop.Exception;

public class NotEnoughStockException extends RuntimeException {

    public NotEnoughStockException() { super(); }
    public NotEnoughStockException(String message) { super(message);}

    public NotEnoughStockException(String message, Throwable cauese ) { super(message, cauese); }

    public NotEnoughStockException(Throwable cause ) { super(cause); }
}
