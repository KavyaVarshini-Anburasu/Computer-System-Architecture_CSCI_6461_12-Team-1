**Team 1 Assembler Project 0 CSCI 6461 Section-12 Spring 2025**

How to Run with the Assembler.jar File

- Prerequisites: Ensure you have Java installed on your system. You can check by running:

java -version

- If Java is not installed, download and install it from the official Java website.

Run the Assembler:

- The SourceFiles/ folder contains pre-supplied assembly source files.

- The corresponding listing files for example test cases are available in the ExampleOutputFiles/ folder.

- Running the assembler will generate new listing and load files in the OutputFiles/ folder.

- To add new source files, place them in the SourceFiles/ folder. The assembler will generate corresponding _listing.txt and _load.txt files.

To run the assembler:

- java -jar Assembler.jar

- This will generate the listing and load files in the OutputFiles/ folder.

To remove generated files, run the cleanup script:

./cleanup.sh

How to Run without Using the Assembler.jar File

- Prerequisites: Ensure you have Java installed. Verify with:

java -version

- If Java is not installed, download and install it from the official Java website.

Compile the Code:

- Open your terminal and navigate to the project directory.

- Run the following command to compile all Java files:

javac *.java

Run the Assembler:

- The SourceFiles/ folder contains pre-supplied assembly source files.

- The corresponding listing files for example test cases are available in ExampleOutputFiles/.

- Running the assembler will generate new listing and load files in the OutputFiles/ folder.

- To add new source files, place them in SourceFiles/ and they will be translated into _listing.txt and _load.txt files.

To run the assembler manually:

java Main

- This will generate the listing and load files in the OutputFiles/ folder.

- To remove generated files, run the cleanup script:

./cleanup.sh



