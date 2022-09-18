package com.rntgroup.messaging.in.java.messaging.configuration.converter;

import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Objects;

/**
 * The proxy-converter for {@link MappingJackson2MessageConverter} to
 * prevent an exception when there is no Object Type attached to JMS Message.
 * This converter fills messageTypeAttribute of received JMS Messages
 * before handling them with inner {@link MappingJackson2MessageConverter}.
 */
public class JmsWorkaroundJsonConverter implements MessageConverter {

    private static final String TYPE_ATTRIBUTE = "_type";

    private final MappingJackson2MessageConverter innerJsonMessageConverter;

    public JmsWorkaroundJsonConverter(MappingJackson2MessageConverter innerJsonMessageConverter) {
        this.innerJsonMessageConverter = innerJsonMessageConverter;
    }

    @Override
    public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
        var message = innerJsonMessageConverter.toMessage(object, session);
        message.setJMSType(object.getClass().getTypeName());

        return message;
    }

    private String extractTypeFrom(Message message) throws JMSException {
        String type = message.getJMSType();
        if (notPresent(type)) {
            type = message.getStringProperty(TYPE_ATTRIBUTE);
        }

        return type;
    }

    private boolean notPresent(String str) {
        return Objects.isNull(str) || str.isBlank();
    }

    @Override
    public Object fromMessage(Message message) throws JMSException, MessageConversionException {
        String type = extractTypeFrom(message);
        message.setJMSType(type);

        return innerJsonMessageConverter.fromMessage(message);
    }

}