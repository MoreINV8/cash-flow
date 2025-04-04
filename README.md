# Cash flow

an application usin for record your spending money and visualized your financial status !!!!

## How to run backend service
1. using marven to install packet `mvn -U clean install`
2. using marven to run service `mvn spring-boot:run`

## Learning Backend Java Spring Boot
- Using database without JBC and JDBC Template [video](https://youtu.be/KgXq2UBNEhA?si=c1XQwv8pgCT0Gm9r)
  - connect database using `DataSource` class to create connection pool
  - retrive environment data from `.env` by using external library `Dotenv`
  - config github workflow with `Dotenv.configure().ignoreIfMissing().systemProperties()` to enable using system environment
- Unit testing
  - use `@profile(__profile name__)` to set scope enable configuration
- Security config [video](https://youtu.be/oeni_9g7too?si=7JW5lhgym-do-iRU)
  - use `@EnableWebSecurity` to let spring boot use this custom configuration instead deafault
  - use `SecurityFilterChain` and `HttpSecurity` parameter to config new custom filter chain
  - add `AuthenticationProvider` to custom default username password login

> [!IMPORTANT]
> every configuration object in java spring boot should be `@Autowired` not `new Object()` __except for model classes__