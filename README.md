# Super4A - ReadMe 

[![Build Status](https://travis-ci.org/BerangereB/super4A.svg?branch=master)](https://travis-ci.org/BerangereB/super4A) [![codecov](https://codecov.io/gh/BerangereB/super4A/branch/master/graph/badge.svg)](https://codecov.io/gh/BerangereB/super4A)


## Project team members

- BOURDOULOUS Bérangère (BerangereB)
- DAGERI Kaan Michaël (kkaann)
- BAILLE Angélique (BAngelique)
- COURREAU Quentin (9QC)

## How to use our API
####PRODUCTS 
- display products : ```/supermarket/products```
- display a specific product with its name : ```/supermarket/products/{name}```
- add product to catalog : ```/supermarket/products/add?name={name}&unit={unit}&price={price}```
- update product price : ```/supermarket/products/update?name={name}&price={price}```
- remove a produt from catalog : ```/supermarket/products/remove/{name}```

####OFFERS
- display offers : ```/supermarket/offers```
- display simple offers : ```/supermarket/offers/simple```
- display bundle offers : ```/supermarket/offers/bundle```
- find offers by specific product : ```/supermarket/offers/find?product={product}```
- add simple offer : ```/supermarket/offers/simple/add?type={offerType}&product={product}&arg={arg}``` arg is optional for _ThreeForTwo_ TYPE

	offerType = _Percent_, _ThreeForTwo_, _TwoForAmount_ or _FiveForAmount_
- add bundle offer : ```/supermarket/offers/bundle/add?type={offerType}&product={product1}&product={product2}&product={product3}&quantity={quantity1}&quantity={quantity2}&quantity={quantity3}&arg={arg}```

	offerType = _PercentBundle_ or _AmountBundle_
- remove offer : ```/supermarket/offers/remove?id={id}```

####CUSTOMERS
- display customers' shopping cart: ```/supermarket/customers```
- display specific customer's shopping cart by Id : ```/supermarket/customers/{id}```
- add product in a customer's shopping cart : ```/supermarket/customers/{id}/add/{product}/{quantity}```
- remove product in a customer's shopping cart : ```/supermarket/customers/{id}/remove/{product}/{quantity}```

- checkout articles from a specific shopping cart => display totalPrice: ```/supermarket/customers/{id}/checkout/totalPrice```
- checkout articles from a specific shopping cart => display Receipt : ```/supermarket/customers/{id}/checkout/receipt```


## Useful links

- https://maven.apache.org/guides/mini/guide-creating-archetypes.html
- https://atom.io/packages/editorconfig
