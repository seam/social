#Seam Social

Provides CDI Beans and extensions to interact with major social network. 

Provides:

+ OAuth cconnectors to authentify to an OAuth provider
+ Support for Authentication for Twitter, LinkedIn and Facebook only right now 
+ Status update for Facebook Twitter and LinkedIn
+ Support for multi-account (multi-service and multi session for the same service)

Seam Social is independent of CDI implementation and fully portable between
Java EE 6 and Servlet environments enhanced with CDI. It can be also used 
with CDI in JSE (desktop application). It is build on top of [scribe-java
from fernandezpablo85](https://github.com/fernandezpablo85/scribe-java)

For more information, see the [Seam Social project page](http://seamframework.org/Seam3/Social).

##Building

    mvn clean install


##Using

The Web example app is quite simple and give a good idea of possibilities of Seam Social Framework.

Main steps to use it are :

+ Create an OAutConfigSettings bean thru Seam configuration (in bean.xml)

For instance :

    <beans xmlns="http://java.sun.com/xml/ns/javaee" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
     xmlns:s="urn:java:ee"
     xmlns:o="urn:java:org.jboss.seam.social.oauth:java:org.jboss.seam.social.twitter:java:org.jboss.seam.social.linkedin:java:org.jboss.seam.social.facebook">

     <o:OAuthServiceSettingsImpl>
         <s:modifies />
         <o:RelatedTo>Twitter</o:RelatedTo>
         <o:serviceName>Twitter</o:serviceName>
         <o:apiKey>FQzlQC49UhvbMZoxUIvHTQ</o:apiKey>
         <o:apiSecret>VQ5CZHG4qUoAkUUmckPn4iN4yyjBKcORTW0wnok4r1k
         </o:apiSecret>
         <o:callback>http://localhost:8080/social-web-client/callback.jsf
         </o:callback>
     </o:OAuthServiceSettingsImpl>
    </beans>

Api Key and Api secret is provided by the service you want to consume (here Twitter). You can use the values above since they're coming from "Seam Social" Twitter application. Callback depends on your application : it's the URL that will collect verifier 

+ Inject an OAuthService bean with one of the following ways :

Using the Interface of the service

    @Named
    @SessionScoped
    public class mySessionBean implements Serializable {
	    ...
        @Inject
        public Twitter twitter;
        ...
    }


or using the generic OAuthService with a Qualifier

    @Named
    @SessionScoped
    public class mySessionBean implements Serializable {
        ...
        @Inject
        @RelatedTo("Twitter")
        OAuthService twitter;
        ...
    }

+ Request the Authorization URL for the service and redirect the app to this url

If we continue on the same example :

    twitter.getAuthorizationUrl();

This call will return the URL needed to initiate connection to the service.

+ Store the verifier in OAuthService bean and init access token

When we return from the service connection we obtain a verifier that we need to store in the OAuthService and init the access token
In JSF we this like that

    <f:metadata>
        <f:viewParam name="#{mySessionBean.twitter.verifierParamName}"
            value="#{mySessionBean.twitter.verifier}"
                     required="true"
                     requiredMessage="Error with Twitter. Retry later"/>
        <f:event type="preRenderView"
            listener="#{mySessionBean.twitter.initAccessToken()}"/>
    </f:metadata>

+ After you can send calls to the service

Getting the Twitter user profile

    TwitterProfile user = twitter.getMyProfile();
    String fullName = user.getFullName();


##Testing

after building you can deploy the war generated in example/web-client/target
in a Java EE 6 container implementing web profile (tested with JBoss 6 but should work in glassfish too)
