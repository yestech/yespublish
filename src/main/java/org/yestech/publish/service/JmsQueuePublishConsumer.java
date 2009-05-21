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

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import static org.yestech.lib.xml.XmlUtils.fromXml;
import org.yestech.publish.IPublishConstant;
import org.yestech.publish.util.PublishUtils;
import org.yestech.publish.objectmodel.IArtifactMetaData;
import org.yestech.publish.objectmodel.IArtifact;
import org.yestech.publish.objectmodel.IFileArtifact;

import javax.jms.*;
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
//        if (message instanceof TextMessage) {
//            TextMessage textMessage = (TextMessage) message;
//            String fileLocation = "";
//            try {
//                String url = textMessage.getStringProperty(IPublishConstant.URL);
//                String fileName = textMessage.getStringProperty(IPublishConstant.FILE_NAME);
//                String xmlMetaData = textMessage.getText();
//                IArtifactMetaData metaData = fromXml(xmlMetaData);
//                fileLocation = url + fileName;
//                URL artifactUrl = new URL(fileLocation);
//                recieve(metaData, artifactUrl.openStream());
//            } catch (Exception e) {
//                logger.error("error retrieving file from location: " + fileLocation, e);
//            }
//        } else if (message instanceof ObjectMessage) {
        if (message instanceof ObjectMessage) {
            ObjectMessage objMessage = (ObjectMessage) message;
            String fileLocation = "";
            try {
                IArtifact artifact = (IArtifact) objMessage.getObject();
                if (PublishUtils.isFileArtifact(artifact)) {
                    IFileArtifact fileArtifact = (IFileArtifact) artifact;
                    String url = objMessage.getStringProperty(IPublishConstant.URL);
                    String fileName = objMessage.getStringProperty(IPublishConstant.FILE_NAME);
                    fileLocation = url + fileName;
                    URL artifactUrl = new URL(fileLocation);
                    fileArtifact.setStream(artifactUrl.openStream());
                    recieve(fileArtifact);
                } else {
                    recieve(artifact);
                }
            } catch (Exception e) {
                logger.error("error retrieving artifact..." , e);
            }
        }

    }

    @Override
    public void recieve(IArtifact artifact) {
        processor.process(artifact);
    }
}
