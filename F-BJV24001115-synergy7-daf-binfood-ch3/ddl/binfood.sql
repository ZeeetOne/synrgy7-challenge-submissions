
CREATE TABLE Users (
    id UUID PRIMARY KEY,
    username VARCHAR(15),
    email_address VARCHAR(30),
    password VARCHAR(50)
);

CREATE TABLE Merchant (
    id UUID PRIMARY KEY,
    merchant_name VARCHAR(25),
    merchant_location VARCHAR(50), 
    open BOOLEAN
);

CREATE TABLE Product (
    id UUID PRIMARY KEY,
    product_name VARCHAR(25),
    price DECIMAL(10, 2),
    merchant_id UUID REFERENCES Merchant(id)
);

CREATE TABLE "Order" (
    id UUID PRIMARY KEY,
    order_time TIMESTAMP,
    destination_address VARCHAR(50),
    user_id UUID REFERENCES Users(id),
    completed BOOLEAN
);

CREATE TABLE Order_Detail (
    id UUID PRIMARY KEY,
    order_id UUID REFERENCES "Order"(id),
    product_id UUID REFERENCES Product(id),
    quantity INTEGER,
    total_price DECIMAL(10, 2)
);
