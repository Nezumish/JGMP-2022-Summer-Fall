#Topic 8: Messaging in Java
##Application Info
Example of implementing EDA in Java

Domain is technical events and its details.

The initial task considered a circular relation between the Impl module 
and the Messaging one, but I tried to separate them and encapsulate the 
Messaging module implementation from the Impl one.

## Modules
###1. 8-38-messaging-in-java-events-service-api:
Web-API of the events service to manage them.

Has the following endpoints:
1. `GET /events/`: returns all saved events
2. `GET /events/{eventId}`: returns the Event by its Id
3. `POST /events/`: creates the Event from the request body
4. `PUT /events/{eventId}`: updates the Event by its Id
5. `DELETE /events/{eventId}`: deletes the Event by its Id

###2. 8-38-messaging-in-java-events-service-api-async:
Asynchronous API to interact with messaging brokers.

Supports 3 actions on events: creating, updating and deleting. Each method accepts a callback function to perform on the Event object.

###3. 8-38-messaging-in-java-events-service-dto:
DTO objects used by the APIs.
Actually, it's only the Event class

###4. 8-38-messaging-in-java-events-service-impl:
The implementation of the API module to manage events.\
In my opinion, more logical implementation of the task: the controller delegates the request to the service,
forming a callback function to apply after messaging.\

The module uses SQL scripts via JDBC, because the scheme is simple and has no complex relation, in other way it would be easier to use an ORM

###5. 8-38-messaging-in-java-events-service-messaging:
The implementation of the asynchronous API module: the starter for autoconfiguring 
3 brokers: Kakfa, Rabbit MQ and Active MQ.\

Each broker supports a few basics properties: credentials (username and password), host, port. The last property is the selected broker name.\

The main problem is invoking a callback without losing benefits of asynchronous communication.
There were a few solutions I was thinking about:
1. gRPC. I can't afford to build another Spark, but it was interesting;
2. Consumers in the impl module. For some irrational reasons I really wanted to separate producers and consumers logic from the service one;
3. Not really the task, but works. Just use the completable future to save async communication;

After a lot of attempts to serialize a Java lambda, I've selected the third option.

