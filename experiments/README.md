# TraceMOP experiments

## Projects and data

You can find our 105 projects [here](data/projects.csv), and you can find our experiments data in this [directory](data).

### Generate CSV 

To generate the plots we have in the paper, run

```bash
pip3 install matplotlib numpy
python3 make-project-data.py data/data-actual.csv .
```


## Usage

## Prerequisites:
- A x86-64 architecture machine
- Ubuntu 20.04
- Python 3.9 or above
- [Docker](https://docs.docker.com/get-docker/)

### Run experiments

```bash
# Inside this directory, outside Docker container,
# run the below command to run TraceMOP without trace tracking and JavaMOP (could take multiple days)
bash run_javamop_in_docker.sh tracemop-vs-javamop.txt tracemop-vs-javamop

# Inside this directory, outside Docker container,
# run the below command to run TraceMOP with trace tracking and our previous prototype (could take multiple days)
bash run_in_docker.sh tracemop-vs-prototype.txt tracemop-vs-prototype

# After running both commands above, run the below in the same directory
bash collect-results.sh tracemop-vs-javamop tracemop-vs-prototype
```

### Outputs
After running the above experiments, the current directory will contain 4 csv files with the following header:

`tracemop-vs-javamop-time.csv`: project name, time to download project, time to run TraceMOP without trace tracking, time to test project, and time to run JavaMOP (in ms)

`tracemop-vs-prototype-time.csv`: project name, time to run TraceMOP with trace tracking, time to run our previous prototype (in ms)

`tracemop-vs-prototype-memory.csv`: project name, memory used by TraceMOP with trace tracking, and memory used by our previous prototype (in KB)

`tracemop-vs-prototype-disk.csv`: project name, disk used by TraceMOP with trace trackin, and disk used by our previous prototype (in byte)
