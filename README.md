# try-lacinia

Playing with [walmartlabs/lacinia](https://github.com/walmartlabs/lacinia) library, a GraphQL implementation in pure Clojure.


## Usage

Start web service at port 3000
```sh
lein ring server-headless
```

Then, send POST requests to `/graphql` url with query data
```sh
(app {:uri "/" :request-method :get})
(app {:uri "/graphql" :request-method :post :body "{ hero { id name}}"})
(app {:uri "/graphql" :request-method :post :body "{ hero { id name friends { name}}}"})
(app {:uri "/graphql" :request-method :post :body "{ human { id name}}"})
```


## License

Copyright © 2017 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
