## Consistent Hashing?

Before jumping into consistent hashing let's first revisit hashing.

### Hashing Revisited

Hashing is a technique of mapping one piece of data of some arbitrary size into another piece of data of fixed size, typically an integer, known as hash or hash code. In layman terms, it's a kind of BlackBox which always gives the same output for the same input. This BlackBox or a function (technically speaking) is usually used for mapping data/key to hash code known as a *hash function*

To illustrate, some hash function is written to map any arbitrary string to some number between 0...50. So any string of any size will always be mapped between 0 to 50. For example,

> hashcode = hash(key) modulo N, where N is the size

*24 = hash(**Artists**) % 50*

*42 = hash(**Actor**) % 50*

"*Artists*" and "*Actor*" are two string which produced hash code of 24 and 42 respectively by the hash function.

Since output (hash code) are far less in number than input strings, much different string would have the same hash code, a phenomenon known as a hash collision, which is handled by either hash chaining or open addressing. Good hash functions spread input values evenly into output range.

### Consistent Hashing

For the sake of example, let's say the Indian government starts an ambitious program to issue a unique-identity card to each and every Indian (Sounds like Aadhar card). During the first month, a million Indians were allotted a unique-identity which was stored in some data store. With time this number started growing at astronomical rate and government decided to distribute this data across different servers (N servers/nodes)

One possible solution to do so is to use mod-N hashing (As discussed above) on the unique-identity number and store user data on the corresponding server number (Between 0 to N)

![](https://miro.medium.com/max/60/1*ZsxmI0HzgoSvnyjwMF0T4Q.jpeg?q=20)

![](https://miro.medium.com/max/2390/1*ZsxmI0HzgoSvnyjwMF0T4Q.jpeg)

Distribution of Aadhar (unique-identity) data to 5 servers

Problems with the above approach?

Adding and removing servers from the cluster hashed with mod-N hashing requires *rehashing* of a massive amount of data to different servers/nodes. In distributed systems, a server can come and go at any point in time. Moving a humongous amount of data could result in cluster downtime as servers would not take any new requests till all data is migrated. Consistent hashing solves this excessive data migration issue in the event of server addition and deletion.

![](https://miro.medium.com/max/2742/1*bgwgnP9lxliGTB_efcCKyA.jpeg)

Adding one node/server to the existing 5 servers requires movement of 75% of data from one server to another in a cluster

![](https://miro.medium.com/max/2382/1*caubNX_RYVe6QZ84lwryJg.jpeg)

Removing one node/server to the existing 5 servers requires movement of 75% of data from one server to another in a cluster

Consistent Hashing to solve huge data migration

Instead of using mod-N hashing, where each key is mapped directly to a node/server. We can use the following configuration to map data to a node.

1. Placement of servers on the ring: Servers are placed in a circle (or in-ring of hash). Hash of servers is calculated(in this example) on their IP addresses and according to their hash value(which is between 0--360) are placed on the ring.


![](https://miro.medium.com/max/2672/1*4X8Gb8jHuBVTwXqu-6NiFA.jpeg)


![](https://miro.medium.com/max/1938/1*ByjR4kGUmYZs-AsPZmMfNA.jpeg)

Placing servers on the ring

2\. Placement of data on the ring: Hash of data is calculated on it's key (Aadhar-id) and they are also placed on this ring(Hash lies between 0--360)

![](https://miro.medium.com/max/1562/1*oGm6ivTVHNbY3gtpjFiZ3A.jpeg)

key distribution on the ring

3\. Determining the placement of data on Servers: To map which server a key belongs to or resided on, we do the following :\
We travel in a clockwise direction on the ring from the point key is located till the point we find a server. This is the server where this key belongs. For eg.\
key: 582657982345(65 degrees) belongs to server-2(75 degrees )and key:579423897328(216 degrees) belongs to server-1(220 degrees)

Effects of removing or adding servers on the ring

1.  Adding a new node to ring: If we add a new node/server to ring (Node 5 added), then we need to move only the data that resides between Node 1 and the degrees to which newly added node belongs. Rest of the data has no effect on the addition of the new node(the average amount of data moved is ~ number of keys/N )
2.  Removing a node from the ring: If we remove node 4 from the ring, then all the data that was getting mapped to server 4 will now map to server 2(the average amount of data moved is ~ number of keys/N )


![](https://miro.medium.com/max/2646/1*XFuVNdoBFmpxjASZ02tTlQ.jpeg)

![](https://miro.medium.com/max/2994/1*ODwL1onnljkCg29G5hn_RQ.jpeg)

Adding/Removing nodes from the ring

Thus, the addition/deletion of a node or server doesn't result in the movement of the colossal amount of data among servers with ring configuration.

Issues with above ring configuration

Above ring configuration indeed solved the problem of huge data migration, yet even this configuration has one problem: Non-uniform distribution of data/keys on the ring\
In the above configuration, node-2 was storing 2 keys out of total 4 keys while node-0 was not having any key. Thus above configuration was resulting in a lot of hot spots. Hot spots should be avoided in the distributed system as hotspots create performance issues.

How to solve the non-uniform distribution of data/keys?

Virtual nodes/replication is the solution for better distribution of keys and removing hotspots. The idea is very simple, instead of placing actual nodes on the ring, we placed virtual nodes there. Let's assume we have 720 virtual nodes on a circle (thus placing them at an interval of 0.5 degrees)

Now we can map each of these virtual nodes to actual/real nodes such that virtual nodes at different locations within the ring map to the same real node.

We can use some hash function which randomly maps virtual nodes to real nodes. This way we get rid of hot spots due to uneven distribution of data

![](https://miro.medium.com/max/60/1*ITR_TioT9yOVkehAiUZnZw.jpeg?q=20)

![](https://miro.medium.com/max/2318/1*ITR_TioT9yOVkehAiUZnZw.jpeg)

Virtual nodes and real nodes

Following java code uniformly distributes data on the ring :

``` java
import java.util.Collection;  
import java.util.SortedMap;  
import java.util.TreeMap;  
 
public class ConsistentHash<T> {  
 
 private final HashFunction hashFunction; 

 // How many times  each server should appear  on the  ring 
 private final int numberOfReplicas;  
 private final SortedMap<Integer, T> circle = new TreeMap<Integer, T>();  
 
 public ConsistentHash(HashFunction hashFunction, int numberOfReplicas,  
     Collection<T> nodes) {  
   this.hashFunction = hashFunction;  
   this.numberOfReplicas = numberOfReplicas;  
 
   for (T node : nodes) {  
     add(node);  
   }  
 }  
 // Adding a server
 public void add(T node) {  
   for (int i = 0; i <numberOfReplicas; i++) {  
     circle.put(hashFunction.hash(node.toString() + i), node);  
   }  
 }  
 // Removing  server
 public void remove(T node) {  
   for (int i = 0; i <numberOfReplicas; i++) {  
     circle.remove(hashFunction.hash(node.toString() + i));  
   }  
 }  
 
 // get Node for the object
 public T get(Object key) {  
   if (circle.isEmpty()) {  
     return null;  
   }  
   int hash = hashFunction.hash(key);  
   if (!circle.containsKey(hash)) { 
    // .tailMap get the keys greater than hash values 
     SortedMap<Integer, T> tailMap = circle.tailMap(hash);  
     // as tail map automatically rotate to the start of  the ring, if the map is null we will set the next key as circle.firstkey() else the next available key in tailMap
     hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();  
   }  
   return circle.get(hash);  
 }  
 
}
```
Where is consistent hashing used?

-   Couchbase automated data partitioning
-   Openstack's Object Storage Service Swift
-   Partitioning component of Amazon's storage system Dynamo
-   Data partitioning in Apache Cassandra


Benefits of using consistent hashing :

1.  Clusters of databases/caches can be scaled elastically with consistent hashing
2.  It facilitates data partitioning and data replication
3.  Helps in high availability of clusters