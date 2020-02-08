# wikipedia

## Build and execute instructions
 - wikipedia is a maven project.
 - Folder names and structure - conventional to maven project.
 - Use `maven clean package` to build/package from the sources, which will create `wikipedia.jar` in `target` folder.
 - Run the application as mentioned below. It requires a path to input file (specification of input file, as per the problem statement) as the command line argument.

![alt text](https://raw.githubusercontent.com/prasegaonkar/wikipedia/master/execute.PNG) 
 
 

## Approach

Solution tries to simulate how search engine works.

The solution for this problem considers 2 workflows - content ingestion and search; And a special data structure - inverted index.

`Content ingestion` - Wikipedia content is read, processed and maintained in a specific data structure.

`Search` - A search string is processed and searched for, in inverted index. The results are sorted by rank.

`Inverted index` - Mapping of `token` to the 1) content where it is found, 2) and token metrices useful for search (like, token frequency and rank).


![alt text](https://raw.githubusercontent.com/prasegaonkar/wikipedia/master/IMG_1261.jpg)