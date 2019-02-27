# Super4A - ReadMe 

[![Build Status](https://travis-ci.com/BerangereB/super4A.svg?token=bPhwiywrTeut7vVkZf6B&branch=master)](https://travis-ci.com/BerangereB/super4A) [![codecov](https://codecov.io/gh/BerangereB/super4A/branch/master/graph/badge.svg?token=QG04UsCMBK)](https://codecov.io/gh/BerangereB/super4A)

NB. La couverture réelle est de 99% (Intellij).Travis rencontrant des problèmes avec la version 2.0.0 de SpringBoot, les tests d'intégrations ne sont pas présents dans le rapport de couverture de code de CodeCov
## Project team members

- BOURDOULOUS Bérangère (BerangereB)
- DAGERI Kaan Michaël (kkaann)
- BAILLE Angélique (BAngelique)
- COURREAU Quentin (9QC)

## How to use our API

Our API complies with REST standards according to the Richardson model. It uses the following
verbs :
- GET
- POST
- PUT
- PATCH
- DELETE

For each query, an HTTP status code is sent : 

- 1xx: Informational
It means the request has been received and the process is continuing.

- 2xx: Success
It means the action was successfully received, understood, and accepted.

- 3xx: Redirection
It means further action must be taken in order to complete the request.

- 4xx: Client Error
It means the request contains incorrect syntax or cannot be fulfilled.

- 5xx: Server Error
It means the server failed to fulfill an apparently valid request.

#### PRODUCTS 

- display products : ``` GET /supermarket/products```
- display a specific product with its name : ``` GET /supermarket/products/{name}```
- add product to the catalog (update if already exists): ``` POST /supermarket/products```

	_With a RequestBody like :_
	```{"Name": "toothbrush","Unit": "Each","Price": 0.99}```
- remove a product by its name : ``` DELETE /supermarket/products/{name}```

#### OFFERS

- display active offers : ```GET /supermarket/offers/active```
- display inactive offers : ```GET /supermarket/offers/inactive```
- display simple offers : ```GET /supermarket/offers/active/simple```
- display bundle offers : ```GET /supermarket/offers/active/bundle```
- activate an Offer whose index is given in inactive offers : ```GET /supermarket/offers/activate/{index}```
- deactivate an Offer whose index is given in active offers : ```GET /supermarket/offers/deactivate/{index}```


#### CUSTOMERS

- display customers' shopping cart: ```GET /supermarket/customers```
- display specific customer's shopping cart by Id : ```GET /supermarket/customers/{id}```
- add product in a customer's shopping cart : ```POST /supermarket/customers/{id}```

	_With a RequestBody like :_
	```{"Product": "toothbrush","Quantity": 2}```
- remove product in a customer's shopping cart : ```DELETE /supermarket/customers/{id}```

- checkout articles from a specific shopping cart => display totalPrice: ```GET /supermarket/customers/{id}/receipt```
- checkout articles from a specific shopping cart => display Receipt : ```GET /supermarket/customers/{id}/printedReceipt```


## Useful links

- https://maven.apache.org/guides/mini/guide-creating-archetypes.html
- https://atom.io/packages/editorconfig
