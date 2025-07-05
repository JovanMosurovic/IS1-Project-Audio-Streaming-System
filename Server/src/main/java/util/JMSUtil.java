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
    
    // Input queues
    @Resource(lookup = "subsystem1Queue")
    private Queue subsystem1Queue;
    
    @Resource(lookup = "subsystem2Queue")
    private Queue subsystem2Queue;
    
    @Resource(lookup = "subsystem3Queue")
    private Queue subsystem3Queue;
    
    // Response queues - separate for each subsystem
    @Resource(lookup = "subsystem1ResponseQueue")
    private Queue subsystem1ResponseQueue;
    
    @Resource(lookup = "subsystem2ResponseQueue")
    private Queue subsystem2ResponseQueue;
    
    @Resource(lookup = "subsystem3ResponseQueue")
    private Queue subsystem3ResponseQueue;
    
    private static final Logger LOGGER = Logger.getLogger(JMSUtil.class.getName());
    
    private void flushResponseQueue(Queue responseQueue, String subsystemName) {
        JMSContext flushContext = connectionFactory.createContext();
        JMSConsumer flushConsumer = flushContext.createConsumer(responseQueue);
        Message msg;
        int flushedCount = 0;
        while ((msg = flushConsumer.receiveNoWait()) != null) {
            System.out.println("[INFO] Server - Flushed stale response from " + subsystemName + ": " + msg.getClass().getSimpleName());
            flushedCount++;
        }
        if (flushedCount > 0) {
            System.out.println("[INFO] Server - Total flushed responses from " + subsystemName + ": " + flushedCount);
        }
        flushConsumer.close();
        flushContext.close();
    }
    
    public Object sendCommandToSubsystem1(String command) {
        return sendCommand(subsystem1Queue, subsystem1ResponseQueue, command, "Subsystem1");
    }
    
    public Object sendCommandToSubsystem2(String command) {
        return sendCommand(subsystem2Queue, subsystem2ResponseQueue, command, "Subsystem2");
    }
    
    public Object sendCommandToSubsystem3(String command) {
        return sendCommand(subsystem3Queue, subsystem3ResponseQueue, command, "Subsystem3");
    }
    
    private Object sendCommand(Queue inputQueue, Queue responseQueue, String command, String subsystemName) {
        try {
            flushResponseQueue(responseQueue, subsystemName);
            
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            
            // Send text command
            TextMessage message = context.createTextMessage();
            message.setText(command);
            message.setJMSReplyTo(responseQueue);
            
            producer.send(inputQueue, message);
            System.out.println("[INFO] Server sent command to " + subsystemName + ": " + command);
            
            // Wait for object response from specific queue
            JMSConsumer consumer = context.createConsumer(responseQueue);
            Message response = consumer.receive(10000);
            
            if (response instanceof ObjectMessage) {
                Object entity = ((ObjectMessage) response).getObject();
                System.out.println("[INFO] Server received entity from " + subsystemName + ": " + entity.getClass().getSimpleName());
                return entity;
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