#include <jni.h>

#include "com_jetucker_InsertionSorter.h"

JNIEXPORT jintArray JNICALL Java_com_jetucker_InsertionSorter_Sort
  (JNIEnv* env, jobject object, jintArray arr)
{
    jboolean *is_copy = 0;
    jsize length = (*env)->GetArrayLength(env, arr);
    jint* myArr = (jint *) (*env)->GetIntArrayElements(env, arr, is_copy);
    
    for(int i = 0 ; i < length ; ++i)
    {
        // first find the largest element in [i, length)
        int maxIndex = i;
        for(int j = i ; j < length ; ++j)
        {
            if(myArr[j] > myArr[maxIndex])
            {
                maxIndex = j;
            }
        }
        
        int temp = myArr[i];
        myArr[i] = myArr[maxIndex];
        myArr[maxIndex] = temp;
    }
    
    (*env)->ReleaseIntArrayElements(env, arr, myArr, 0);
    return arr;
}

JNIEXPORT jint JNICALL Java_com_jetucker_InsertionSorter_GetMemoryAccesses
  (JNIEnv * env, jobject obj)
{
    return 0;
}