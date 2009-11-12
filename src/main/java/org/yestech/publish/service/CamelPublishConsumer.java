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
import org.apache.camel.impl.DefaultMessage;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.yestech.publish.IPublishConstant;
import org.yestech.publish.objectmodel.IArtifact;
import org.yestech.publish.objectmodel.IFileArtifact;
import org.yestech.publish.objectmodel.TerracottaPipeArtifact;
import org.yestech.publish.util.PublishUtils;
import static org.yestech.publish.util.PublishUtils.translateArtifact;

import java.net.URL;
import java.util.Map;
import java.io.*;

/**
 * A camel based consumer that assumes the body of a {@link org.apache.camel.Message} is of type
 * {@link IArtifact}.  It assumes the message body is already of type {@link org.yestech.publish.objectmodel.IArtifact}.
 * This is used for example after the {@link org.yestech.publish.util.CamelXmlToArtifactFilter} has been applied.
 *
 * @author Artie Copeland
 * @version $Revision: $
 */
public class CamelPublishConsumer implements IPublishConsumer {

    final private static Logger logger = LoggerFactory.getLogger(CamelPublishConsumer.class);
    private IPublishProcessor processor;
    private Map<String, Object> headerParameters;
    private File tempDirectory;

    public IPublishProcessor getProcessor() {
        return processor;
    }

    public File getTempDirectory() {
        return tempDirectory;
    }

    @Required
    public void setTempDirectory(File tempDirectory) {
        this.tempDirectory = tempDirectory;
    }

    @Required
    public void setProcessor(IPublishProcessor processor) {
        this.processor = processor;
    }

    public Map<String, Object> getHeaderParameters() {
        return headerParameters;
    }

    public void setHeaderParameters(Map<String, Object> headerParameters) {
        this.headerParameters = headerParameters;
    }

    public void process(Exchange exchange) {
        final Throwable throwable = exchange.getException();
        if (throwable == null) {
            final Message message = exchange.getIn();
            String fileLocation = "";
            try {
                IArtifact artifact = translateArtifact(message.getBody(IArtifact.class));
                if (PublishUtils.isFileArtifact(artifact)) {
                    IFileArtifact fileArtifact = (IFileArtifact) artifact;
                    String url = message.getHeader(IPublishConstant.URL, String.class);
                    String fileName = message.getHeader(IPublishConstant.FILE_NAME, String.class);
                    fileLocation = url + fileName;
                    URL artifactUrl = new URL(fileLocation);
                    String fqn = PublishUtils.saveTempFile(tempDirectory, artifactUrl.openStream(), fileArtifact);
                    recieve(fileArtifact);
                    PublishUtils.removeTempFile(fqn);
                } else {
                    recieve(artifact);
                }
                if (headerParameters != null) {
                    for (Map.Entry<String, Object> entry : headerParameters.entrySet()) {
                        message.setHeader(entry.getKey(), entry.getValue());
                    }
                }
            } catch (Exception e) {
                logger.error("error retrieving artifact...", e);
                exchange.setException(e);
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
