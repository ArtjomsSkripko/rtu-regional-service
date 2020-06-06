ALTER USER user_name SET search_path to 'transport_system';
CREATE SCHEMA IF NOT EXISTS "transport_system";

CREATE TABLE "transport_system"."regional_discount"(
    "city" varchar(100) NOT NULL,
    "discount" decimal(4,2) NOT NULL,
    "passenger_type" varchar(100) NOT NULL
);

CREATE TABLE "transport_system"."application_discount"(
    "app_type" varchar(100) NOT NULL,
    "discount" decimal(4,2) NOT NULL,
    "app_name" varchar(100) NOT NULL
);

CREATE TABLE "transport_system"."company_discount"(
    "company" varchar(100) NOT NULL,
    "discount" decimal(4,2) NOT NULL
);

CREATE TABLE "transport_system"."regional_offer"(
    "offer_type" varchar(100) NOT NULL,
    "company_name" varchar(100) NOT NULL,
    "transport_types" varchar(100) NOT NULL,
    "price" decimal(4,2) NOT NULL,
    "tax_rate" decimal(4,2) NOT NULL,
    "city" decimal(4,2) NOT NULL,
    "route_nummber" varchar(100)
);

CREATE TABLE "transport_system"."stops"(
    "street_name" varchar(100) NOT NULL,
    "home_number" int NOT NULL,
    "city" varchar(100) NOT NULL,
    "route_number" int
);