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
| Correctly Classified Instances | 94.0473%	| 93.9693%	| 94.0473% |
| Incorrectly Classified Instances | 5.9527% | 6.0307% | 5.9527% |
|Kappa statistic|	0.8206|	0.8156|	0.8207|
|Mean absolute error|	0.0595|	0.0603|	0.0595|
|Root mean squared error|	0.244|	0.2456|	0.244|
|Relative absolute error|	17.1713%|	17.3963 %|	17.1713%|
|Root relative squared error|	58.61%	|58.9927 %|	58.61%|
|Total Number of Instances|	3847	|3847| 	3847|

   ### Load data in R from mysql
   ```
   install.packages("RODBC") library(RODBC)
   ``` 
   * Build connection 
   ```
   mysql channel=odbcConnect("mysqlodbc", uid="root", pwd="123456")
   ``` 
   * Select all tables
   ```
   sqlTables(channel)
   ```
   * Manipulate data with sql
   ```
   library(sqldf)
   accdata = sqlQuery(channel,"select act,position,data,t_min,t_max,t_mcr,t_sttdev,t_mean,t_rms,t_iqr,t_mad,t_variance from  accdata",stringsAsFactors=FALSE)
   accdata2 = sqldf("select act,position,t_min,t_max,t_mcr,t_sttdev,t_mean,t_rms,t_iqr,t_mad,t_variance from accdata where act in ('Walking','Running') and position in ('Hand Fixed','Hand Swing')")
   ```
   * Save as CSV
   ```
   write.csv(accdata2,file="accdata.csv")
   ```
   ### Manipulate JSON with R
   ```
   install.packages("rjson") library(rjson)
   ```
   * Load in JSON
   ```
   grades=fromJSON(file = 'd:/20150524.json', unexpected.escape = "error")
   ```
   * encapsulate JSON data 
   ```
   min = c()
max = c()
mean = c()
for (n in grades){
  min = c(min,n$min)
  max = c(max,n$max)
  mean = c(mean,n$mean)
}
data = data.frame(min,max,mean)
```
     
