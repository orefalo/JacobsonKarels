# JacobsonKarels

Sample Jacobson Karels algorithm in Java.  
It estimates API latency and timeout based on sample metrics

# Use Cases

* Calculate average latency of an API call
* Cstimate the maximum timeout for a given API call

# Usage

Could be used by a query complexity anylizer to reject API calls which are too long to compute.

# Algorithm

Time is not a factor, sample latency is.  
 Put differently without samples, estimates are not moving.
 
From the sample latency, a deviation is calculated from the last estimate to the current value.  
Based on this deviation a new estimate and timeout value are computer.
 
 