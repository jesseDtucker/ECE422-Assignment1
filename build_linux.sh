clear
rm -r ./bin
mkdir -p ./bin/production/ECE422-Assignment1/
javac -sourcepath ./src/com/jetucker -d ./bin/production/ECE422-Assignment1/ -classpath ./bin/production/ECE422-Assignment1/ ./src/com/jetucker/Generator.java
javac -sourcepath ./src/com/jetucker -d ./bin/production/ECE422-Assignment1/ -classpath ./bin/production/ECE422-Assignment1/ ./src/com/jetucker/ISortVarient.java
javac -sourcepath ./src/com/jetucker -d ./bin/production/ECE422-Assignment1/ -classpath ./bin/production/ECE422-Assignment1/ ./src/com/jetucker/HeapSorter.java
javac -sourcepath ./src/com/jetucker -d ./bin/production/ECE422-Assignment1/ -classpath ./bin/production/ECE422-Assignment1/ ./src/com/jetucker/InsertionSorter.java
javac -sourcepath ./src/com/jetucker -d ./bin/production/ECE422-Assignment1/ -classpath ./bin/production/ECE422-Assignment1/ ./src/com/jetucker/Adjudicator.java
javac -sourcepath ./src/com/jetucker -d ./bin/production/ECE422-Assignment1/ -classpath ./bin/production/ECE422-Assignment1/ ./src/com/jetucker/Driver.java

javah -jni -d ./src/com/jetucker/ -cp bin/production/ECE422-Assignment1/ com.jetucker.InsertionSorter
gcc -I"/usr/lib/jvm/java-8-oracle/include" -I"/usr/lib/jvm/java-8-oracle/include/linux" -fPIC -std=c99 -Wall -shared ./src/com/jetucker/lib_insertionSort.c -o ./libcom_jetucker_InsertionSorter.so
