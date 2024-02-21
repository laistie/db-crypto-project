import psycopg2
import csv

# Function to clean numeric values
def clean_numeric(value):
    if value is None:
        return '0'
    return value.replace('$', '').replace(',', '')

def clean_tables(cursor):
    cursor.execute("DELETE FROM dbproject.moedavolatil")
    cursor.execute("DELETE FROM dbproject.cryptomoeda")
    print("Tables cleaned successfully.")

# Database connection parameters
db_params = {
    'dbname': 'laistie',
    'user': 'laistie',
    'password': '040120',
    'host': '189.90.66.154',
    'port': '5432'
}

# Connect to the PostgreSQL database
conn = psycopg2.connect(**db_params)
cursor = conn.cursor()

# Clean up the tables before inserting new data
clean_tables(cursor)
conn.commit()

# Read the CSV file
with open('historical-snapshot.csv', 'r') as file:
    reader = csv.DictReader(file)
    for row in reader:
        # Clean up the numeric values
        price = clean_numeric(row['Price'])
        market_cap = clean_numeric(row['Market Cap'])

        # Insert data into the 'cryptomoeda' table first
        cursor.execute(
            """
            INSERT INTO dbproject.cryptomoeda (nome, sigla)
            VALUES (%s, %s)
            ON CONFLICT (sigla) DO NOTHING
            """,
            (row['Name'], row['Symbol'])
        )
        conn.commit()  # Commit after each insert to ensure data is available for the next table

        # Then insert data into the 'moedavolatil' table
        cursor.execute(
            """
            INSERT INTO dbproject.moedavolatil (data_requisicao, valorusd, marketcap, sigla)
            VALUES (%s, %s, %s, %s)
            """,
            (row['Date'], price, market_cap, row['Symbol'])
        )
        conn.commit()  # Commit after each insert

# Close the connection
cursor.close()
conn.close()
