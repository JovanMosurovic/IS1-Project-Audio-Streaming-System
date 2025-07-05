package subsystem1;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;

public abstract class AbstractSubsystem {
    
    private ConnectionFactory connectionFactory;
    protected Queue inputQueue;
    private boolean running = true;
    private static final Logger LOGGER = Logger.getLogger(AbstractSubsystem.class.getName());
    
    public void start() {
        if (inputQueue == null) {
            System.err.println("[ERROR] " + getSubsystemName() + " - Input queue not set");
            throw new IllegalStateException("Input queue not set");
        }
        
        try {
            Context ctx = new InitialContext();
            connectionFactory = (ConnectionFactory) ctx.lookup("jms/__defaultConnectionFactory");
            
            JMSContext context = connectionFactory.createContext();
            JMSConsumer consumer = context.createConsumer(inputQueue);
            JMSProducer producer = context.createProducer();
            
            System.out.println("[INFO] " + getSubsystemName() + " started listening for messages");
            
            while (running) {
                try {
                    Message message = consumer.receive(1000);
                    
                    if (message instanceof TextMessage) {
                        handleTextCommand((TextMessage) message, producer);
                    }
                    
                } catch (JMSException ex) {
                    System.err.println("[ERROR] " + getSubsystemName() + " JMS Error: " + ex.getMessage());
                    LOGGER.log(Level.SEVERE, getSubsystemName() + " JMS Error", ex);
                }
            }
            
        } catch (Exception e) {
            System.err.println("[ERROR] " + getSubsystemName() + " Error in message processing: " + e.getMessage());
            LOGGER.log(Level.SEVERE, getSubsystemName() + " Error in message processing", e);
        }
    }
    
    // Handle text command and return entity as object
    private void handleTextCommand(TextMessage textMessage, JMSProducer producer) throws JMSException {
        String command = textMessage.getText();
        System.out.println("[INFO] " + getSubsystemName() + " received command: " + command);
        
        Object result = handleCommand(command);
        
        // Send result back as object message
        Destination replyTo = textMessage.getJMSReplyTo();
        if (replyTo != null) {
            ObjectMessage responseMessage = connectionFactory.createContext().createObjectMessage();
            responseMessage.setObject((java.io.Serializable) result);
            producer.send(replyTo, responseMessage);
            System.out.println("[INFO] " + getSubsystemName() + " sent response");
            System.out.println("------------------------------------------------------");
        }
    }
    
    public void stop() {
        running = false;
        cleanup();
        System.out.println("[INFO] " + getSubsystemName() + " stopped");
    }
    
    // Abstract methods
    public abstract Object handleCommand(String command);
    public abstract String getSubsystemName();
    protected abstract void cleanup();
}