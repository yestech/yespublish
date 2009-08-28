package org.yestech.publish.publisher;

import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;
import org.junit.Test;
import org.yestech.lib.util.EncoderUtil;

/**
 * @author A.J. Wright
 */
public class BitgravityWebdavPublisherUnitTest {

    @Test
    public void testUri() throws URIException {
        String u = "http://webdav.bitgravity.com/images/77de68daecd823babbb58edb1c8e14d7106e83bb/41b0393f77bfda015023f010801c9588a4ef8d12_Photo 6.jpg";

        u = EncoderUtil.uriEncode(u);

        URI uri = new URI(u, true, "utf-8");

    }
}
