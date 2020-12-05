# AI_ntua
Lab project in NTUA (2018) for the Artficial Intelligence course 7th semester


# Τaxibeat
Taxibeat is an application written in Java and Prolog . It's main purpose is to find the closest taxi to the client's coordinates . After finding the closest taxi the application tries to find the best route to the client's destination filtering informations such as traffic , traffic lights , open roads , rush hour , accidents etc.
This application was impelented as part of the Artificial Intelligence course (7th semester) for the National Technical University of Athens.


## Input data 
The actual input data : map coordinates , streets and road information such as road lanes , traffic jam , road direction etc are part of the https://www.openstreetmap.org dataset .

## Application Launch time efficiency and output examples 
Below you can find an example usage of the current application . In order to run this application you should of course compile it using javac . After this just type : java nodes.csv client.csv taxis.csv example2 in a terminal window . In the above execution command (which stands as an example) nodes.csv stands as the map file which contains the coordinates of our nodes , client.csv stands as the file with the coordinates of client , taxis.csv stands as the file with the coordinates of the available taxis and example2 stands as the name of the output file (kml) . The output file will be a map with the sortest available routes from all taxis to the client . In every output file the green route stands for the closest taxi route to the client .

### Application's execution example :

![alt text](https://github.com/manzar96/ai_ntua/blob/master/images/runt.jpg)

### Application's time efficiency :

![alt text](https://github.com/manzar96/ai_ntua/blob/master/images/time.jpg)

### Application's outputs examples :

**Example 1 :**

![alt text](https://github.com/manzar96/ai_ntua/blob/master/images/ex1.jpg)


**Example 2 :**

![alt text](https://github.com/manzar96/ai_ntua/blob/master/images/ex2.png)


Finally we implented an A* alternative at which the algorithm finds all the closest paths . In order to implement this alternation of A* we changed the algorithm in a way that it does not stop if it finds the first sortest path. Moreover we used doubles in order to calculate distances . This selection made our calculations too acurate . As a result , we had paths with a difference in distance of a meter or less . In order to solve this problem we accepted not only the closest distance but also distances that are around 5 % greater than the sortest distance (5 % assumption is made with respect to the average distance , in Athens it is about 3-5 kilometers).

**Image of alternative sortest routes :**

![alt text](https://github.com/manzar96/ai_ntua/blob/master/images/p1.png)