
# Trip Calculator

This app takes user taps on buses from a csv file and calculates the duration and cost of the trips. The trip information is then outputted to a csv file. 

Input expected format is a csv file containing the list of tap information of different users
```
ID, DateTimeUTC, TapType, StopId, CompanyId, BusID, PAN
1, 22-01-2023 13:00:00, ON, Stop1, Company1, Bus37, 5500005555555559
2, 22-01-2023 13:05:00, OFF, Stop2, Company1, Bus37, 5500005555555559
3, 22-01-2023 09:20:00, ON, Stop3, Company1, Bus36, 4111111111111111
4, 23-01-2023 08:00:00, ON, Stop1, Company1, Bus37, 4111111111111111
5, 23-01-2023 08:02:00, OFF, Stop1, Company1, Bus37, 4111111111111111
6, 24-01-2023 16:30:00, OFF, Stop2, Company1, Bus37, 5500005555555559
```

Output expected format is a csv file containing the list of trip information including trip duration and cost

```
Started, Finished, DurationSecs, FromStopId, ToStopId, ChargeAmount, CompanyId, BusID, PAN, Status
22-01-2018 13:00:00, 22-01-2018 13:05:00, 900, Stop1, Stop2, $3.25, Company1, Bus37, 5500005555555559, COMPLETED
```

### Dependencies

This app has been built using Java openjdk-22 and Maven 3.9.6

### Build and run

```dtd
mvn compile
mvn package
java -jar target/gs-maven-0.1.0.jar
```


### Assumptions

- Taps are in order of id/date so no sorting is required.
- The reviewers of this code understand my experience is mainly .Net which made things more difficult. For example, knowing which file reading/writer to use.
- If there is no known cost for the trip eg stop doesnt exist, use 0. Log this information to monitoring with alerts.