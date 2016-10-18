To see the necessary changes in an example for optimizations,
refer to the index below. Follow lines numbers in which files
and see before/after images in the analysis folder.

Avoid Creating Unnecessary Objects or memory allocations
	TodoActivity.java						96
	See: optimizedHeap.png
		 deoptimizedHeap.png
		 heap-after.tiff	 
		 heap-before.tiff
		
Avoid Internal Getters/Setters
	TodoActivity.java 						125
	
Use Static / Final where appropriate
	TodoProvider.java						20
	TodoActivity.java						32
	
Use float judiciously
	TodoActivity.java						67
	See: float-vs-integers.tiff
	
Views
	Splash.java								15
	See: hierarchy-after.png
		 hierarchy-before.png
	
Use standard libraries, enhanced for loop
	TodoActivity.java						108
	
Use Strictmode
	TodoActivity.java						144
	
Optimize the onSomething() classes
	Splash.java								9
	
Relativelayouts versus Linearlayouts
	TodoActivity.java						154
	See: hierarchy-after.png
		 hierarchy-before.png
	
Avoid Synchronization
	TodoProvider.java						46
	
Close Resources
	TodoProvider.java						91
	
Arrays are faster than vectors
	TodoActivity.java						104
	
Don't return entire table of data
	TodoProvider.java						70
	
Heap Usage
	TodoActivity.java						166
	See: optimizedHeap.png
		 deoptimizedHeap.png
		 heap-after.tiff
		 heap-before.tiff

Eclipse Memory Analyzer
	See: mat-after.tiff
		 mat-before.tiff		 

Memory Allocation
	See: allocations-after.png
		 allocations-before.png

Threads
	See: threads-after.tiff
		 threads-before.tiff

Method Profiling
	See: methods-after.tiff 
		 methods-before.tiff
		 optimized-MP-Traceview.png
		 deoptimized-MP-Traceview.png
	
Traceview
	TodoActivity.java						49
	See: optimized-MP-Traceview.png
		 deoptimized-MP-Traceview.png
		 optimizedDDMS.trace
		 deoptimizedDDMS.trace
		
Lint
	See:
		 lint-before.tiff
		 lint-perf-check-before.txt
		 lint-perf-list-before.txt
