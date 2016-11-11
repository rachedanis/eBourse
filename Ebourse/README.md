=========

### Build the Project
```
mvn clean install
```


### Set up MySQL
```
mysql -u root -p 
> CREATE USER 'tutorialuser'@'localhost' IDENTIFIED BY 'tutorialmy5ql';
> GRANT ALL PRIVILEGES ON *.* TO 'tutorialuser'@'localhost';
> FLUSH PRIVILEGES;
```


### Set up Email

You need to configure the email by providing your own username and password.
