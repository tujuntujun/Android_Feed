package transsocket;

public class TcpClientFactory {

    private TcpClientFactory(){}

    public static TcpClient createTcpClient() {
        return new TcpClient();
    }
}
