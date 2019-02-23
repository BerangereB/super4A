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

1	1xx: Informational
It means the request has been received and the process is continuing.

2	2xx: Success
It means the action was successfully received, understood, and accepted.

3	3xx: Redirection
It means further action must be taken in order to complete the request.

4	4xx: Client Error
It means the request contains incorrect syntax or cannot be fulfilled.

5	5xx: Server Error
It means the server failed to fulfill an apparently valid request.

#### PRODUCTS 
- display products : ```/supermarket/products```
- display a specific product with its name : ```/supermarket/products/{name}```
- add product to catalog : ```/supermarket/products/add?name={name}&unit={unit}&price={price}```
- update product price : ```/supermarket/products/update?name={name}&price={price}```
- remove a produt from catalog : ```/supermarket/products/remove/{name}```

#### OFFERS
- display offers : ```/supermarket/offers```
- display simple offers : ```/supermarket/offers/simple```
- display bundle offers : ```/supermarket/offers/bundle```
- find offers by specific product : ```/supermarket/offers/find?product={product}```
- add simple offer : ```/supermarket/offers/simple/add?type={offerType}&product={product}&arg={arg}``` arg is optional for _ThreeForTwo_ TYPE

	offerType = _Percent_, _ThreeForTwo_, _TwoForAmount_ or _FiveForAmount_
- add bundle offer : ```/supermarket/offers/bundle/add?type={offerType}&product={product1}&product={product2}&product={product3}&quantity={quantity1}&quantity={quantity2}&quantity={quantity3}&arg={arg}```

	offerType = _PercentBundle_ or _AmountBundle_
- remove offer : ```/supermarket/offers/remove?id={id}```

#### CUSTOMERS
- display customers' shopping cart: ```/supermarket/customers```
- display specific customer's shopping cart by Id : ```/supermarket/customers/{id}```
- add product in a customer's shopping cart : ```/supermarket/customers/{id}/add/{product}/{quantity}```
- remove product in a customer's shopping cart : ```/supermarket/customers/{id}/remove/{product}/{quantity}```

- checkout articles from a specific shopping cart => display totalPrice: ```/supermarket/customers/{id}/checkout/totalPrice```
- checkout articles from a specific shopping cart => display Receipt : ```/supermarket/customers/{id}/checkout/receipt```


## Useful links

- https://maven.apache.org/guides/mini/guide-creating-archetypes.html
- https://atom.io/packages/editorconfig
