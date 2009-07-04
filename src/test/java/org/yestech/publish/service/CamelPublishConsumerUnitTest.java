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
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.yestech.lib.xml.XmlUtils;
import org.yestech.publish.objectmodel.IArtifact;
import org.yestech.publish.objectmodel.IArtifactMetaData;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
@RunWith(JMock.class)
public class CamelPublishConsumerUnitTest {
    private Mockery context = new Mockery();
    private CamelPublishConsumer consumer;

    @Before
    public void setUp() {
        consumer = new CamelPublishConsumer();
    }

    @Test
    public void testNonTerracottaArtifactProcess() {
        final Exchange exchange = context.mock(Exchange.class, "Exchange");
        final Message message = context.mock(Message.class, "Message");
        final IArtifact artifact = context.mock(IArtifact.class, "Artifact");
        final IPublishProcessor publisher = context.mock(IPublishProcessor.class, "Publisher");


        context.checking(new Expectations() {
            {
                oneOf(exchange).getException();
                will(returnValue(null));
                oneOf(exchange).getIn();
                will(returnValue(message));
                oneOf(message).getBody(IArtifact.class);
                will(returnValue(artifact));
                oneOf(publisher).process(artifact);
            }
        });
        consumer.setProcessor(publisher);
        consumer.process(exchange);
    }
}