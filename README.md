## Environment variables

order-service
```
DB_URL_ORDER=jdbc:postgresql://localhost:5432/db_order
DB_USER_ORDER=hdev
DB_PASSWORD_ORDER=123456
```

product-service
```
DB_URL_PRODUCT=jdbc:postgresql://localhost:5432/db_product
DB_USER_PRODUCT=hdev
DB_PASSWORD_PRODUCT=123456
```

user-service
```
MONGO_URI=mongodb://localhost:27017/db_user
MONGO_SCHEMA=db_user
```

service discovery
```
EUREKA_SERVER=http://localhost:8761/eureka
```
