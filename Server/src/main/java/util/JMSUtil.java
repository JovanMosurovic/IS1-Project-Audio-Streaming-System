package util;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.TextMessage;

@ApplicationScoped
public class JMSUtil {
    
    @Resource(lookup = "jms/__defaultConnectionFactory")
    private ConnectionFactory connectionFactory;
    
    @Resource(lookup = "subsystem1Queue")
    private Queue subsystem1Queue;
    
    @Resource(lookup = "subsystem2Queue")
    private Queue subsystem2Queue;
    
    @Resource(lookup = "subsystem3Queue")
    private Queue subsystem3Queue;
    
    @Resource(lookup = "responseQueue")
    private Queue responseQueue;
    
    private static final Logger LOGGER = Logger.getLogger(JMSUtil.class.getName());
    
    public Object sendCommandToSubsystem1(String command) {
        return sendCommand(subsystem1Queue, command, "Subsystem1");
    }
    
    public Object sendCommandToSubsystem2(String command) {
        return sendCommand(subsystem2Queue, command, "Subsystem2");
    }
    
    public Object sendCommandToSubsystem3(String command) {
        return sendCommand(subsystem3Queue, command, "Subsystem3");
    }
    
    // Core method - send command, get object (entity) back
    private Object sendCommand(Queue targetQueue, String command, String subsystemName) {
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            
            // Send text command
            TextMessage message = context.createTextMessage();
            message.setText(command);
            message.setJMSReplyTo(responseQueue);
            
            producer.send(targetQueue, message);
            System.out.println("[INFO] Server sent command to " + subsystemName + ": " + command);
            
            // Wait for object response (entity)
            JMSConsumer consumer = context.createConsumer(responseQueue);
            Message response = consumer.receive(10000); // 10 second timeout
            
            if (response instanceof ObjectMessage) {
                Object entity = ((ObjectMessage) response).getObject();
                System.out.println("[INFO] Server received entity from " + subsystemName + ": " + entity.getClass().getSimpleName());
                return entity; // Return entity directly
            }
            
            System.err.println("[ERROR] Server - No object response received from " + subsystemName);
            return null;
            
        } catch (JMSException ex) {
            System.err.println("[ERROR] Server JMS error with " + subsystemName + ": " + ex.getMessage());
            LOGGER.log(Level.SEVERE, "JMS error", ex);
            return null;
        }
    }
}