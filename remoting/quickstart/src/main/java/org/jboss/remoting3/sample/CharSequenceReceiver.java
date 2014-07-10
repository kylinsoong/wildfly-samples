package org.jboss.remoting3.sample;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.jboss.remoting3.Channel;
import org.jboss.remoting3.Endpoint;
import org.jboss.remoting3.MessageInputStream;
import org.jboss.remoting3.OpenListener;
import org.jboss.remoting3.Remoting;
import org.jboss.remoting3.remote.RemoteConnectionProviderFactory;
import org.jboss.remoting3.security.SimpleServerAuthenticationProvider;
import org.jboss.remoting3.spi.NetworkServerProvider;
import org.xnio.IoUtils;
import org.xnio.OptionMap;
import org.xnio.Options;
import org.xnio.Sequence;
import org.xnio.channels.AcceptingChannel;
import org.xnio.channels.ConnectedStreamChannel;

public class CharSequenceReceiver {
	
	private static final int THREAD_POOL_SIZE = 100;
	private static final int BUFFER_SIZE = 8192;
    private static byte[] buffer;
	
	protected final Endpoint serverEndpoint;
	
	private AcceptingChannel<? extends ConnectedStreamChannel> server;
	
	public CharSequenceReceiver() throws IOException {
		serverEndpoint = Remoting.createEndpoint("connection-test-server", OptionMap.create(Options.WORKER_TASK_CORE_THREADS, THREAD_POOL_SIZE, Options.WORKER_TASK_MAX_THREADS, THREAD_POOL_SIZE));
		serverEndpoint.addConnectionProvider("remote", new RemoteConnectionProviderFactory(), OptionMap.create(Options.SSL_ENABLED, Boolean.FALSE));
		final NetworkServerProvider networkServerProvider = serverEndpoint.getConnectionProviderInterface("remote", NetworkServerProvider.class);
		SimpleServerAuthenticationProvider provider = new SimpleServerAuthenticationProvider();
		provider.addUser("bob", "test", "pass".toCharArray());
		server = networkServerProvider.createServer(new InetSocketAddress("localhost", 30123), OptionMap.create(Options.SASL_MECHANISMS, Sequence.of("CRAM-MD5")), provider, null);
		System.out.println("Server Created, " + server.getLocalAddress());
		
		serverEndpoint.registerService("test", new OpenListener(){

			public void channelOpened(Channel channel) {
				channel.receiveMessage(new Channel.Receiver(){

					public void handleError(Channel channel, IOException error) {
						
					}

					public void handleEnd(Channel channel) {
//						System.out.println(channel.getConnection().getRemoteEndpointName() + " ended");
					}

					public void handleMessage(Channel channel, MessageInputStream message) {
						try {
                            channel.receiveMessage(this);
                            buffer = new byte[BUFFER_SIZE];
                            while (message.read(buffer) > -1);
                            System.out.println("    Receive: " + new String(buffer));
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            IoUtils.safeClose(message);
                        }
					}
				});
			}

			public void registrationTerminated() {
				
			}}, OptionMap.EMPTY);
	}


	public static void main(String[] args) throws IOException {
		new CharSequenceReceiver();
	}

}
