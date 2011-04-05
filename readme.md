#Seam Social

Provides CDI Beans and extensions to interact with major social network. 

Provides:

+ OAuth cconnectors to authentify to an OAuth provider
+ Support for Authentication for Twitter, LinkedIn and Facebook only right now 
+ Status update for Twitter and LinkedIn
+ and more to come...

Seam Social is independent of CDI implementation and fully portable between
Java EE 6 and Servlet environments enhanced with CDI. It can be also used 
with Cdi in JSE (desktop application). It is build on top of [scribe-java
from fernandezpablo85](https://github.com/fernandezpablo85/scribe-java)

For more information, see the [Seam Social project page](http://seamframework.org/Seam3/Social).

##Building

   mvn clean install

##Testing

after building you can deploy the war generated in example/web-client/target
in a Java EE 6 container implementing web profile (tested with JBoss 6 but should work in glassfish too)
