# API Gateway Pattern
![alt text](https://cdn-1.wp.nginx.com/wp-content/uploads/2017/04/Palladino-conf2016-slide9_API-Gateway-Pattern-11-54.png)


![alt text](https://piotrminkowski.files.wordpress.com/2017/04/swagger.png?w=768&h=656)

## Theory
* todo pattern description

## Features
### Swagger Support
#### TODO
* configuration (https://springfox.github.io/springfox/docs/current/#springfox-spring-mvc-and-spring-boot)

### Routing: Spring Cloud Netflix Zuul Support

From Spring Cloud docs:
Routing in an integral part of a microservice architecture. For example, / may be mapped to your web application, /api/users is mapped to the user service and /api/shop is mapped to the shop service. Zuul is a JVM based router and server side load balancer by Netflix.

Netflix uses Zuul for the following:
* Authentication
* Insights
* Stress Testing
* Canary Testing
* Dynamic Routing
* Service Migration
* Load Shedding
* Security
* Static Response handling
* Active/Active traffic management

Zuul have 4 types of filters:
* ```pre``` filters are executed before the request is routed,
* ```route``` filters can handle the actual routing of the request,
* ```post``` filters are executed after the request has been routed, and
* ```error``` filters execute if an error occurs in the course of handling the request

Tutorials:
https://spring.io/guides/gs/routing-and-filtering/

In dev...Stay tuned.

### Oauth2 Support
In dev...

http://projects.spring.io/spring-security-oauth/docs/oauth2.html

## Best practices
https://www.nginx.com/blog/microservices-api-gateways-part-1-why-an-api-gateway/
https://apigee.com/about/blog/taglist/restful-api-design
https://piotrminkowski.wordpress.com/2017/04/14/microservices-api-documentation-with-swagger2/

## Other
https://en.wikipedia.org/wiki/OpenAPI_Specification
https://github.com/Netflix/zuul/wiki/How-it-Works

