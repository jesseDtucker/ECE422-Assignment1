cls
javah -verbose -jni -d ./src/com/jetucker/ -cp bin/production/ECE422-Assignment1/ com.jetucker.InsertionSorter
call "C:\Program Files (x86)\Microsoft Visual Studio 12.0\VC\vcvarsall.bat"
"C:\Program Files (x86)\Microsoft Visual Studio 12.0\VC\bin\cl.exe" /I"C:\Program Files (x86)\Microsoft Visual Studio 12.0\VC\include" /I"C:/Program Files/Java/jdk1.8.0_25/include/" /I"C:/Program Files/Java/jdk1.8.0_25/include/win32/" /LD /D_WINDLL .\src\com\jetucker\lib_insertionSort.c /link /DLL /OUT:libcom_jetucker_InsertionSorter.dll