/* Module name is Unix. */ 
%module Unix

%{ 
/* Include the POSIX operating system APIs. */ 
#include <unistd.h> 
%}

/* Ask SWG to wrap getlogin function. */
extern char* getlogin(void);

