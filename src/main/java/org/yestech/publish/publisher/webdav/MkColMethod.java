package org.yestech.publish.publisher.webdav;

import org.apache.commons.httpclient.HttpMethodBase;

/**
 * @author A.J. Wright
 */
public class MkColMethod extends HttpMethodBase {

    public MkColMethod(String uri) throws IllegalArgumentException, IllegalStateException {
        super(uri);
        setFollowRedirects(true);
    }

    @Override
    public String getName() {
        return "MKCOL";
    }
    
}
