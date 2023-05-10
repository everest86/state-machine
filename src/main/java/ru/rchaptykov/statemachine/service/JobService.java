package ru.rchaptykov.statemachine.service;

public interface JobService {
    String lockBprof(String jobId);
    String getAggregates(String jobId);
    String unlockBprof(String jobId);
}
