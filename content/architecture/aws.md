
amazon cloud post -> on premise aws

Infrastructure as Code --> Cloud Formation -- json/yaml --> Create the whole stack

Regions --> Avalability Zones 
    AZ to  AZ -> always synchronous replictaion
    AZs division is based on fault tolerance/fault isolated
        AZ will be devided into different datacenter

Edge locations for CDN (aws cloud front)
    -  infrastructure.aws

Compute services
    - EC2
        - Break a particular server to smaller VMs (EC2) using hypervisors
        - And we use AMI to copy(use as template) for creating EC2
        - AMI contains both OS and APPS

    - EC2 autoscaling
        - Add new instances to application  as and when necessary
        - Its for both adding/removing the servers 
        - AWS cloud watch  service will help autoscaling

    - Elastic load balancer    
        - It will act as single point of contact for  all the autoscaling group instances
        - This can  also distribute traffic across  AZs
    - Elastic container Service (ECS) (for micro services)
    - Aws Lambda (server less)
        -  Pay  for  only compute time
        - Run code  without  provisioning and managing EC2
        - Ex: Like adding a new object to S3 to run a function
        - Ex2: When EC2 is termination will run a particular function


Storage:

    - S3
        -  Object level store
        - 99.<9 nines>
        - It copies object to 3 more AZ hence it is more durable
        - No limit on the bucket size
        - but 5TB per object is the max  limit
        - versioning can be enabled as needed(to counter accidental deletion/versioning)
        - 
    - EBS/EFS
        - Block level store (which means we can change each block in the memory - unlike s3 where we need to replace the whole object)
        - EBS can only be connected to one EC2
        - General purpose SSD will get 20k mbps/Provisioned IOPs 64k mbps/normal cold HDD 250 mbps
    - EFS
        - Block level store (which means we can change each block in the memory - unlike s3 where we need to replace the whole object)
        - EFS can be shared across a region ( so can be shared across EC2)
        - This is mostly shared file systems
    
Databases:
    - AWS RDS
        - Fully managed
        - Multi  AZ deployment - HA and reliability(in the same region)  -> Kind of Master slave
        - Read replica - for HA and DR  (in another region) --> Kind of Master for read and write/ read replica for only reads
        - We need to create Read replica by selecting region -  its  not created automaticcally.
    - Amazon Dynamo
        - Milli second latency
        - Nosql database
        - Useful in serverless
        - Gaming leader boards/IoT
    - Amazon Migration Service
        - For migrating from on prem to amazon quickly and securely

Networking
    - VPC
        - It can  be only in the single region
        - it can span across multiple AZ
        - CIDR 10.0.0.0/24 --> meaning first 3  octates are reserved and we can change only last ocatate
            - so total 256 IPs available
        - aws can only allow between  /16(~65536 IPs) to  /28 (16 IPs)
        - Subnet only within AZ (but one AZ can have many subnets)
        - CIDR  is for VPC
            -  so we need  to split CIDR ranges for each subbet (pub/pri subnets)
        - VPC to VPC connection can be established with VPC peering (if the IP addresses are not overlapping)
    - Route  53
        - Domain name to IP address
        - Automatic failover -  based on ELB for each region (when we DR  set up in  two regions)
    - Direct connect
        - To connect cloud  and on prem (its a dedicated network  (not internet))
        - VPC g/w in cloud + Customer G/w on prem
        - This doesn't go  via internet but direct connect (through  vendors     )

    - cloud front distribution (CDN)
        - Ex: netflix streaming
        - Edge  location uses  Amazon Backbone network to connect  to aws (so it is super fast)

Security:
    - Shared responsibility
    - customer responsible for security  inside the cloud
        - os/regions/who can access/encryption of the data/security  groups/firewalls, Account management
    - Amazon is responsible for security of the cloud
        - Network infra/hypervisors etc.
    
    - IAM
        - Amazon congnito for mobile/web applications
        - IAM -> for users
        - Secret Managers - for master password
    - Infrastructure protection
        - AWS WAF - DDoS attacts
        - AWS Shield - Layer 7
            Advanced AWS Shield will provide different ways of  minimizing DDos attacks 
                Based  Geo/IP/ etc.
                This will also support  SYN flood attacks/Layer 3/4/7 Dos attacks
    





