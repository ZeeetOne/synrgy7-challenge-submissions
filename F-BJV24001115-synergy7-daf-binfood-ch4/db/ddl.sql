CREATE TABLE users (
    id UUID PRIMARY KEY,
    username VARCHAR(30) NOT NULL,
    email_address VARCHAR(50) NOT NULL,
    password VARCHAR(60) NOT NULL
);

CREATE TABLE merchant (
    id UUID PRIMARY KEY,
    merchant_name VARCHAR(50) NOT NULL,
    merchant_location VARCHAR(100) NOT NULL,
    open BOOLEAN NOT NULL
);

CREATE TABLE product (
    id UUID PRIMARY KEY,
    product_name VARCHAR(50) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    merchant_id UUID NOT NULL,
    FOREIGN KEY (merchant_id) REFERENCES merchant(id)
);

CREATE TABLE "order" (
    id UUID PRIMARY KEY,
    order_time TIMESTAMP NOT NULL,
    destination_address VARCHAR(100) NOT NULL,
    user_id UUID NOT NULL,
    completed BOOLEAN NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE orderdetail (
    id UUID PRIMARY KEY,
    order_id UUID NOT NULL,
    product_id UUID NOT NULL,
    quantity INTEGER NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES "order"(id),
    FOREIGN KEY (product_id) REFERENCES product(id)
);
