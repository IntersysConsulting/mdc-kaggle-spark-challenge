# [Spark Batch] KickStarter Project Success

The **Spark Batch Challenge** consists in creating a **prediction engine** for KickStarter Projects using Spark and other 
data-engineering frameworks.  

## Requirements
* Scala programming language
* SBT (simple build tool)
* Spark
* Python 3.x
* Postgres

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

To use jupyter notebook, run the following: `jupyter notebook `
## Create a database


