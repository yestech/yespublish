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
import static org.yestech.lib.xml.XmlUtils.fromXml;
import org.yestech.publish.IPublishConstant;
import org.yestech.publish.objectmodel.IArtifactMetaData;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public class JmsQueuePublishConsumer implements IPublishConsumer, MessageListener {
    final private static Logger logger = LoggerFactory.getLogger(JmsQueuePublishConsumer.class);
    private IPublishProcessor processor;

    public IPublishProcessor getProcessor() {
        return processor;
    }

    @Required
    public void setProcessor(IPublishProcessor processor) {
        this.processor = processor;
    }

    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            try {
                String url = textMessage.getStringProperty(IPublishConstant.URL);
                String fileName = textMessage.getStringProperty(IPublishConstant.FILE_NAME);
                String xmlMetaData = textMessage.getText();
                IArtifactMetaData metaData = fromXml(xmlMetaData);
                URL artifactUrl = new URL(url + "/" + fileName);
                recieve(metaData, artifactUrl.openStream());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public void recieve(IArtifactMetaData metaData, InputStream artifact) {
        processor.process(metaData, artifact);
    }
}
