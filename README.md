# Super4A - ReadMe 

[![Build Status](https://travis-ci.org/BerangereB/super4A.svg?branch=master)](https://travis-ci.org/BerangereB/super4A) [![codecov](https://codecov.io/gh/BerangereB/super4A/branch/master/graph/badge.svg)](https://codecov.io/gh/BerangereB/super4A)


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

#### OFFERS
- display offers : ```GET /supermarket/offers```
- display simple offers : ```GET /supermarket/offers/simple```
- display bundle offers : ```GET /supermarket/offers/bundle```
- find offers by specific product : ```GET /supermarket/offers/find?product={product}```


#### CUSTOMERS
- display customers' shopping cart: ```GET /supermarket/customers```
- display specific customer's shopping cart by Id : ```GET /supermarket/customers/{id}```
- add product in a customer's shopping cart : ```POST /supermarket/customers/{id}/add/{product}/{quantity}```
- remove product in a customer's shopping cart : ```DELETE /supermarket/customers/{id}/remove/{product}/{quantity}```

- checkout articles from a specific shopping cart => display totalPrice: ```GET /supermarket/customers/{id}/checkout/totalPrice```
- checkout articles from a specific shopping cart => display Receipt : ```GET /supermarket/customers/{id}/checkout/receipt```


## Useful links

- https://maven.apache.org/guides/mini/guide-creating-archetypes.html
- https://atom.io/packages/editorconfig
