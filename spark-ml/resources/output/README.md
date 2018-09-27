## Instructions

The expected output of your application:
* `predictions` directory containing a single csv for the predictions of the `test` data of your model.
* `model` directory containing the pmml definition of your machine learning model. 

## Directory Structure
```text
- ...
- resources/
    |- output/
       |- README.md
       |- predictions/
       |    |- <generic-spark-name>.csv
       |    |- ... 
       |- online-predictions/
       |    |- <generic-spark-name>.csv
       |    |- ...
       |- model
            |- model-<score>.pmml
```

Where:
```text
    generic-spark-name    : represents the name that sparks gives to the csv file. 
    score                 : defined in the README file of the base directory. 
```
