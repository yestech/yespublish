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

import org.yestech.publish.objectmodel.IArtifact;
import org.yestech.publish.objectmodel.IFileArtifact;
import org.yestech.publish.objectmodel.TerracottaPipeArtifact;
import static org.yestech.publish.util.PublishUtils.reset;
import org.yestech.publish.IPublishConstant;
import org.yestech.lib.xml.XmlUtils;
import org.springframework.beans.factory.annotation.Required;
import org.terracotta.modules.annotations.Root;
import org.terracotta.message.pipe.Pipe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
@SuppressWarnings("unchecked")
public class TerracottaPublishProducer implements IPublishProducer {
    final private static Logger logger = LoggerFactory.getLogger(TerracottaPublishProducer.class);
    @Root
    private Pipe pipe;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Pipe getPipe() {
        return pipe;
    }

    @Required
    public void setPipe(Pipe pipe) {
        this.pipe = pipe;
    }

    @Override
    public void send(IArtifact artifact) {
        try {
            pipe.put(XmlUtils.toXml(artifact));
        } catch (InterruptedException e) {
            logger.error("Error putting artifact onto pipe: " + artifact, e);
        }
    }

    @Override
    public void send(IFileArtifact artifact) {
        try {
            final File inputFile = artifact.getFile();
            if (inputFile == null) {
                throw new RuntimeException("file can't be null for file artifact");
            }
            final TerracottaPipeArtifact terractottaArtifact = TerracottaPipeArtifact.create(artifact);
            terractottaArtifact.getParameters().put(IPublishConstant.FILE_NAME, inputFile.getName());
            terractottaArtifact.getParameters().put(IPublishConstant.URL, url);
            reset(artifact);
            pipe.put(XmlUtils.toXml(terractottaArtifact));
        } catch (InterruptedException e) {
            logger.error("Error putting artifact onto pipe: " + artifact, e);
        }
    }
}