---
Ref: 
---

We check for deduplication check while running on a graph/forest to remove any duplication of work.

Ex: 1. Web crawling -> the content of the html same although thats hosted/copied to other sites. In this case we don't want to repeat crawling.

Ex 2: Google drive/any other cloud store- while uploading files/photos - we can check  the  files checksum to find duplicates.

## Different process to remove deduplictaion

* Hashing technique:
    - Use a hash function and val mod(allowed size)

* Collisions will create a problem - so have a good hash function.

* We can also use bloom  filter for this purpose, it may give false  positives.


Checksum
--------

The checksum is a small sized datum, generated by applying a hash function on a large chunk of data. This hash function should have a minimum rate of collisions such that the hashes for different inputs are almost unique. That means, getting the same hash for different inputs is nearly impossible in practice.

These checksums are used to verify if a segment of data has been modified. The checksum (from a known hash function) of received data can be compared with the checksum provided by the sender to verify the purity of the segment. That is how all the data is verified in TCP/IP protocols.

In this way, if we generate two checksums for two files, we can declare that the two files aren't duplicates if the checksums are different. If the checksums are equal, we can claim that the files are identical, considering the fact that getting the same hash for two different files is almost impossible.

And many websites provide hashes of the files at the download pages, especially when the files are located on different servers. In such a scenario, the user can verify the authenticity of a file by comparing the provided hash with the one he generated using the downloaded file.

[![](https://www.clonefileschecker.com/blog/wp-content/uploads/2017/06/image1.png)](https://www.clonefileschecker.com/blog/wp-content/uploads/2017/06/image1.png)

There are [various hashing functions](https://en.wikipedia.org/wiki/List_of_hash_functions) that are used to generate checksums. Here are some popular ones.

| Name | Output size |
| MD5 | 128bits |
| SHA-1 | 160bits |
| SHA-256 | 256bits |
| SHA-512 | 512bits |

### MD5

MD5 is a popular hashing function which was initially used for cryptographic purposes. Even though this generates 128-bit output, many people no longer use it due to a host of vulnerabilities that later surfaced. However, it still can be used as a checksum to verify the authenticity of a file (or a data segment) to detect unintentional changes/corruptions.

### SHA-1

SHA 1 (Secure Hash Algorithm 1) is a cryptographic hashing function which generates a 160-bit output. This is no longer considered as secure after many attacks, web browsers already have stopped accepting SLL Certificates based on SHA-1.

The later versions of SHA checksums (SHA-256 and SHA-512) are more secure as they incorporate vast improvements and hence don't contain any vulnerabilities as oft he time this article was published.