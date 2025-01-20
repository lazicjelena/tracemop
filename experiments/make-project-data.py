# data/data-actual.csv .
import os
import argparse
import csv
import statistics

import matplotlib.pyplot as plt
import numpy as np
import numpy

from collections import defaultdict
from time import strftime, localtime


def get_project_data(args):
    project_list, mop_time_list, new_time_list, old_time_list, time_improve_list, mop_mem_list, new_mem_list, old_mem_list, mem_improve_list, new_disk_list, old_disk_list, disk_improve_list = ([] for i in range(12))
    javamop_vs_tracemop_list = []
    time_list = []
    memory_list = []
    traces_list = []
    events_list = []
    disk_list = []
    
    memory_rel_list = []
    memory_X_list = []
    disk_rel_list = []
    disk_X_list = []
    
    uniqueness = {}
    events_and_monitors = {}
    with open(os.path.join('data', 'project-tests-events-monitors.csv')) as f:
        reader = csv.DictReader(f)
        for row in reader:
            events_and_monitors[row['project']] = {'monitors': int(row['monitors']), 'events': int(row['events'])}
    
    with open(args.statscsv, mode='r') as statsfile:
        reader = csv.DictReader(statsfile)
        for row in reader:
            project_list.append(row['project'])
            javamop_vs_tracemop_list.append(round(float(row['javamop']) / float(row['tracemop (no-track) new']), 3))
            
            mop_time_list.append(round(float(row['tracemop (no-track) new']) / 1000, 3))
            new_time_list.append(round(float(row['tracemop new time']) / 1000, 3))
            old_time_list.append(round(float(row['tracemop old time']) / 1000, 3))
            time_improve_list.append(round(float(row['old/new']) * 100 - 100, 3))

            new_mem_list.append(round(float(row['tracemop new memory']) / 1000000, 3))
            old_mem_list.append(round(float(row['tracemop old memory']) / 1000000, 3))
            mem_improve_list.append(round(float(row['new - old in GB']), 3))
            
            if row['tracemop new memory'] == '0':
                memory_rel_list.append(0)
                memory_X_list.append(1)
            else:
                memory_rel_list.append( round( (float(row['tracemop old memory']) / float(row['tracemop new memory']))*100-100 ) )
                memory_X_list.append( round( float(row['tracemop old memory']) / float(row['tracemop new memory']), 3 ) )
            
            new_disk_list.append(round(float(row['tracemop new disk (byte)']) / 1000000, 3))
            old_disk_list.append(round(float(row['tracemop old disk (byte)']) / 1000000, 3))
            disk_improve_list.append(round(new_disk_list[-1] - old_disk_list[-1], 3))
            
            disk_rel_list.append( round( (float(row['tracemop old disk (byte)']) / float(row['tracemop new disk (byte)']))*100-100 ) )
            disk_X_list.append( round( float(row['tracemop old disk (byte)']) / float(row['tracemop new disk (byte)']), 3 ) )
            
            
            time_list.append(round(float(row['tracemop old time']) / 1000 - float(row['tracemop new time']) / 1000, 3))
            memory_list.append(round(float(row['tracemop old memory']) / 1000000 - float(row['tracemop new memory']) / 1000000, 3))
            disk_list.append(round(float(row['tracemop old disk (byte)']) / 1000000 - float(row['tracemop new disk (byte)']) / 1000000, 3))
            traces_list.append(events_and_monitors[row['project']]['monitors'])
            events_list.append(events_and_monitors[row['project']]['events'])

    plt.rcParams.update({'font.size': 18})
    fig, ax = plt.subplots(figsize=(10, 1))
    x = list(time_list)
    plt.boxplot(x, vert=False, widths=2)
    plt.xlabel('Time taken by prototype minus time taken by TraceMOP')
    out_file = os.path.join(args.paperdir, 'time_box' + '.png')
    plt.yticks([0],[''])
    fig.savefig(out_file, bbox_inches='tight')
    plt.close(fig)

    fig, ax = plt.subplots(figsize=(10, 1))
    x = list(memory_list)
    plt.boxplot(x, vert=False, widths=2)
    plt.xlabel('Memory used by prototype minus memory used by TraceMOP')
    out_file = os.path.join(args.paperdir, 'memory_box' + '.png')
    plt.yticks([0],[''])
    fig.savefig(out_file, bbox_inches='tight')
    plt.close(fig)

    fig, ax = plt.subplots(figsize=(10, 1))
    x = list(disk_list)
    plt.boxplot(x, vert=False, widths=2)
    plt.xlabel('Disk used by prototype minus disk used by TraceMOP')
    out_file = os.path.join(args.paperdir, 'disk_box' + '.png')
    plt.yticks([0],[''])
    fig.savefig(out_file, bbox_inches='tight')
    plt.close(fig)


def main(args):
    get_project_data(args)


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='Make overall stats plots.')
    parser.add_argument('statscsv')
    parser.add_argument('paperdir')
    args = parser.parse_args()
    main(args)
