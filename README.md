# Horn
Notify publicly disclosed cybersecurity vulnerabilities.

Table of Contents
1. [Introduction](#introduction)
2. [How to use](#how-to-use)
3. [Documentation](#documentation)
4. [Overview](#overview)

## Introduction
Horn is a tool that allows you to be notified when a new vulnerability is discovered and announced.
For the moment it is a very young version, still in active development.

## How to use
To use Horn, you have to clone this repository and compile the project using `mvn package`.
Then you can use the command line interface to interact with Horn by running the created JAR file.

## Documentation
Does not exist at this time for obvious reasons.

Notice that the configuration file should look like this:
```properties
mail.smtp.auth=true
mail.smtp.host=your_smtp_server
mail.smtp.port=your_smtp_port
mail.smtp.ssl.trust=your_smtp_server
recipient=your_email@example.com
username=your_username
password=your_password
```

## Overview
For the moment, Horn (in daemon mode) recovers the last vulnerability referenced by the CERT-FR and notifies by email (to the address specified in the configuration file).
I will add in a very near future the retrieval from the CVE database.
In the same way, I will add other ways to be notified (messenger bot for example).