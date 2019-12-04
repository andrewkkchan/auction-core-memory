# auction-core-memory: In memory consumer to demonstrate competing consumer in event sourcing at fastest speed to handle burst
## What it does?
This code base is the consumer part of event sourcing.  This code implements the consumption on in-process-memory instead of Redis and Database to illustrate competing consumers and eventual consistency can be done using in-process-memory.

## How about persistence?
Persistence is achieved through persisting the events in the event log (e.g., Kafka) --which is often speed up by using restore point of offset.  Database implementation can be easily produced as well using the same interfaces.

