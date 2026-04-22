Programme that implements A* algorithm in order to solve 15 puzzle using different heuristics. To test on randomly generated permutation use commands:  
javac Test100.java  
javac TestNonInc.java  
for compilation and:  
java Test100  
java TestNonInc  
to launch tests.  
In some instances algorithm might exceed heap space due to too many generated neighbours. To prevent this you might use options "-Xms$g -Xmx$g" where '$' is how much ram space you are willing to assign initially and maximally to the programme.
