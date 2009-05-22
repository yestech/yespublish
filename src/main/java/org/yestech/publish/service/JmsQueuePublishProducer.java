/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */

/*
 *
 * Author:  Artie Copeland
 * Last Modified Date: $DateTime: $
 */
package org.yestech.publish.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import static org.yestech.lib.xml.XmlUtils.toXml;
import org.yestech.publish.IPublishConstant;
import org.yestech.publish.util.PublishUtils;
import static org.yestech.publish.util.PublishUtils.reset;
import org.yestech.publish.objectmodel.IArtifactMetaData;
import org.yestech.publish.objectmodel.IArtifact;
import org.yestech.publish.objectmodel.IFileArtifact;

import javax.jms.*;
import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public class JmsQueuePublishProducer implements IPublishProducer {
    final private static Logger logger = LoggerFactory.getLogger(JmsQueuePublishProducer.class);

    private JmsTemplate jmsTemplate;
    private Queue queue;
    private String url;

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    @Required
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public Queue getQueue() {
        return queue;
    }

    @Required
    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void send(final IArtifact artifact) {
        jmsTemplate.send(queue, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                ObjectMessage message = session.createObjectMessage();
                message.setObject(artifact);
                return message;
            }
        });
    }

    @Override
    public void send(final IFileArtifact artifact) {
        final File inputFile = artifact.getFile();
        if (inputFile == null) {
            throw new RuntimeException("file can't be null for file artifact");
        }
        reset(artifact);
        jmsTemplate.send(queue, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                ObjectMessage message = session.createObjectMessage();
                message.setObject(artifact);
                message.setStringProperty(IPublishConstant.FILE_NAME, inputFile.getName());
                message.setStringProperty(IPublishConstant.URL, url);
                return message;
            }
        });
    }
}
