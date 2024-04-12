package hello.jdbc.connection;


public abstract class ConnectionConst { //상수만 선언해 둬서 이거 객체로 생성하지 말라고 abstract 선언
    public static final String URL = "jdbc:h2:tcp://localhost/~/test"; //h2접근 규약. 틀리면 안됨
    public static final String USERNAME = "sa";
    public static final String PASSWORD ="";
}
