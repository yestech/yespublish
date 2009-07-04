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
public class CamelXmlPublishConsumerUnitTest {
    private Mockery context = new Mockery();
    private CamelXmlPublishConsumer consumer;

    @Before
    public void setUp() {
        consumer = new CamelXmlPublishConsumer();
    }

    @Test
    public void testNonTerracottaArtifactProcess() {
        final Exchange exchange = context.mock(Exchange.class, "Exchange");
        final Message message = context.mock(Message.class, "Message");
        final IPublishProcessor publisher = context.mock(IPublishProcessor.class, "Publisher");


        context.checking(new Expectations() {
            {
                oneOf(exchange).getException();
                will(returnValue(null));
                oneOf(exchange).getIn();
                will(returnValue(message));
                oneOf(message).getBody(with(String.class));
                will(returnValue(XmlUtils.toXml(new TestArtifact())));
                oneOf(message).setBody(with(any(IArtifact.class)));
                oneOf(message).setBody(with(any(IArtifact.class)));
                oneOf(publisher).process(with(any(IArtifact.class)));
            }
        });
        consumer.setProcessor(publisher);
        consumer.process(exchange);
    }

    private class TestArtifact implements IArtifact {
        @Override
        public Object getArtifactIdentifier() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void setArtifactIdentifier(Object identifier) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public IArtifactMetaData getArtifactMetaData() {
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void setArtifactMetaData(IArtifactMetaData artifactMetaData) {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }

}
