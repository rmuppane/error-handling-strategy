package com.example;

import org.kie.api.runtime.process.ProcessContext;
import org.kie.api.runtime.process.ProcessWorkItemHandlerException;

public class Script {
    public static void handleStrategy(ProcessContext kcontext) {
        String strategy = (String) kcontext.getVariable("strategy");
        ProcessWorkItemHandlerException pwihe = (ProcessWorkItemHandlerException) kcontext.getVariable("Error");
        pwihe = new ProcessWorkItemHandlerException(pwihe.getProcessId(), ProcessWorkItemHandlerException.HandlingStrategy.valueOf(strategy), pwihe.getCause());
        kcontext.setVariable("Error", pwihe);
    }

    public static void decreaseRetry(ProcessContext kcontext) {
        ProcessWorkItemHandlerException pwihe = (ProcessWorkItemHandlerException) kcontext.getVariable("Error");
        var retry = pwihe.getRetries() - 1;
        if (retry > 0) {
            pwihe = new ProcessWorkItemHandlerException(pwihe.getProcessId(), ProcessWorkItemHandlerException.HandlingStrategy.RETRY, pwihe.getCause(), retry);
            kcontext.setVariable("Error", pwihe);                
        }
        kcontext.setVariable("retry", retry);
    }
}