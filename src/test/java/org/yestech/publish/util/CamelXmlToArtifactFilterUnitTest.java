package org.yestech.publish.util;

import org.junit.Before;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.Mock;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.*;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.yestech.publish.objectmodel.IArtifact;
import org.yestech.publish.objectmodel.IArtifactMetaData;
import org.yestech.publish.IPublishConstant;
import org.yestech.lib.xml.XmlUtils;

/**
 *
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CamelXmlToArtifactFilterUnitTest {
    private CamelXmlToArtifactFilter filter;

    @Mock
    private Exchange mockExchange;

    @Mock
    private Message mockMessage;

    @Before
    public void setUp() {
        filter = new CamelXmlToArtifactFilter();
    }

    @org.junit.Test
    public void testFilter() {
        when(mockExchange.getException()).thenReturn(null);
        when(mockExchange.getIn()).thenReturn(mockMessage);
        final TestArtifact testArtifact = new TestArtifact();
        final String xmlArtifact = XmlUtils.toXml(testArtifact);
        when(mockMessage.getBody(String.class)).thenReturn(xmlArtifact);

        final ArgumentCaptor<IArtifact> artifactArgumentCaptor = ArgumentCaptor.forClass(IArtifact.class);
        doNothing().when(mockMessage).setBody(artifactArgumentCaptor.capture());
        doNothing().when(mockMessage).setHeader(IPublishConstant.RAW_XML_ARTIFACT, xmlArtifact);
        filter.filter(mockExchange);
        final IArtifact iArtifact = artifactArgumentCaptor.getValue();
        verify(mockExchange).getException();
        verify(mockExchange).getIn();
        verify(mockMessage, times(2)).setBody(iArtifact);
        verify(mockMessage).setHeader(IPublishConstant.RAW_XML_ARTIFACT, xmlArtifact);
        assertEquals(testArtifact, iArtifact);
    }

    public static class TestArtifact implements IArtifact {
        private String value = "ture";
        private static final long serialVersionUID = -3139689016154552926L;

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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TestArtifact that = (TestArtifact) o;

            if (value != null ? !value.equals(that.value) : that.value != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return value != null ? value.hashCode() : 0;
        }
    }
}
