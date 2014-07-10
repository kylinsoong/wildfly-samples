package org.jboss.remoting3.test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

import org.jboss.remoting3.Channel;
import org.jboss.remoting3.Connection;
import org.jboss.remoting3.Endpoint;
import org.jboss.remoting3.MessageInputStream;
import org.jboss.remoting3.MessageOutputStream;
import org.jboss.remoting3.OpenListener;
import org.jboss.remoting3.Registration;
import org.jboss.remoting3.Remoting;
import org.jboss.remoting3.RemotingOptions;
import org.jboss.remoting3.remote.RemoteConnectionProviderFactory;
import org.jboss.remoting3.security.SimpleServerAuthenticationProvider;
import org.jboss.remoting3.spi.NetworkServerProvider;
import org.xnio.FutureResult;
import org.xnio.IoFuture;
import org.xnio.IoUtils;
import org.xnio.OptionMap;
import org.xnio.Options;
import org.xnio.Sequence;
import org.xnio.channels.AcceptingChannel;
import org.xnio.channels.ConnectedStreamChannel;

public class OutboundMessageCountTest {
	
	private static final int MAX_OUTBOUND_MESSAGES = 20;
	
	private Endpoint endpoint;
	private Registration registration;
	private AcceptingChannel<? extends ConnectedStreamChannel> streamServer;
	
	private Registration serviceRegistration;
	private Connection connection;
	
	private Channel clientChannel;
    private Channel serverChannel;
    
    private static final int BUFFER_SIZE = 8192;
    private static byte[] buffer;

	public OutboundMessageCountTest() throws IOException, URISyntaxException {
		endpoint = Remoting.createEndpoint("test", OptionMap.EMPTY);
		registration = endpoint.addConnectionProvider("remote", new RemoteConnectionProviderFactory(), OptionMap.create(Options.SSL_ENABLED, Boolean.FALSE));
		NetworkServerProvider networkServerProvider = endpoint.getConnectionProviderInterface("remote", NetworkServerProvider.class);
		SimpleServerAuthenticationProvider provider = new SimpleServerAuthenticationProvider();
        provider.addUser("bob", "test", "pass".toCharArray());
        streamServer = networkServerProvider.createServer(new InetSocketAddress("::1", 30123), OptionMap.create(Options.SASL_MECHANISMS, Sequence.of("CRAM-MD5")), provider, null);
	
        final FutureResult<Channel> passer = new FutureResult<Channel>();
        serviceRegistration = endpoint.registerService("org.jboss.test", new OpenListener(){

			public void channelOpened(Channel channel) {
				passer.setResult(channel);
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
        
        IoFuture<Connection> futureConnection = endpoint.connect(new URI("remote://[::1]:30123"), OptionMap.EMPTY, "bob", "test", "pass".toCharArray());
        connection = futureConnection.get();
        final OptionMap channelCreationOptions = OptionMap.create(RemotingOptions.MAX_OUTBOUND_MESSAGES, MAX_OUTBOUND_MESSAGES);
        IoFuture<Channel> futureChannel = connection.openChannel("org.jboss.test", channelCreationOptions);
        clientChannel = futureChannel.get();
        serverChannel = passer.getIoFuture().get();
	}
	
	public void testOutboundMessageSend() throws Exception {
		final int NUM_THREADS = 150;
        final ExecutorService executorService = Executors.newFixedThreadPool(NUM_THREADS);
        final Future<Throwable>[] futureFailures = new Future[NUM_THREADS];
        final Semaphore semaphore = new Semaphore(MAX_OUTBOUND_MESSAGES, true);
        try {
            // create and submit the tasks which will send out the messages
            for (int i = 0; i < NUM_THREADS; i++) {
                futureFailures[i] = executorService.submit(new MessageSender(this.clientChannel, semaphore));
            }
            int failureCount = 0;
            // wait for the tasks to complete and then collect any failures
            for (int i = 0; i < NUM_THREADS; i++) {
                final Throwable failure = futureFailures[i].get();
                if (failure == null) {
                    continue;
                }
                failureCount++;
                System.out.println("Thread#" + i + " failed with exception " + failure);
            }
        } finally {
            executorService.shutdown();
        }
	}
	
	public void destroy(){
		IoUtils.safeClose(connection);
		serviceRegistration.close();
		IoUtils.safeClose(streamServer);
        IoUtils.safeClose(endpoint);
        IoUtils.safeClose(registration);
	}

	public static void main(String[] args) throws Exception {
		OutboundMessageCountTest test = new OutboundMessageCountTest();
		test.testOutboundMessageSend();
		test.destroy();
	}
	
	private class MessageSender implements Callable<Throwable> {

        private Semaphore semaphore;
        private Channel channel;

        MessageSender(final Channel channel, final Semaphore semaphore) {
            this.semaphore = semaphore;
            this.channel = channel;
        }

        @Override
        public Throwable call() throws Exception {
            for (int i = 0; i < 3; i++) {
                try {
                    // get a permit before trying to send a message to the channel
                    this.semaphore.acquire();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return e;
                }
                MessageOutputStream messageOutputStream = null;
                try {
                    // now send a message
                    messageOutputStream = this.channel.writeMessage();
                    messageOutputStream.write("hello".getBytes());
                } catch (IOException e) {
                    return e;
                } finally {
                    // close the message
                    if (messageOutputStream != null) {
                        messageOutputStream.close();
                    }
                    // release the permit for others to use
                    this.semaphore.release();
                }
            }
            // no failures, return null
            return null;
        }
	}

}
