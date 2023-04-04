[![Community badge: Incubating](https://img.shields.io/badge/Lifecycle-Incubating-blue)](https://github.com/Camunda-Community-Hub/community/blob/main/extension-lifecycle.md#incubating-)
[![Community extension badge](https://img.shields.io/badge/Community%20Extension-An%20open%20source%20community%20maintained%20project-FF4700)](https://github.com/camunda-community-hub/community)
![Compatible with: Camunda Platform 8](https://img.shields.io/badge/Compatible%20with-Camunda%20Platform%208-0072Ce)


# camunda-8-connector-salesforce

This collection contains different connector to realize operation on Salesforce.

# Build

```bash
mvn clean package
```



# Create object
## API
### Input

Create one object in Salesforce.

In the API, it's possible to give a direct information to create an object, or a list of operation. This is piloted by the "mode" attribut.


```json
{
  "mode" : "SINGLE|LIST",
  "ObjectType": ".....",
  "ListOfAttributes": {
    "name1": "value",
    "name2": "value"

  },
  "operations": [
    {
      "ObjectType": ".....",
      "ListOfAttributes": {
        "name": "value"
      }
    }
  ]
}
```
### Output
According to the mode, the result contains one information, or a list of result.


```json
{
  "ObjectID" : "...",
  "StatusOperation" : "",
  
  "operations": [
    {
    "ObjectID" : "...",
    "StatusOperation" : ""
    }
  ]
}
````
## Element Template


# Query Objects API
## API

Query in Salesforce.

Execute a query, which returns a list of objects.


```json
{
  "query": "....."
}
```
### Output
According to the mode, the result contains one information, or a list of result.


```json
{
  "operations": [
    {
      "ObjectID" : "...",
      "ObjectType": ""
    }
  ]
}
````
## Element Template



# Load Object API
## API

Load a object in Salesforce

In the API, it's possible to give a direct information to delete one object, or a list of operations. This is piloted by the "mode" attribut.


```json
{
  "mode" : "SINGLE|LIST",
  "ObjectId": ".....",
  "operations": [
    {
      "ObjectId": "....."
    }
  ]
}
```
### Output
According to the mode, the result contains one information, or a list of result.


```json
{
  "ObjectID" : "...",
  "StatusOperation" : "",
  "ListOfAttributes" : {
    "name1": "value",
    "name2": "value"
  },
  "operations": [
    {
      "ObjectID" : "...",
      "StatusOperation" : "",
      "ListOfAttributes" : {
        "name1": "value",
        "name2": "value"
      }
    }
  ]
}
````

## Element Template

# Update Object API

Update one object in Salesforce.

In the API, it's possible to give a direct information to delete one object, or a list of operations. This is piloted by the "mode" attribut.


```json
{
  "mode" : "SINGLE|LIST",
  "ObjectId": ".....",
  "ListOfAttributes" : {
    "name1": "value",
    "name2": "value"
  },
  "operations": [
    {
      "ObjectId": ".....",
      "ListOfAttributes" : {
        "name1": "value",
        "name2": "value"
      }
    }
  ]
}
```
### Output
According to the mode, the result contains one information, or a list of result.


```json
{
  "ObjectID" : "...",
  "StatusOperation" : "",
  
  "operations": [
    {
    "ObjectID" : "...",
    "StatusOperation" : ""
    }
  ]
}
````
## Element Template




# Delete Object API
## API

Delete one object in Salesforce.

In the API, it's possible to give a direct information to delete one object, or a list of operations. This is piloted by the "mode" attribut.


```json
{
  "mode" : "SINGLE|LIST",
  "ObjectId": ".....",
  "operations": [
    {
      "ObjectId": "....."
    }
  ]
}
```
### Output
According to the mode, the result contains one information, or a list of result.


```json
{
  "ObjectID" : "...",
  "StatusOperation" : "",
  
  "operations": [
    {
    "ObjectID" : "...",
    "StatusOperation" : ""
    }
  ]
}
````
## Element Template



