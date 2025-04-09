# Cash flow

an application usin for record your spending money and visualized your financial status !!!!

## How to run backend service?
1. using Maven to install packege `mvn -U clean install`
2. using Maven to run service `mvn spring-boot:run`

### Learning Backend Java Spring Boot
- Using database without JBC and JDBC Template [video](https://youtu.be/KgXq2UBNEhA?si=c1XQwv8pgCT0Gm9r)
  - connect database using `DataSource` class to create connection pool
  - retrive environment data from `.env` by using external library `Dotenv`
  - config github workflow with `Dotenv.configure().ignoreIfMissing().systemProperties()` to enable using system environment
- Unit testing
  - use `@profile(__profile name__)` to set scope enable configuration
- Security config [video](https://youtu.be/oeni_9g7too?si=7JW5lhgym-do-iRU)
  - use `@EnableWebSecurity` to let spring boot use this custom configuration instead default
  - use `SecurityFilterChain` and `HttpSecurity` parameter to config new custom filter chain
  - add `AuthenticationProvider` to custom default username password login
  - add `AuthenticationManager` to hold and get verify later
    - use `AuthenticationManager` in [UserService.java](/backend/src/main/java/cash/flow/backend/services/UserService.java)
  - implement JWT service to generate and verify token
    - generate __secret key__ in constructor function _or can use hard code secret key_

## How to create Anugular and Nuxt project?
- __Angular__ [video](https://youtu.be/oUmVFHlwZsI?si=OiSah1zEg8VU6khz)
  - install Angular cli `npm install -g @angular/cli`
  - start Angular project `ng new __project name__`
  - creating new component `ng g c __path__/__new component`
  - adding assets to `public` and can use `__asset file__.__file type__`
- __Nuxt__
  - start Nuxt project `npm create nuxt __project name__`

> [!TIP]
> if `@RequestBody` doesn't recognize json request body add `@JsonProperty(__property name__)` or change variable name to **snake_case**

> [!IMPORTANT]
> every configuration object in java spring boot should be `@Autowired` not `new Object()` __except for model classes__

---

### What have learnt
- To add opacity to hexadecimal color code is to add 2 digit of hexadecimal at the end
- To get hexadecimal of opacity
  - First convert opacity(%) match with 255 (1 byte)
  - Then convert result to hexadecimal
    - Remainder method
    - example convert `x` to hexadecimal
      - y0 = x % 16
      - y1 = floor(x / 16) % 16
      - result will equal ... + y1 + y0
