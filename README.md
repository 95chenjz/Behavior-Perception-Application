# Behavior-Perception-Application
Developed a steps-monitoring APP based on machine learning method on android platform.

This repository is built for documenting and reproducing the project in CAS, Beijing, 07/2016.

## Enviroment & Equipment
  * Cell Phone with Android operating system and acceleration sensors 
  * R v3.3.1 && RStudio v0.99.902 or above
  * Weak v3.9 or above
  * Eclipse IDE
  * JDK 1.8
  * Python 2
  * gp424win32

## Training Sample
  * Data fetched by acceleration sensors in cell phone and labeled by postures (still, walking, running, upstairs downstairs).
  * The frequency of data fetching is 64HZ. Every 2s-time window has a group of 128 acceleration data.
  * Save as CSV (accdata.csv).
  * Load in R with 
    ```
    read.csv（accdata.csv)
    ```
  * Load in Weak and save as arff.

### Features
  * min
  * max
  * variance = 1/length*sum(xi-x)^2 (0 < i < length)
  * mcr (mean cross rate)
  * stddev (standard deviation)
  * rms (root mean square)
  * iqr (interquartile range)
  * mad = sum(|xi-x|)/length
  
  ### Plots with original data (R)
  ```
    library(rjson)
    walking = fromJSON(accdata[1,3])
    plot(walking,ylim=c(7,15),pch=20,xlab="accdata",ylab="accvalue",col="red",type="o")
    title("Walking-128 acceleration data")
    plot(running,pch=20,xlab="accdata",ylab="accvalue",col="red",type="o")
    title("Running-128 acceleration data")
  ```
  ### Features plots (R)
    * Sort data according to acts and positions:
    
  ```
     accdata2 = sqldf("select * from accdata2 order by act,position")
     plot(accdata2$t_min,pch=20,xlab="accdata",ylab="accvalue",col="red",type="o")
  ```
    * Check the number of data in different positions:
   ```
   sqldf("select count(),act from accdata2 group by act") 
   abline(v=858, col="blue") 
   sqldf("select count(),act,position from accdata2 group by act,position") 
   adcount = sqldf("select count(*) count,act,position from accdata2 group by act,position") 
   temp = 0;
   for(c in adcount$count)   { 
   temp =temp +c; 
   abline(v=temp, col="black") 
   }
   ```
   ### Optimization (R)
   ```
   adcount = sqldf("select count(*) count,act,position from accdata2 group by act,position")
	 adcount2 = sqldf("select count(*),act from accdata2 group by act")

	 for(i in c(1:9)){
  	dev.new()
	  future1=paste(names(accdata2)[i+2],".png")
	  png(file=future1)

	  plot(accdata2[,i+2],pch=20,xlab="accdata",ylab="accvalue",col="red",type="o")
	  title(names(accdata2)[i+2])

	  temp = 0;
	  for(c in adcount$count){
		temp = temp +c;
		abline(v=temp, col="black")
	  }
	  abline(v=adcount2[1,1], col="blue")
	  dev.off()
	}
    ```
  
  ### Model testing in Weka
    * Keep all columns
    * Remove variance
    * Remove stddev
    
    
    
      |               | Keep all columns | Remove variance | Remove stddev |
      | ------------- | ------------- | ------------- | ------------- |
      | Correctly Classified Instances | 94.0473 %	| 93.9693 %	| 94.0473 % |
    

     
