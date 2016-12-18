package com.java.samples.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FutureExecutor {

	public static void main(String[] args) throws InterruptedException {
		final int maxNumber = 10_000;
		final Runnable printNumTask = () -> {
			for(int i=0; i<maxNumber; i++)
				System.out.println("Num: "+i); 
		};
		final Runnable printSumTask = () -> {
			for(int i=0; i<maxNumber; i++)
				System.out.println("Sum:"+(i+i)); 
		};
		final Runnable printSubstractTask = () -> {
			for(int i=0; i<maxNumber; i++)
				System.out.println("Substract:"+(maxNumber-i));
		};
		ExecutorService cachedExecService = Executors.newCachedThreadPool();
		ExecutorService fixedExecService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		ScheduledExecutorService scheduledExecService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
		cachedExecService.execute(printNumTask);
		fixedExecService.execute(printSumTask);
		scheduledExecService.scheduleWithFixedDelay(printSubstractTask, 15, 15, TimeUnit.SECONDS);
		scheduledExecService.awaitTermination(60, TimeUnit.SECONDS);
		closeExecutors(cachedExecService, fixedExecService, scheduledExecService);
	}
	
	private static void closeExecutors(ExecutorService... executorServices){
		for(ExecutorService executorService : executorServices){
			executorService.shutdown();
		}
	}

}
