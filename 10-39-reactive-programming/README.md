# Topic 10: Reactive Programming
## Application info
A simple program to demonstrate some reactive programming points in Java by means of Spring and Reactor Project.
It downloads a JSON of sports from Decathlon, parses and saves it asynchronously.

## Task description
There are few main concepts: Producer, Subscription and Subscriber.

The first one there - is the `DecathlonService` class. It downloads some data from the Decathlon API and 
supplies it as a Flux (the actual Producer implementation).

The Subscriber is the `SportModelSupplier` class. 
It subscribes on the Flux just after the creating and processes the incoming data with batch requests.\

There are more elegant ways to manage batch processing by Flux API:
1. Various` onBackpressureBuffer()` or `buffer()` functions to limit the incoming data pieces until processing;
2. Using `window(int maxSize)` function to define some smaller Flux windows from the origin one;
3. The Deprecated `limitRequest(long n)` replaced by `take(n, true)` in 3.4.x or `take(n)` in 3.5.0. Even those functions are going
to be removed soon.

There is the API to interact with the saved data:
1. `GET api/v1/sport`: returns all the fetched data;
2. `GET api/v1/sport?q=...`: return the sport entries containing the queried word in the name;
3. `POST api/v1/sport/{newSportName}`: creates a new sport entry with the given name.