javah -verbose -jni -d ./src/com/jetucker/ -cp bin/production/ECE422-Assignment1/ com.jetucker.InsertionSorter
gcc -I"C:/Program Files/Java/jdk1.8.0_25/include/" -I"C:/Program Files/Java/jdk1.8.0_25/include/win32/" -std=c99 -shared -Wall -c ./src/com/jetucker/lib_insertionSort.c -o ./bin/production/ECE422-Assignment1/com/jetucker/libcom_jetucker_InsertionSorter.so
