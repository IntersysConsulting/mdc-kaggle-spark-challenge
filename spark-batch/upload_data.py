import pandas as pd
from cassandra.cluster import Cluster


def get_cassandra_session():
    cluster = Cluster()
    session = cluster.connect()
    return session


def create_cql_query(table, columns):
    cql_query = """
    INSERT INTO {table} ({cols})
    VALUES ({placeholder})
    """.format(table=table, cols=",".join(columns), placeholder=",".join(["%s"] * len(columns)))
    return cql_query

def cassandra_insert(sess, query, values):
    try:
        sess.execute(query, tuple(values))
    except Exception as e:
        print("Failed to perform insert into Cassandra: {}".format(str(e)))

def main():
    session = get_cassandra_session()
    df = pd.read_csv("data/ks-projects-201801.csv")
    cql_query = create_cql_query("spark.kickstarter", df.columns).replace("ID", '"ID"').replace("usd pledged", '"usd pledged"')
    for row in df.values:
        cassandra_insert(session, cql_query, row)


if __name__ == "__main__":
    main()

