package lib;

public class CommandRefusedException extends Exception {
    public CommandRefusedException(){}

    public String toString()
    {
        return "Command refused";
    }
}
