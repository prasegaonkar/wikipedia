# wikipedia

## Build and execute instructions
 - `wikipedia` is a maven project.
 - Folder names and structure - conventional to maven project.
 - Use `maven clean package` to build/package from the sources, which will create `wikipedia.jar` in `target` folder.
 - Run the application as mentioned below. It requires a path to input file (specification of input file, as per the problem statement) as the command line argument.

![alt text](https://raw.githubusercontent.com/prasegaonkar/wikipedia/master/execute.PNG) 
 

## Approach

Solution tries to follow how search engine works.

The solution for this problem considers 2 workflows - `content ingestion` and `search`; And a special data structure - `inverted index`.

`Content ingestion` - Wikipedia content is read, processed and stored/kept in a specific data structure.

`Search` - A search string is processed and searched for, from the specific data structure. The results are sorted by rank.

`Inverted index` - Mapping of `token` to the 1) content where it is found, 2) and token metrices useful for search (like, token frequency and rank). 


### Ingestion Workflow

Content goes through these workflow stages/steps 
 - `Tokenization` is splitting the content at word boundary.
 - `StopWordsFiltering` remove common words which contribute very less significantly to the `intent of content`. Examples - is, are, has, what, when. 
 - `Stemming` or lemmatization process extracts the root word. Example - 'walk' is root word for 'walking' and 'walked'.
 - `Indexing` is last and very important step. It involves creating and saving the content in the format that makes it efficiently searchable.


### Search Workflow
Search query goes through the stages of `tokenization`, `stop words filtering` and `stemming`, followed by `searching` for the content against the matching tokens, in inverted index. 

The results are at the last stage sorted (based on either the matching 'token count' or 'rank').

![alt text](https://raw.githubusercontent.com/prasegaonkar/wikipedia/master/IMG_1261.jpg)


## Tests and implementation considerations

 - There could be cases or boundary conditions which might have got missed or are not covered in the implemented solution. For such cases, this solution can be improved upon; starting by adding failing tests for such cases. 
 - Current supported behavior of this application is defined "entirely" by written tests.
