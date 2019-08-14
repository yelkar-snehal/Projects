#include <stdlib.h>
#include <dlfcn.h>//for dynamic link loader
#include "App.h"//jni

JNIEXPORT jint JNICALL Java_App_calcBill(JNIEnv *env, jclass jobj, jintArray arr)
{
    int i,sum=0;
    jsize len = (*env)->GetArrayLength(env, arr);

    void *p= NULL; // Pointer for storing address of shared ubject file
    int (*countTotal)(int[], int)= NULL; // Function ptr of the function to be called from Pure C's so file
    

    jint *params = (*env)->GetIntArrayElements(env, arr, 0);
    p = dlopen("/home/snehal/Desktop/Practice/Projects/JNI/ShoppingPortal/calcLogic.so", RTLD_LAZY);
    if(!p)
    {
        printf("Unable to load library: %s\n",dlerror());
    }

    countTotal= dlsym(p,"countTotal");
   

    if(countTotal == NULL)
    {
        printf("Unable to get address of function: %s\n",dlerror());
    }

    sum= countTotal(params, len);

    (*env)-> ReleaseIntArrayElements(env, arr, params, 0);
   dlclose(p);
    return sum;
}

JNIEXPORT jint JNICALL Java_App_calcTax(JNIEnv *env, jclass jobj, jint sum)
{
    int gst=0;

    void *p= NULL;
    int (*countTax)(int)=NULL;

    p = dlopen("/home/snehal/Desktop/Practice/Projects/JNI/ShoppingPortal/calcLogic.so", RTLD_LAZY);
    if(!p)
    {
        printf("Unable to load library: %s\n",dlerror());
    }

    countTax=dlsym(p,"countTax");

    if(countTax == NULL)
    {
        printf("Unable to get address of function: %s\n",dlerror());
    }

    gst= countTax(sum);

    return gst;



}

/*RTLD_LAZY
Perform lazy binding. Only resolve symbols as the code that references them is executed. If the symbol is never referenced, then it is never resolved. (Lazy binding is only performed for function references; references to variables are always immediately bound when the library is loaded.)
RTLD_NOW
If this value is specified, or the environment variable LD_BIND_NOW is set to a nonempty string, all undefined symbols in the library are resolved before dlopen() returns. If this cannot be done, an error is returned.
Zero or more of the following values may also be ORed in flag:
RTLD_GLOBAL
The symbols defined by this library will be made available for symbol resolution of subsequently loaded libraries.
RTLD_LOCAL
This is the converse of RTLD_GLOBAL, and the default if neither flag is specified. Symbols defined in this library are not made available to resolve references in subsequently loaded libraries.
RTLD_NODELETE (since glibc 2.2)
Do not unload the library during dlclose(). Consequently, the library's static variables are not reinitialized if the library is reloaded with dlopen() at a later time. This flag is not specified in POSIX.1-2001.
RTLD_NOLOAD (since glibc 2.2)
Don't load the library. This can be used to test if the library is already resident (dlopen() returns NULL if it is not, or the library's handle if it is resident). This flag can also be used to promote the flags on a library that is already loaded. For example, a library that was previously loaded with RTLD_LOCAL can be reopened with RTLD_NOLOAD | RTLD_GLOBAL. This flag is not specified in POSIX.1-2001.
RTLD_DEEPBIND (since glibc 2.3.4)
Place the lookup scope of the symbols in this library ahead of the global scope. This means that a self-contained library will use its own symbols in preference to global symbols with the same name contained in libraries that have already been loaded. This flag is not specified in POSIX.1-2001.*/
