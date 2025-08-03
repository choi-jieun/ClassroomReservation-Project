package backend;

//정수만으로 구성되어 있지 않거나 7자리가 아닐 때 발생 예외를 처리하는 프로그램
public class InvalidLoginIdException extends Exception {
    public InvalidLoginIdException(String message) {
        super(message);
    }
}