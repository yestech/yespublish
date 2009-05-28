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

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.yestech.publish.IPublishConstant;
import org.yestech.publish.objectmodel.IArtifact;
import org.yestech.publish.objectmodel.IFileArtifact;
import org.yestech.publish.util.PublishUtils;

import java.net.URL;

/**
 * A camel based processor that assumes the body of a {@link org.apache.camel.Message} is of type
 * {@link IArtifact}.
 *
 * @author Artie Copeland
 * @version $Revision: $
 */
public class CamelPublishConsumer implements IPublishConsumer, Processor {

    final private static Logger logger = LoggerFactory.getLogger(CamelPublishConsumer.class);
    private IPublishProcessor processor;

    public IPublishProcessor getProcessor() {
        return processor;
    }

    @Required
    public void setProcessor(IPublishProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        final Throwable throwable = exchange.getException();
        if (throwable == null) {
            final Message message = exchange.getIn();
            String fileLocation = "";
            try {
                IArtifact artifact = message.getBody(IArtifact.class);
                if (PublishUtils.isFileArtifact(artifact)) {
                    IFileArtifact fileArtifact = (IFileArtifact) artifact;
                    String url = message.getHeader(IPublishConstant.URL, String.class);
                    String fileName = message.getHeader(IPublishConstant.FILE_NAME, String.class);
                    fileLocation = url + fileName;
                    URL artifactUrl = new URL(fileLocation);
                    fileArtifact.setStream(artifactUrl.openStream());
                    recieve(fileArtifact);
                } else {
                    recieve(artifact);
                }
            } catch (Exception e) {
                logger.error("error retrieving artifact...", e);
            }
        } else {
            logger.error("error in the exchange", throwable);
        }
    }

    @Override
    public void recieve(IArtifact artifact) {
        processor.process(artifact);
    }
}
