
public class Main {

    public static void main(String[] args) {

        System.out.println("Hello World!");

        MyConnection connection = new MyConnection();
        FrameClosing frameClosing = new FrameClosing(connection.conn);
    }

}
