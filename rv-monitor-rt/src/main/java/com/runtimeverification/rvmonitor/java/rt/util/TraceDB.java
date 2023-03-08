package com.runtimeverification.rvmonitor.java.rt.util;

import java.util.List;
import java.util.Map;

public interface TraceDB {

    public void put(String monitorID, String trace, int length);

    public void update(String monitorID, String trace, int length);

    public void createTable();

    public abstract int uniqueTraces();

    public abstract int size();

    public abstract List<Integer> getTraceLengths();

    public abstract Map<String, Integer> getTraceFrequencies();

    public abstract void dump();

}
