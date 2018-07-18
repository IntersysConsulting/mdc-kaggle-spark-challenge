# [Spark Batch] KickStarter Project Success

The **Spark Batch Challenge** consists in creating a **prediction engine** for KickStarter Projects using Spark and other 
data-engineering frameworks. 

**Problem definition**

Create a **REST API** that will serve as a prediction engine for Kickstarter Projects 
and to perform basic CRUD operations.
* Get request of a given project at `http://localhost:8080/kickstarter/projects/predict` should return a json response
indicating if the project will be successful or not in the platform. 
* Put request of a given project at `http://localhost:8080/kicksterter/projects/submit` should add the project to 
a Cassandra database. 

## Requirements
* Scala programming language
* SBT (simple build tool)
* Spark
* Python 3.x
* Cassandra and cql

## Download the data

This challenge uses a public dataset [available here (kaggle)](https://www.kaggle.com/kemical/kickstarter-projects)
that contains the data of over 300,000 kickstarter projects.

To download the data you'll have to create an account at [Kaggle](https://www.kaggle.com) 
and install their [command-line API](https://github.com/Kaggle/kaggle-api).

**Debian-based OS**
* Create an empty data directory from this project: `mkdir data`
* Setup the Kaggle command line tool.
    * Install with pip: `pip install kaggle`
    * Get a api-key from Kaggle (my account > create new api token) and place it at: `~/.kaggle/kaggle.json`
* Download the dataset: `kaggle datasets download -d kemical/kickstarter-projects`
    * This command should download the data in the kaggle directory created in the prev step. 
    * You might need to add an alias: `alias kaggle="~/.local/bin/kaggle"`
* Move the relevant files to the data directory.
    * Run from this project directory: `cp ~/.kaggle/datasets/kemical/kickstarter-projects/*201801.csv data/`
  
## (Optional) Visualize the dataset

This section requires you to perform a basic data analysis before the data-engineering section. 
Follow the guideline provided in the Jupyter Notebook **data-visualization.ipynb**.

### Install Jupyter notebook and dependencies
**Debian-based OS**
* Install python and pip: `sudo apt install python3 ipython3 python3-pip`
* Install jupyter notebook: `pip3 install jupyter`
* Install dependencies: `pip3 install -r python-requirements.txt`
* (Maybe) You might need to install tkinter package in order to use matplotlib. Run in the command line:
  * Install: `sudo apt install python3-tk`
  * Test: `python -c "import matplotlib.pyplot as plt; plt.show()"`

### Use Jupyter notebook

**Debian-based OS**
* Start jupyter notebook by running the following in the terminal: `jupyter notebook`
* Now you can open/edit/create jupyter nobooks (.ipynb files).

## Upload data to Cassandra

The challenge consists to 

**Debian-based OS**
1. Install Cassandra from the [official website](http://cassandra.apache.org/).
2. Start the Cassandra service: `sudo service cassandra start`
3. Use `cqlsh` to create the keyspace and table.
    * Create the keyspace: 
    ```
    CREATE KEYSPACE spark WITH REPLICATION = {'class':'SimpleStrategy', 'replication_factor':1};
    ```
    * Create the table: 
    ```cqlsh
    CREATE TABLE spark.kickstarter (
      "ID" bigint PRIMARY KEY,
      name text,
      category text,
      main_category text,
      country text,
      currency text,
      deadline timestamp,
      goal double,
      launched timestamp,
      pledged double,
      state text,
      backers int,
      "usd pledged" double,
      usd_pledged_real double,
      usd_goal_real double,
    );
    ```
4. Upload the csv data into Cassandra by running the python script named `upload_data.py`.
    * Note that the `python_requirements.txt` file contains the libraries needed to run the script.
    * Install the requirements: `pip install -r python_requirements.txt`
    * Run the script: `python3 upload_data.py`

## [Task 1] Create a predictive model using Spark 
The first task consists in creating a prediction model using Spark. Consider the following:
* Automate the steps required to tune the model.
* Create a well-defined pipeline for feature transformation, generation and cleaning. 
* Use a **random search algorithm** to test different configurations of hyperparameters.
    * Save the model type, configuration and score in a Cassandra table (e.g., `model`)
* Save the best model using the PMML standard. 

The task should start when running:
```
sbt "run com.intersys.mdc.challenge.spark.batch.Task1"
```

## [Task 2] Create a REST API to serve the model
The second task consists in creating an independent REST API to serve the model and interact with the 
database. 

The task should start when running:
```
sbt "run com.intersys.mdc.challenge.spark.batch.Task2"
```

## Authors and contributions

Please add an `issue` if you identify any problems or bugs in the code. Feel free to contact the authors and 
contributors for any question. 

* [Rodrigo Hernández Mota](https://www.linkedin.com/in/rhdzmota/) (rhdzmota)

## License
Contact **Intersys Consulting** for further information. 


