package org.yestech.publish.util;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.yestech.publish.objectmodel.IArtifact;
import org.yestech.publish.objectmodel.TerracottaPipeArtifact;
import org.yestech.publish.objectmodel.IFileArtifact;
import org.yestech.publish.IPublishConstant;
import static org.yestech.publish.util.PublishUtils.translateArtifact;
import org.yestech.lib.xml.XmlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

/**
 * This class takes a raw xml camel message and converts it into an {@link IArtifact} then puts it back into the Message Body
 * of the {@link Exchange}.  The original xml is then placed in the header under the key {@link IPublishConstant#RAW_XML_ARTIFACT}.
 *
 */
public class CamelXmlToArtifactFilter {
    final private static Logger logger = LoggerFactory.getLogger(CamelXmlToArtifactFilter.class);
    
    /**
     * Filter the exchange.
     *
     * @param exchange the Exchange that contains the Message.
     */
    public void filter(Exchange exchange) {
        final Throwable throwable = exchange.getException();
        if (throwable == null) {
            final Message message = exchange.getIn();
            try {
                String xmlArtifact = message.getBody(String.class);
                final IArtifact tempArtifact = (IArtifact) XmlUtils.fromXml(xmlArtifact);
                message.setBody(tempArtifact);
                String url = "";
                String fileName = "";
                if (PublishUtils.isTerracottaArtifact(tempArtifact)) {
                    TerracottaPipeArtifact terracottaArtifact = (TerracottaPipeArtifact) tempArtifact;
                    url = terracottaArtifact.getParameter(IPublishConstant.URL);
                    fileName = terracottaArtifact.getParameter(IPublishConstant.FILE_NAME);

                }
                IArtifact artifact = translateArtifact(tempArtifact);
                message.setBody(artifact);
                message.setHeader(IPublishConstant.RAW_XML_ARTIFACT, xmlArtifact);
            } catch (Exception e) {
                logger.error("error retrieving artifact...", e);
                exchange.setException(e);
            }
        } else {
            logger.error("error in the exchange", throwable);
        }
    }
}
