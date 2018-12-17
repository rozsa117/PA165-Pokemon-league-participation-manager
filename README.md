# Pokemon league participation manager

For detailed information visit the wiki page of the project.

## Authors

* Jiří Medveď - 38451
* Michal Mokros - 456442
* Tomi Rózsa - 445653
* Tibor Zauko - 433531

## Building and installation

Java version 8 is recommended for running this project. Java version 11 should also work, but the developers take absolutely no guarantee that you will be able to run the project successfully.

```
mvn clean install
```

On some platforms, surefire plugin may fail with the following error:

```
Error: Could not find or load main class org.apache.maven.surefire.booter.ForkedBooter
```

In that case, try adding **-Dsurefire.useSystemClassLoader=false** to the command invocation:

```
mvn clean install -Dsurefire.useSystemClassLoader=false
```
### Running web app and REST servers

Please refer to the [project wiki](https://github.com/rozsa117/PA165-Pokemon-league-participation-manager/wiki), which has dedicated pages for both topics.
