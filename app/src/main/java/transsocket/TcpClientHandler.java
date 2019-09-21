package transsocket;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;



public class TcpClientHandler extends SimpleChannelInboundHandler<Object> {

    private final String SUCCEED = "1";
    private final String FAILED = "0";
    private final String REC = "TCP Client接收的消息:";

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (SUCCEED.equals(msg)){
            System.out.println(REC + "成功");
        }else if(FAILED.equals(msg)){
            System.out.println(REC + "失败");
        }else {
            System.out.println(REC + "Server回传的数据异常");
        }
    }

}
