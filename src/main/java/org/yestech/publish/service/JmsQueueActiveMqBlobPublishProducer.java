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

import org.yestech.publish.objectmodel.IArtifactMetaData;
import org.yestech.publish.IPublishConstant;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.apache.activemq.BlobMessage;
import org.apache.activemq.ActiveMQSession;

import javax.jms.Queue;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.JMSException;
import java.io.InputStream;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public class JmsQueueActiveMqBlobPublishProducer implements IPublishProducer {

    private JmsTemplate jmsTemplate;
    private Queue queue;

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public Queue getQueue() {
        return queue;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }

    @Override
    public void send(final IArtifactMetaData metaData, final InputStream artifact) {
        jmsTemplate.send(queue, new MessageCreator()
        {
            public Message createMessage(Session session) throws JMSException {
                BlobMessage message = ((ActiveMQSession) session).createBlobMessage(artifact);
                message.setObjectProperty(IPublishConstant.META_DATA_IDENTIFIER, metaData);
                return message;
            }
        });

    }
}
