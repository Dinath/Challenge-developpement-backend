
# Development

## Deploy

- **Tomcat 9.0.12** was used to deploy the application.
- **Java 1.8.0_181** as programming language.
- **MariaDB 10.1.36** as database.

In order to accomplish the communication between Tomcat and **MySQL**/**MariaDB** we need the [MySQL Java Connector library](https://dev.mysql.com/downloads/connector/j/) being put in the Tomcat's `lib` folder.

## Notes

The web service is protected by a middleware checking the `admin` keyword in URLs. Thus, the user story number 3 is not protected.

There is a cascade between `Question` > `Answer` and `Question` > `Tag`. If a question got deleted, corresponding answer and tags are deleted too.

No framework have been used, just the plain `JDBC` driver. 

`JSON` have been used over `XML`because `JSON` is must lighter than `XML`.

**No test have been implemented.**


## Test environment

To setup the database, just create a database named "Challenge", and import the following sql file.

```
mysql -u root -p -e "create database challenge;"
mysql -u root -p --default-character-set=utf8 < create-tables.sql 
```

Database configuration file is in `WebContent/META-INF/context.xml`. 

### Import sample

```
mysql -u root -p --default-character-set=utf8 < init-test-values.sql 
```

## Security

Passwords are stored under the column name `entitypassword` in table `entityuser`.

They are hashed with `BCrypt` using a cost of `10`.

### Generate a BCrypt password 

[From StackExchange](https://unix.stackexchange.com/questions/307994/compute-bcrypt-hash-from-command-line), here is the way to generate bcrypt hashed passwords on Linux.


```
htpasswd -bnBC 10 "" password | tr -d ':\n' | sed 's/$2y/$2a/'
```

I did use `BCrypt` to hash passwords in database, because **SHA** algorithm seems to be vulnerable to brute force attacks.

**Basic auth** is used to secure URLs, so **HTTPS** must be used to prevent MIM attacks.


#### Example use with CURL

```
 curl -H "Authorization: Basic YS5zb3llckBvdXRsb29rLmZyOmFsZXg=" http://localhost:8080/NetheosChallenge/question/admin
```

`YS5zb3llckBvdXRsb29rLmZyOmFsZXg=` is a simple `Base64` of `a.soyer@outlook.fr:pass`.

A more easy to test command :
```
curl -u a.soyer@outlook.fr:alex http://localhost:8080/NetheosChallenge/question/admin
```


## User Stories

### User story 1

#### POST

Post must be made in JSON, following **label** and **answer** fields are mandatory.
**Tags** are optional, but if specified, they must provide a **label** list.

```
{
	"label": "Alex",
	"answer": {
		"label": "Soyer"
	},
	"tags": [
		{
			"label": "fi"
		},
		{
			"label": "lo"
		}
	]
}
```

#### URL

```
http://localhost:8080/NetheosChallenge/question/admin/create
```


### User story 2

#### GET

Returns a list of **questions**, including **answers** and **tags**.

By default, database limit for queries is `10`, this is defined with the variable `DATABASE_DEFAULT_LIMIT` in `src/utils/database/Utils.java`.

#### URL

```
http://localhost:8080/NetheosChallenge/question/admin
```

#### GET /{pagination}

Same as above, but paginate the result to avoid performance issues.

#### URL

```
http://localhost:8080/NetheosChallenge/question/admin/10
```


### User story 3

#### GET

In order to search a term, you must provide a **search** parameter. The parameter needs to be of at least 3 characters. Special ones are forbidden.

This route is not protected since it does not contain "admin" in it.

#### URL

```
http://localhost:8080/NetheosChallenge/question/find?search=recru
```
