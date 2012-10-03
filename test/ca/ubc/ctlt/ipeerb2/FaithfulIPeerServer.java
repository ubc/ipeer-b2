package ca.ubc.ctlt.ipeerb2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Faithful iPeer server as it's always return the results we defined. Define
 * the HTTP requests/responses in the context
 * 
 * @author compass
 * 
 */
public class FaithfulIPeerServer {
	private final static Logger logger = LoggerFactory
			.getLogger(FaithfulIPeerServer.class);
	private final static String host = "127.0.0.1";
	private final static int port = 8889;
	private ServerSocket socket;

	public static void main(String[] args) throws Exception {
		FaithfulIPeerServer server = new FaithfulIPeerServer();
		if (args.length >= 1) {
			if ("start".equals(args[0])) {
				server.run();
			} else if ("stop".equals(args[0])) {
				server.stop();
			}
		}
	}

	public FaithfulIPeerServer() {
	}

	public void run() {
		try {
			socket = new ServerSocket(port, 1, InetAddress.getByName(host));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		AbstractApplicationContext ctx = new ClassPathXmlApplicationContext(
				"/ca/ubc/ctlt/ipeerb2/webservice/faithfulIPeerServer-context.xml");
		// shutdown the spring context before exit so that we can shutdown jetty
		// as well
		ctx.registerShutdownHook();
		
		Socket accept;
		try {
			logger.info("Faithful iPeer server started. Listening to the command...");
			accept = socket.accept();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					accept.getInputStream()));
			reader.readLine();
			logger.info("Stopping faithful iPeer server");
			accept.close();
			socket.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void stop() throws Exception {
		Socket s = new Socket(InetAddress.getByName(host), port);
		OutputStream out = s.getOutputStream();
		logger.info("Sending stop request");
		out.write(("\r\n").getBytes());
		out.flush();
		s.close();
	}
}
