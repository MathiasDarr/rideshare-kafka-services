## Rideshare Services Data Model ##
This directory contains data generating & data seeding scripts

## Data Model ##
* users 
    * userid PRIMARY KEY
    * first_name
    * last_name
    * city
    * phone_number
    * email
    * password

* drivers 
    * driverid PRIMARY KEY
    * first_name
    * last_name
    * city
    * phone_number
    * email
    * password

* rides 



### Scripts ###
* generate_data.py
    - generates random names for 200,000 users & 15,000 drivers
* seed_postgres_data.py
    - insert the randomly generated data to postgres
    
    
kafka-avro-console-consumer --from-beginning --topic users.users \
  --bootstrap-server localhost:9092 \
  --property print.key=true \
  --property schema.registry.url=http://localhost:8081
    