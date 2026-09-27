package exception;
public class Exceptions { public static class DuplicateId extends Exception{public DuplicateId(String m){super(m);}} public static class NotFound extends Exception{public NotFound(String m){super(m);}} public static class InvalidData extends Exception{public InvalidData(String m){super(m);}} }
