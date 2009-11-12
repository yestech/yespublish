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
import org.yestech.publish.objectmodel.IArtifact;
import org.yestech.publish.objectmodel.IFileArtifact;
import org.yestech.publish.IPublishConstant;
import static org.yestech.publish.util.PublishUtils.reset;
import org.apache.commons.io.IOUtils;
import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.impl.DefaultMessage;
import org.apache.camel.impl.DefaultProducerTemplate;
import org.springframework.beans.factory.annotation.Required;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * A camel based producer that assumes the body of a {@link org.apache.camel.Message} is of type
 * {@link org.yestech.publish.objectmodel.IArtifact}.  It sends all message asynchronously to the startEndPoint Supplied
 *
 * @author Artie Copeland
 * @version $Revision: $
 */
public class CamelPublishProducer implements IPublishProducer {
    private CamelContext context;
    private String startEndPoint;

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public CamelContext getContext() {
        return context;
    }

    @Required
    public void setContext(CamelContext context) {
        this.context = context;
    }

    public String getStartEndPoint() {
        return startEndPoint;
    }

    @Required
    public void setStartEndPoint(String startEndPoint) {
        this.startEndPoint = startEndPoint;
    }

    @Override
    public void send(IArtifact artifact) {
        final DefaultProducerTemplate template = (DefaultProducerTemplate) context.createProducerTemplate();
        Endpoint endpoint = context.getEndpoint(startEndPoint);
        template.setDefaultEndpoint(endpoint);
        Exchange exchange = endpoint.createExchange();
        Message message = new DefaultMessage();
        message.setBody(artifact);
        exchange.setIn(message);
        template.asyncSend(endpoint, exchange);
    }

    @Override
    public void send(IFileArtifact artifact) {
        final File inputFile = artifact.getFile();
        if (inputFile == null) {
            throw new RuntimeException("file can't be null for file artifact");
        }
        final DefaultProducerTemplate template = (DefaultProducerTemplate) context.createProducerTemplate();
        Endpoint endpoint = context.getEndpoint(startEndPoint);
        template.setDefaultEndpoint(endpoint);
        Exchange exchange = endpoint.createExchange();
        Message message = new DefaultMessage();
        message.setHeader(IPublishConstant.FILE_NAME, inputFile.getName());
        message.setHeader(IPublishConstant.URL, url);
        message.setBody(artifact);
        exchange.setIn(message);
        template.asyncSend(endpoint, exchange);
        reset(artifact);
    }
}