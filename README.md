# LooperAPI

<hr>

## Description    

This is Spring Boot REST backend application. Use file system as DB and store Logs of the different API actions.  
There are two main parts of the API contained in the `DataStructuresController` and in the `LooperController`.
First part represent some basic implementations of linear data structures. They are used to process simple algorithmic    
operations like: transforming input to CSV output, check input string is it valid palindrome, find longest palindrome from string input.    
Second part represent basic thread operations as: starting new thread, terminate thread by Id, terinate all not terminated threads, 
get threads details and get application logs.

<hr>

## Requirements       

Windows OS with partition (C:)         

<hr>

## Details      

The API works on port 8080 and can be hitted by client such Postman (or similar).    
The port is described in `application.properties` file as default.   
Application assume that the OS is Windows and there is (C:) disk available.     
On run will create folder on (C:) to store the data "C:\LooperAPI_DB"      
and will create inside text file "LooperAPI_DB"(if the file already exists, will replace it with empty file).      

<hr>

## Routes 

### Data structures routes: 

`Expect JSON object in the request body, with field: "numbers"    
"numbers" must be array of integers    
Return given integers from the list as comma separated values(CSV)    
@param inputJson example: "numbers": [1,2,3,4,5,6]`     
* __PUT /data-structures/csv-string__

`Expect query param: "palindrome"
Return boolean depends on check if input string is valid palindrome
@param palindrome query example: ?palindrome=ABCDFDCBA`               
* __GET /data-structures/palindrome-check__ 
 
`Expect query param: "palindrome-string"
Return the longest palindrome contained in the input string 
@param palindromeString query example: ?palindrome-string=ZABCBAVUIG`                         
* __GET /data-structures/longest-palindrome__

### Looper routes:     

`Return JSON object with the details of all the loopers`      
* __GET /looper/all-loopers-details__     

`Return the details of all logs as String representation`     
* __GET /looper/all-logs-details__     

`Expect JSON object in the request body  
Start new thread for looper and log his iteration details to the log file        
Return the details of the started looper in JSON format               
@param inputJson example:          
{           
   "loopsCount": 5, //Long                
   "sleepMilliseconds": 1000, //Long                
   "text": "Some text" //String             
}`          
* __POST /looper/start-new-looper__                            

`Expect thread Id (long or integer) as path variable                
Kill looper by his Id                
Return the details of the killed looper in JSON format                
@param threadIdStr example: 51`                
* __DELETE /looper/kill-looper/{thread-id}__                

`Terminate all threads and return the details of all killed loopers as JSON`          
* __DELETE /looper/kill-all-loopers__                  

