"""
Create & populate users, drivers, ride requests & rides relation data model
"""
# !/usr/bin/env python3
from utilities.postgres_connection import get_postgres_connection
import uuid
import csv

def create_users_schema():
    created_schema_statement = """CREATE SCHEMA IF NOT EXISTS users;"""
    cur.execute(created_schema_statement)
    conn.commit()


def create_users_table():
    create_users_table = """
            SET search_path TO users;
            CREATE TABLE IF NOT EXISTS users.users(
                    userid VARCHAR(50) PRIMARY KEY,
                    first_name VARCHAR NOT NULL,
                    last_name VARCHAR NOT NULL,
                    email VARCHAR NOT NULL,
                    UPDATE_TS timestamp NOT NULL
            );
    """
    cur.execute(create_users_table)
    conn.commit()

# def create_schema_users_table():
#     create_users_table = """
#             CREATE TABLE IF NOT EXISTS schema_users(
#                     userid VARCHAR(50) PRIMARY KEY
#             );
#     """
#     cur.execute(create_users_table)
#     conn.commit()

def populate_users_table():
    insert_into_users_table = """
    SET search_path TO users;
    INSERT INTO users.users(userid, first_name, last_name, email,UPDATE_TS) VALUES(%s,%s,%s,%s, current_timestamp);"""

    USERS_CSV_FILE = 'data/users/users.csv'

    with open(USERS_CSV_FILE, newline='') as csvfile:

        reader = csv.DictReader(csvfile)
        i = 0
        try:
            for row in reader:
                i += 1
                cur.execute(insert_into_users_table, [row['userid'], row['first_name'], row['last_name'], row['email']])
                if i == 10:
                    break
            conn.commit()
        except Exception as e:
            print(e)
            conn.commit()



    conn.commit()


if __name__ =='__main__':
    conn = get_postgres_connection('postgresdb')
    cur = conn.cursor()

    create_users_schema()
    create_users_table()
    # create_users_table()
    populate_users_table()
    print("THE POSTGRES DATABASE HAS BEEN SEEDED.")
