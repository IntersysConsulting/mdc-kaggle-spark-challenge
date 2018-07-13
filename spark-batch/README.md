# Recommendation Engine

The **Spark Batch Challenge** consists in creating a **Film Recommendation Engine** using Spark and other 
data-engineering frameworks.  

## Requirements


## Download the data

This challenge uses a public dataset [available here (kaggle)](https://www.kaggle.com/rounakbanik/the-movies-dataset/home)
that contains metadata of over 45,000 movies and 2.6 million rating from nearly 270,000 users.

To download the data you'll have to create an account at [Kaggle](https://www.kaggle.com) 
and install their [command-line API](https://github.com/Kaggle/kaggle-api).

**Debian-based OS**
* Create an empty data directory from this project: `mkdir data`
* Download the dataset: `kaggle datasets download -d rounakbanik/the-movies-dataset`
    * This command should download the data in a hidden directory for kaggle at $HOME. 
* Move the relevant files to the data directory.
credits.csv   links.csv        movies_metadata.csv keywords.csv ratings.csv
    * `cp ~/.kaggle/datasets/rounakbanik/credits.csv data/credits.csv`
    * `cp ~/.kaggle/datasets/rounakbanik/links.csv data/links.csv`
    * `cp ~/.kaggle/datasets/rounakbanik/movies_metadata.csv data/movies_metadata.csv`
    * `cp ~/.kaggle/datasets/rounakbanik/keywords.csv data/keywords.csv`
    * `cp ~/.kaggle/datasets/rounakbanik/ratings.csv data/ratings.csv`
 
## (Optional) Visualize the dataset

This section requires you to perform a basic data analysis before the data-engineering section. 
Follow the guideline provided in the Jupyter Notebook **data-visualization.ipynb**.

### Install Jupyter notebook
**Debian-based OS**
* Install python and pip: `sudo apt install python3 python3-pip`
* Install ipython: `sudo apt install ipython3`
* Install jupyter notebook: `.`

## Create a database


