/**
 *  Created by: Fredrik Öberg
 *  Date of creation: 201101
 *  Latest update: -
 *
 */

import java.io.*;
import java.net.Socket;


/**
 * Handles the sending of messages in the chat client. Implements runnable so
 * the class operates in its own thread.
 */
public class MessageSender implements Runnable{

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter sender;
    private String message;

    /**
     * Creates an instance of the MessageSender class.
     * @param socket is the socket through which messages are sent.
     */
    public MessageSender(Socket socket){
        this.socket = socket;
    }

    /**
     * Is initiated when the threads start() function is called upon. Handles the output from, the
     * chat client. Takes messages from standard in and sends it to the connected chat server. If the message
     * "quit" is entered the client exits the chat session by closing the connected socket.
     */
    @Override
    public void run() {
        try {
            this.sender = new PrintWriter(this.socket.getOutputStream(), true);
            this.reader = new BufferedReader(new InputStreamReader((System.in)));

            this.sender.println(this.reader.readLine());

            do{
                this.message = this.reader.readLine();
                this.sender.println(message);
            }while(!this.message.equals("quit"));
        } catch (IOException ex) {
            System.out.println("Error getting input stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
