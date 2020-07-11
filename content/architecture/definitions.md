# Definitions

## SLA vs SLO
Service Level Agreements/Service Level Objectives
Service level agreements (SLAs), which are commitments made to customers by companies, or service level objectives (SLOs), which are agreed upon by departments of a company.

### SLA

A service level agreement is a contract between a service provider and that provider's consumers that specifies the service provider's responsibilities with respect to different measurable aspects of what is being provided, such as availability, performance, response time for problems, and so on.

Within these agreements, there are clear clauses specifying service details its time-period, cost, availability etc. Typically for internet-based services, the SLA includes specifications concerning MTBF (Mean Time Between Failures), MTTR (Mean Time To Repair or Recovery), the responsibility of data rates, the party committing a fault and other measurable factors.

To meet SLA
 * Recovery time objectives (RTOs)
    - "target time set for resumption of product, service or activity delivery after an incident". 
    - This actually means that RTO is crucial when implementing business continuity in a company – calculating how quickly you need to recover will determine what kind of preparations are necessary. For example, if RTO is 2 hours, then you need to invest quite a lot of money in a disaster recovery center, telecommunications, automated systems, etc. – because you want to be able to achieve full recovery in only 2 hours. However, if your RTO is 2 weeks, then the required investment will be much lower because you will have enough time to acquire resources after an incident has occurred.

 * Recovery point objectives (RPOs)
  - "the maximum tolerable period in which data might be lost"
  - ask yourself how much data you can afford to lose? If you are filling in a database with various kinds of information, is it tolerable to lose 1 hour of work, 2 hours or maybe 2 days?
  - This number of hours or days is the RPO. Recovery Point Objective is crucial for determining one element of business continuity strategy – the frequency of backup. If your RPO is 4 hours, then you need to perform backup at least every 4 hours; every 24 hours would put you in a big danger, but if you do it every 1 hour, it might cost you too much.

Some other important measures:
 * TAT: TAT or Turn-Around Time is the time the provider or their assigned team takes to complete a particular task.
 * FCR: FCR stands for First-Call Resolution. This is the percentage of calls which did not require any further step to reach a solution. That is, the recipient from support team resolved the issue of the caller during that very call.

## LRPs ans SRPs
    * Long Range Plans : plans which multi-year for any architecture. Mostly going to multi cloud/sub-strate etc.
    * Short Range Plans : plans for with in quarter/year. Introducing caching/microservices etc.


