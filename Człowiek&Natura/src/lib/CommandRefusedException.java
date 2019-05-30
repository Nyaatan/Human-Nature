package lib;

public class CommandRefusedException extends Exception {
    public CommandRefusedException(String message){
        this.message = message;
    }

    String message;

    public String toString()
    {
        return "Command refused " + message;
    }
}
