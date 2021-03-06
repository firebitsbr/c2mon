# Client API

The C2MON client API is written in Java and provides various service classes to interact with the server:

* `TagService`: The most important service class and most probably the only one you will need in the beginning. Provides methods to
search for and subscribe to tags.
* `AlarmService`: Provides methods for subscribing to active alarms or retrieving alarm information.
* `CommandService`: Provides methods for executing commands on source equipment.
* `ConfigurationService`: Provides methods for creating/reading/updating/removing pre-configured entities (such as tags or commands).
* `SupervisionService`: Provides methods for registering heartbeat listeners, to monitor server connection state.
* `SessionService`: Optional service to secure the command execution (requires implementing an `AuthenticationManager`).

## Including the API dependency

To use the API, you need to add a dependency on `c2mon-daq-core`. Include it in your project using Maven:

```xml
<dependency>
    <groupId>cern.c2mon.daq</groupId>
    <artifactId>c2mon-daq-core</artifactId>
    <version>__insert_version_here__</version>
</dependency>
```

Or using Gradle:

```
compile "cern.c2mon.daq:c2mon-daq-core:__insert_version_here__"
```

Remember to replace "__insert_version_here__" with a real version number, such as "1.8.0".

## Connecting to a C2MON server

By default, the client tries to connect to a server running at `localhost:61616`.

To override this, the client looks for the `c2mon.client.conf.url` property at startup. You can set it using a JVM
argument (`-Dc2mon.client.conf.url=...`) or as a system property (`System.setProperty("c2mon.client.conf.url", "...")`). It takes both `file://`
and `http://` protocol formats.


The file must contain at least the `c2mon.client.jms.url` property, which is the [URL of the JMS broker](http://activemq.apache.org/uri-protocols.html)
to which the C2MON server is listening for client connections. For example:

```bash
c2mon.client.jms.url=tcp://jms-broker-host:61616
```

## Using the API

### Spring Boot applications

Applications that use Spring Boot can benefit from integration with the C2MON client. Access to the client service classes can simply be acquired by
`@Autowiring` them, e.g.:

```java
@Autowired
private TagService tagService;
```

### Non-Spring applications

Regular Java applications must use the `C2monServiceGateway` to acquire access to the service classes. At application startup, initialise the client:

```java
C2monServiceGateway.startC2monClientSynchronous();
```

The startup can take several seconds, so to initialise the client asynchronously (letting you load other parts of your system in the meantime):

```java
C2monServiceGateway.startC2monClient();
```

Then the service classes can be acquired via the gateway, e.g. `TagService tagService = C2monServiceGateway.getTagService();`.
