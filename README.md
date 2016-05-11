# Telosys Web Admin ![TravisCI buils status](https://travis-ci.org/telosys-web-admin-emn/telosys-web-admin.svg?branch=master)
Administration interface for [Telosys SaaS](https://github.com/telosys-saas/telosys-saas).

## Configuration
Before launching Telosys Web Admin, you need to configure it so that it can
detect the Telosys SaaS directory.

open *src/main/resources/META-INF/webadmin.properties* with your favorite text editor
and replace *telosys_fs_location=../telosys-saas/fs/* by the path to your own
Telosys SaaS installation.

## Compilation
To compile the project, clone it, go to the root of the repository and use maven :

    mvn compile

## Run it
To start the server you have two options, either use maven/jetty or package the project.

### Maven/Jetty
Open a terminal at the root of the project and type :

    mvn jetty:run

The server should then be launched and you can start using the administration
interface by typing http://127.0.0.1:8080/do/users in your browser.

### Packaging
Open a terminal at the root of the project and type :

    mvn package

You should now have a WAR file in the *target* directory.
You can use this WAR file to deploy the project on your Tomcat (or equivalent) server.