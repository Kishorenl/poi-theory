
Non-functional requirements

- scalability
    - vertical scaling for MySQL
    - horizontal scaling
        - cassandra / HBase (time series and columnar tables - high reads and writes)
        - Mongo / coucch (Document DBs)
        - Clustered envs (hazelcast/docker-compose)
        - Kafka with Zookeeper
        - clustered redis
- Reliability/Fault tolerance
    - Backup
    - Availability is a bit different from Reliability. Availability is the uptime (99.99%) but reliability is how secure the system is. Ex: zoom is available but there are some issues with security which causes problem to reliability. But, more reliability results in more availability!!
- Security

- Reliability & Manageability

- Performance




Start with data model and how you want to store data/process events etc.

Data Model:

Where we want to store ? SQL/NoSQL
    NoSQL: 
    - Eventual consistency
    - Tuneable consistency
    
    Types of NoSQL:
    - Column
        - cassandra - wide column -> This uses gossip protocol
        - Hbase  - Master based architecture
    - key value
    - document
        - Mongo DB - leader based architecture
    - graph DBs


Stream processing:
    check pointing:
    



