package org.jboss.seam.social;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.social.cdi.RelatedTo;
import org.jboss.seam.social.oauth.*;
import org.jboss.seam.social.scribe.OAuthProviderScribe;
import org.jboss.seam.social.scribe.OAuthTokenScribe;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;


import javax.enterprise.inject.Instance;
import javax.enterprise.inject.UnsatisfiedResolutionException;
import javax.inject.Inject;

import java.io.FileNotFoundException;
import java.lang.annotation.Annotation;

import static org.mockito.Mockito.*;


/**
 * Created by IntelliJ IDEA.
 * User: antoine
 * Date: 13/10/11
 * Time: 17:22
 * To change this template use File | Settings | File Templates.
 */

@RunWith(Arquillian.class)
public class MultiServicesManagerImplTest {


        @Deployment
    public static Archive<?> createTestArchive() throws FileNotFoundException {
        /*
         * File beanFile = new File("src/twitter/resources/META-INF/beans.xml"); if (!beanFile.exists()) throw new
         * FileNotFoundException();
         */
        Archive<?> ret = ShrinkWrap
                .create(WebArchive.class, "social.war")
                .addClasses(MultiServicesManagerImpl.class)

                .addAsLibraries(
                        DependencyResolvers.use(MavenDependencyResolver.class)
                                .configureFrom("../settings.xml")
                                .loadMetadataFromPom("pom.xml")
                                .artifact("org.jboss.solder:solder-impl").artifact("org.scribe:scribe")
                                .resolveAs(GenericArchive.class));
        // ret.as(ZipExporter.class).exportTo(new File("mytest.war"), true);

        return ret;
    }

    @Inject
    MultiServicesManagerImpl msm;


    @Test(expected = UnsatisfiedResolutionException.class)
    public void testGetNewService() throws Exception {
        msm.getNewService("toto");

    }

    @Test
    public void testDestroyCurrentService() throws Exception {

    }
}
