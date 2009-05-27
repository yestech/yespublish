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

import org.junit.Ignore;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import org.yestech.publish.objectmodel.ArtifactType;
import org.yestech.publish.objectmodel.ProducerArtifactType;
import org.yestech.publish.publisher.IPublisher;
import org.jmock.integration.junit4.JMock;
import org.jmock.Mockery;
import org.jmock.Expectations;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
@RunWith(JMock.class)
public class DefaultPublishProcessorUnitTest {
    private DefaultPublishProcessor processor;
    private Mockery context = new Mockery();

    @Before
    public void setUp() {
        processor = new DefaultPublishProcessor();
    }

    @Test
    public void testNullProcessorType() {
        final IPublisher publisher = context.mock(IPublisher.class, "publisher");
        List<IPublisher> publisherList = new ArrayList<IPublisher>();
        publisherList.add(publisher);
        processor.setProcessorList(publisherList);
        final Map<ArtifactType, IPublisher> map = processor.getPublishers();
        assertEquals(0, map.size());
    }
}
