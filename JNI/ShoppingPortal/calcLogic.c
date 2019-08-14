#include<stdio.h>

extern int countTotal(int[],int);

int countTotal(int arr[],int len)
{
    int i,sum=0;

    for(i=0;i<len;i++)
    {
        sum+=arr[i];
    }

    return sum;
}

int countTax(int sum)
{
    int tax=0;

    tax=((sum*18)/100)+sum;

    return tax;
}