Scenario: Create regional offers for adult passenger
Given offer request for trip in RIGA
And add passenger type ADULT
And add transport type BUS
And add offer type SINGLE_TICKET
And add company name RIGAS_SATIKSME
And set number of tickets to 5
When call create offers
Then response status is OK
And 1 offer has city RIGA
And 1 offer id is not null
And 1 offer has discount value 1.00

Scenario: Create regional offers for adult passenger with specified street names
Given offer request for trip in RIGA
And add passenger type ADULT
And add transport type BUS
And add offer type SINGLE_TICKET
And add company name RIGAS_SATIKSME
And set number of tickets to 5
And set departure street name Slokas iela and home number 22
And set arrival street name K.Valdemara iela and home number 6
When call create offers
Then response status is OK
And 1 offer has city RIGA
And 1 offer id is not null
And 1 offer has discount value 1.00

Scenario: Create regional offers for adult passenger with specified street names when nothing found
Given offer request for trip in RIGA
And add passenger type ADULT
And add transport type BUS
And add offer type SINGLE_TICKET
And add company name RIGAS_SATIKSME
And set number of tickets to 5
And set departure street name Slokas iela and home number 22
And set arrival street name Krasta iela and home number 6
When call create offers
Then response status is NOT_FOUND

Scenario: Create regional offers for scholar passenger
Given offer request for trip in RIGA
And add passenger type SCHOLAR
And add transport type BUS
And add offer type SINGLE_TICKET
And add company name RIGAS_SATIKSME
And set number of tickets to 5
When call create offers
Then response status is OK
And 1 offer has city RIGA
And 1 offer id is not null
And 1 offer has discount value 0.00