

For Leader election, membership, distributed datastructures (using  shards etc.) - we can use any of the below. Zookeeper seems to be  the best.


1) Hazelcast, by version 3.12, provides a `CPSubsystem` which is a `CP` system in terms of [CAP](https://en.wikipedia.org/wiki/CAP_theorem) and built using [Raft](https://raft.github.io/) consensus algorithm inside the Hazelcast cluster. `CPSubsytem` has a distributed lock implementation called `FencedLock` which can be used to implement a leader election.

For more information about `CPSubsystem` and `FencedLock` see;

-   [CP Subsystem Reference manual](https://docs.hazelcast.org/docs/3.12.2/manual/html-single/index.html#cp-subsystem)
-   [Riding the CP Subsystem](https://hazelcast.com/blog/riding-the-cp-subsystem/)
-   [Distributed Locks are Dead; Long Live Distributed Locks!](https://hazelcast.com/blog/long-live-distributed-locks/)

*Hazelcast versions before 3.12 are not suitable for leader election. As you already mentioned, it can choose availability during network splits, which can lead to election of multiple leaders.*

* Hazelcast clustering is used with TCP or IP or hostname or microservices  discovery. We have specific data structures for distributedmaps where we can keep the data and that will be shared across the servers. So none of the servers can see all the data at once (if there are more than one server in the cluster.)
    
2) Zookeeper doesn't suffer from the mentioned split-brain problem, you will not observe multiple leaders when network split happens. Zookeeper is built on [ZAB](https://ieeexplore.ieee.org/document/5958223?reload=true&arnumber=5958223) atomic broadcast protocol.

3) Etcd is using [Raft](https://raft.github.io/) consensus protocol. Raft and ZAB have similar consistency guarantees, which both can be used to implement a leader election process.

