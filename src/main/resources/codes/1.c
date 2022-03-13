
#include <stdio.h>
#define N 10
int main(){
	int a[N]={1468,100,4,9,10,18,6,39,1,123},i=0;
	int min,max;
	int length=N;
	if(length%2!=0){
		max=min=a[0]; 
		i=1;
	}else{
		max=a[0]>a[1]?a[0]:a[1];
		min=a[0]<a[1]?a[0]:a[1];
	}
	for(;i<length-1;i+=2){
		if(a[i]<=a[i+1]){ 
			if(a[i]<min)
				min=a[i];
			if(a[i+1]>max)
				max=a[i+1];
		}else{
			if(a[i]>max)
				max=a[i];
			if(a[i+1]<min)
				min=a[i+1];
		}
	} 
	printf("max=%d,min=%d\n",max,min);
	return 0; 
}