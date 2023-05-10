package ru.rchaptykov.statemachine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.rchaptykov.statemachine.service.JobService;

@RestController
public class JobController {
    @Autowired
    JobService jobService;

    @GetMapping("/exec/{jobId}")
    public String execJob(@PathVariable String jobId){
        String s = jobService.lockBprof(jobId);
        String aggregates = jobService.getAggregates(jobId);
        String s1 = jobService.unlockBprof(jobId);
        return s1;
    }

    @GetMapping("/lock/{jobId}")
    public String lock(@PathVariable String jobId){
        return jobService.lockBprof(jobId);
    }

    @GetMapping("/aggr/{jobId}")
    public String aggr(@PathVariable String jobId){
        return jobService.getAggregates(jobId);
    }

    @GetMapping("/unlock/{jobId}")
    public String unlock(@PathVariable String jobId){
        return jobService.unlockBprof(jobId);
    }
}
