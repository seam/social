package org.jboss.seam.social;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 * Created by IntelliJ IDEA.
 * User: antoine
 * Date: 29/09/11
 * Time: 16:51
 * To change this template use File | Settings | File Templates.
 */
public class JsonMapperTest {
    JsonMapper jm;

    @Before
    public void setUp() {
        jm = new JsonMapper();
    }

    @Test(expected = NullPointerException.class)
    public void testReadNullResponse() {
        jm.readValue(null, Object.class);
    }


    @Test(expected = SeamSocialException.class)
    public void testReadEmptyBody() {
        HttpResponse resp=mock(HttpResponse.class);
        when(resp.getBody()).thenReturn("");


        jm.readValue(resp, Object.class);
    }

    @Test(expected = NullPointerException.class)
    public void testReadNullBody() {
        HttpResponse resp=mock(HttpResponse.class);
        when(resp.getBody()).thenReturn(null);


        jm.readValue(resp, Object.class);
    }


    @Test(expected = NullPointerException.class)
    public void testRegisterNullModule() {

        jm.registerModule(null);

    }
}
