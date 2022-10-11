# Topic 9: RPC protocols and Data Formats
## Application Info
There are 2 example application implementing some RPC technologies:
1. gRPC + Protobuff
2. Avro

The first application consists of 3 services: Golang server + NodeJS and Java clients. 
Every component is in its own module sharing the same messaging.proto file for more convenient compilation \
The second application implements Avro to transfer messages via Kafka

## gRPC task
#### 1. Golang server
The server uses static proto file compilation via `protoc` compilator:

    protoc --go_out=. --go-grpc_out=. path/to/messaging.proto

Then start the server:

    go run server.go

#### 2. Java client
Java client uses static compilation with the Maven plugin. Do `mvn clean compile` before starting the client.

#### 3. NodeJS client
Opposite the previous modules, this client uses dynamic code generation.\
Install its dependencies:

    npm install

And lunch the app:

    node main.js

## Avro + Kafka task
There are simple Consumer and Producer. The domain is books, the declared sides exchange between each other. 
There are Avro scheme file of the domain and docker-compose file to wrap out the required environment.
The Avro scheme is compiled into the class files by the Avro Maven plugin while compiling the app.