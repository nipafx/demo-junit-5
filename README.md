# Demo of JUnit 5

Demo project accompanying [a series of posts exploring JUnit 5](https://blog.codefx.org/tag/junit-5/).

## First Steps

* [Setup](http://blog.codefx.org/libraries/junit-5-setup/):
have a look at [`pom.xml`](pom.xml) or [`build.gradle`](build.gradle)
* [Basics](http://blog.codefx.org/libraries/junit-5-basics/):
[`LifecycleTest`](src/test/java/org/codefx/demo/junit5/basics/LifecycleTest.java) is a good introduction, for more details see the other classes in
[`test/.../basics`](src/test/java/org/codefx/demo/junit5/basics)

## Next Steps

* [Architecture](http://blog.codefx.org/design/architecture/junit-5-architecture/) (has no code samples)
* Side by side with JUnit 4:
configured in [`pom.xml`](pom.xml) and [`build.gradle`](build.gradle) (search for _4.12_) and demonstrated in [`test/.../junit4`](src/test/java/org/codefx/demo/junit4)
* [Dynamic tests](http://blog.codefx.org/libraries/junit-5-dynamic-tests/):
 demonstrated in [`test/.../dynamic`](src/test/java/org/codefx/demo/junit5/dynamic)
* [Parameterized tests](http://blog.codefx.org/libraries/junit-5-parameterized-tests/):
demonstrated in [`test/.../parameterized`](src/test/java/org/codefx/demo/junit5/parameterized), starting with [`HelloParams`](src/test/java/org/codefx/demo/junit5/parameterized/HelloParams.java)

## Extensions

* [Extension model](http://blog.codefx.org/design/architecture/junit-5-extension-model/):
implemented in [`main`](src/main/java/org/codefx/demo/junit5) and used in [`test/.../extensions`](src/test/java/org/codefx/demo/junit5/extensions)
* [Conditions](http://blog.codefx.org/libraries/junit-5-conditions/):
implemented in [`main`](src/main/java/org/codefx/demo/junit5) and used in [`test/.../extensions`](src/test/java/org/codefx/demo/junit5/extensions)
* Parameter injection
