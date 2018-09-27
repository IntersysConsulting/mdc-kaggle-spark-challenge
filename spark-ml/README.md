# Spark ML Challenge: Kickstarter Projects

The **Spark ML Challenge** consists on creating a predictive engine capable of determining 
the success or failure of a Kickstarter project. This challenge aims to cover topics from 
model training up to model online scoring. 

## Download the data

This problem uses a public [Kaggle dataset](https://www.kaggle.com/kemical/kickstarter-projects)
that contains the data of over 300,000 kickstarter projects.

To download the data you'll have to create a [Kaggle account](https://www.kaggle.com) 
and install their [command-line API](https://github.com/Kaggle/kaggle-api). 
Alternatively, you can download the csv files from their website and move the 2018 dataset to the 
base directory of this challenge. 

### Kaggle CLI Instructions

**Debian-based OS**
* Setup the Kaggle command line tool.
    * Install with pip: `pip install kaggle`
    * Get a api-key from Kaggle (my account > create new api token) and place it at: `~/.kaggle/kaggle.json`
* Download the dataset: `kaggle datasets download -d kemical/kickstarter-projects`
    * You might need to add an alias: `alias kaggle="~/.local/bin/kaggle"`
    * Note: the data might be downloaded at `~/.kaggle/...`. Move to the current dir if needed. 
* Move the zip file to the data directory if needed. 
    * Run from this project directory: `cp ~/.kaggle/datasets/kemical/kickstarter-projects/*201801.csv data/`
* Unzip the content: `unzip kickstarter-projects.zip && rm -r *201612.csv`
    * We'll be working on the 2018 dataset.

## Problem definition

In this section we define the scope and tasks for each problem. 

Each problem is evaluated sequentially due to possible dependency with advanced tasks of further problems.
The reviewer will evaluate the output for each problem, clean code and best practices. The following 
convention will be used: 

The solution of problem `i` should be executed when running
```bash
sbt "runMain com.intersys.mdc.challenge.spark.ml.problem<i>.Solution"
```

Where:
```text
    i : is the number of the problem (i.e., 1, 2)
```

**Example** The soution of the first problem should execute when running:
* `sbt "runMain com.intersys.mdc.challenge.spark.ml.problem1.Solution"`

## Problem 1 : ML Algorithm

In this problem you have to train/test a machine learning model that predicts
the success or failure of a Kickstarter project.

**Keypoints**

* Use [Apache Spark Machine Learning Library](https://spark.apache.org/docs/latest/ml-guide.html)
(spark-mllib) to solve this problem.
* **Data processing instructions :** perform at least the following data processing steps.
    * Ignore (filter) the `undefined` values from the `state` column.
    * Assume a project success if the state is `successful` or `live`. 
    All other states must be treated as a project failure.
    * Create a `date_diff` column containing the difference in days from `launched` to `deadline`.
* **Training instructions :** 
    * Random split for the `train` and `test` dataset of **0.70**.
    * Test multiple algorithms (e.g., decision-trees, random-forest, logistic-regression).
    * For each algorithm:
        * Consider using either [random or grid search](https://stats.stackexchange.com/questions/160479/practical-hyperparameter-optimization-random-vs-grid-search) for hyperparameter optimization.
        * Use the [cross-validation technique](https://en.wikipedia.org/wiki/Cross-validation_%28statistics%29).
* **Target metric :**
    * In this challenge we are interested on the [area under receiver operating characteristic curve](https://en.wikipedia.org/wiki/Receiver_operating_characteristic) 
    metric a.k.a. `area under ROC curve`.
* **Predictions :** 
    * Save the predictions for the `test` dataset on a single csv file contained at `resources/output/predictions`.
    * The resulting file should contain the input columns, expected output, and prediction. 
* **Model representation :**
    * Use the [predictive model markup language](https://en.wikipedia.org/wiki/Predictive_Model_Markup_Language) 
    (PMML format) to represent from the data processing up to the model transform. 
    * The [Java PMML](https://github.com/jpmml) dependencies for Apache Spark are provided.
    * Save the model: `resources/output/model/model-<score>.pmml`
        * Where `score` represents the first two decimals of the area under the ROC curve metric. 
        * Example: `resources/output/model/model-95.pmml`
* **Running the application :** all the previous steps should be executed when running:
    * `sbt "runMain com.intersys.mdc.challenge.spark.ml.problem1.Solution"`
## Problem 2 : ML Scoring

In this problem you have to create an API using [Docker](https://www.docker.com/) 
and the [Openscoring](https://github.com/openscoring/openscoring) REST web service. 

* Use `docker` to run the Openscoring REST API on port 8080.
    * Build an image: `docker build -t intersysconsulting/openscoring resources/provided/docker/`
    * Run a container: `docker container run -p 8080:8080 -d --name kickstarter intersysconsulting/openscoring`
* Upload your model and assign the name `kickstarter`. 
    * **Hint** you can use the `curl` methods (see examples [here](https://github.com/openscoring/openscoring)).
    * **Recommendation** test your online model against the several examples on `resources/provided/requests-json` or `resources/providades/requests-csv`.
* **Sanity check** 
    * Create a Scala/Spark program that takes a random sample (5%) of the prediction dataset of **Problem I**
    at `resources/output/prediction/*.csv` and queries your online model for scoring the result. 
    * Did the online-scoring results where exactly the same than the Spark model results? 
    * Save the resulting dataset as a single csv-file at `resources/output/online-prediction/` with
     a new column containing the predictions from the Openscoring API. 
* **Running the application :** the sanity check logic should be executed when running:
    * `sbt "runMain com/intersys.mdc.challenge.spark.ml.problem2.Solution"`
    * Note that the Openscore service must be up and running.
    
## Authors and contributions

Please add an `issue` if you identify any problems or bugs in the code. Feel free to contact the authors and 
contributors for any question. 

* [Rodrigo Hernández Mota](https://www.linkedin.com/in/rhdzmota/)

## License
See the `LICENSE.md` file. 
