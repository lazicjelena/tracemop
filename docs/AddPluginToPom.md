# Adding TraceMOP Maven Plugin To pom.xml files

An alternative way to run TraceMOP is to add its Maven plugin to project's pom.xml file:
```xml
<plugin>
  <groupId>edu.cornell</groupId>
  <artifactId>tracemop-maven-plugin</artifactId>
  <version>1.0</version>
</plugin>
```
[Here is an example](https://github.com/guan-kevin/commons/blob/6f93a3db1d76ebf57d6f7299561a81045c252f1b/pom.xml#L279-L283)

Adding TraceMOP plugin to pom.xml file allows you to use a shorter command to run TraceMOP:
```bash
~/project$ mvn tracemop-maven-plugin:run
```
