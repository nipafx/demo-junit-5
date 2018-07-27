# Demo of JUnit 5

Demo project accompanying a series of posts exploring JUnit 5:

## First Steps

* [Setup](http://blog.codefx.org/libraries/junit-5-setup/):
have a look at [`pom.xml`](pom.xml) or [`build.gradle`](build.gradle)
* [Basics](http://blog.codefx.org/libraries/junit-5-basics/):
[`LifecycleTest`](src/test/java/org/codefx/demo/junit5/basics/LifecycleTest.java)
is a good introduction, for more details see the other classes in
[`org.codefx.demo.junit5.basics`](src/test/java/org/codefx/demo/junit5/basics)

## Next Steps

* [Architecture](http://blog.codefx.org/design/architecture/junit-5-architecture/) (has no code samples)
* Migration
* [Dynamic Tests](http://blog.codefx.org/libraries/junit-5-dynamic-tests/): demonstrated in [`test/.../dynamic`](src/test/java/org/codefx/demo/junit5/dynamic)
* [Parameterized Tests](http://blog.codefx.org/libraries/junit-5-parameterized-tests/): demonstrated in [`test/.../parameterized`](src/test/java/org/codefx/demo/junit5/parameterized), starting with [`HelloParams`](src/test/java/org/codefx/demo/junit5/parameterized/HelloParams.java)

## Extensions

* [Extension Model](http://blog.codefx.org/design/architecture/junit-5-extension-model/): implemented in [`main`](src/main/java/org/codefx/demo/junit5) and used in [`test/.../extensions`](src/test/java/org/codefx/demo/junit5/extensions)
* [Conditions](http://blog.codefx.org/libraries/junit-5-conditions/): implemented in [`main`](src/main/java/org/codefx/demo/junit5) and used in [`test/.../extensions`](src/test/java/org/codefx/demo/junit5/extensions)
* Parameter Injection
