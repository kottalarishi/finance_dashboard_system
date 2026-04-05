# finance_dashboard_system
The finance_dashboard_system is to build the finance data processing and to provied the role based access control 

## Problem Statement <br>
Here in the  finance_dashboard_system there are  multiple finacial records and diffrent types of user's, the user's get access to the records based on their respective roles.

## Features <br> 
  ### The features of finance_dashboard_system are mentioned below <br>
- Role Based Access control  - VIEWER ,ANALYST,ADMIN <br>
- Jwt authentication and token generation   for register and login <br>
- Filter transaction - filter by category date and type. <br>
- User management - create,get,update(partial),updateRole,activate,Deactivate ,delete <br>
- Transaction management - create,get,getTransactions of user,delete, update(partial)  <br>
- Cascade delete - if we delete the user it also delete all the transaaction of users <br> 
  ### finance dashboard to track : <br>
   - Total income <br>
   - Total expenses <br>
   - Net balance <br>
   - Category wise total <br>
   - Recent activity <br>
   - Monthly  trends <br>
   - Monthly categiry wise total <br>
   ## Tech Stack <br>
   ### Technologies used to develope the finance_dashboar_system <br>
   - Programing language : Java <br>
   - FrameWork           : SpringBoot <br>
   - Database            : PostgreSql <br>
   - Migration           : Flyway <br>
   - Security            : Jwt Authentication <br>
   - Build tool          : Maven <br>
   - Testing Api's       : Postman <br>

   ## Steps to Run
    1 . Clone the repo
        git clone https://github.com/kottalarishi/finance_dashboard_system <br>
    2 . create a postgresSql database name 'finance_dashboard_system' <br>
    3 . provied application.properties in gitIgnore as example, create file application.properties and fill with your values <br>
    4 . Before running check the flyway it will create tables V6_ version is change the role intialized with my fileds so  please check into it  <br>

   ## Project Structure
   ####  In this backend application i have used layred architecture, where controller handles the requests,   repository interacts with the database and the business logic is written in service.
   ```text
   src/main/java/com/rishi/finance_dashboard_system/
   |-controller
   |-dto
   |-entity
   |-exception
   |-mapper
   |-repository
   |-security
   |-service
   |-serviceImplementation
   |-serviceInterfaces
   |-util
   ```
   - controller : The controller layer handles the HTTP requests from the frontend or we can called client, works with service layer and return the responses to the frontend or client. <br> <br>
   - dto  : Data transfer object it seems like a security layer which is used to transfer the data from frontend to database between the layers, helps in data exposure and clean code. <br> <br>
   - entity : Entity layer is used to map the java object to databse records.<br> <br>
   - exception: In this exception handling is implemented to handle errors over all the layers by using global exception handler  and provieded status codes with APiResponse.<br> <br>
   - mapper : Mapper is used to convert entity to dto and dto to entity while data is transferd from frontend to database and database to frontend.<br> <br>
   - security : in security implemented Jwt authentication, customUserdetailsand SecurityConfigurration <br> <br>
   - service : in the service i implemented the CustomerUserDetailaService to wrap user and authorities together <br> <br>
   - repository : The most imporatant layer is repository which interacts with the database where perform basic crud  operations from the repository using Jpa Repository.<br> <br>
   - serviceImplementation: The business logic is written in the service implementation layer and here we validate most of the edge cases and constraints.<br> <br>
 
  ## System Desgin
  ```
  The Finacne Dashboard system is desgined based on role based access .<br> <br>
  Multiple users interact with multoiple records based on their sepcific role.<br> <br>

  ### user Flow
   - User registration- On registration user will get VIEWER role on default (registration named as saveUser as Api)<br> <br>
   - the One who need to be admin should change their Role to ADMIN By using the flyway after it he can change role to VIEWER TO ANALYST OR ADMIN ,ANALYST TO VIEWER OR ADMIN.<br> <br>
   - User logs in then receives JWt token which contians email and role<br> 
   - user will send the token with request<br> 
   - After the request the token is validated on every single request <br>
   - @PreAuthorize annotation will check the role before request pass to controller  <br>
   - At last Service process the request and returns data as response <br>

   ## Transaction Flow
     - ADMIN will create the transaction with amount type category notes and update ,delte the records
     - ANALYST can view the records and fetch and filter transactions
    -  VIEWER can only see the sum summaries like totalIncome,net Balance totalExpense only 
     - Dashboard APis will return the aggregated data and recent summaries of transaction

  ## Access Control FLow
    JwtFilter-->SecurityConfiguration-->controller-->service-->Respository-->Response

  ## Data Relation
     One user has many transactions
     deleting the user will also deltes the transactions of user (Cascade.ALl)
     each transaction will belongs to one user

  ```
  
  ## API Overview
  The following edescribes about the APi of finance_dashboard system <br>
  
  ### AuthController related Apis <br>
  - POST /api/v1/login -->  After login it returns jwt token , role and email <br> 

    
   ## UserController APIs  
   - POST /api/v1/users/saveUser  : This api will save user in DB ----- public APi.     <br>                            
   - GET /api/v1/users/{id} : This api will fetch the deatils of user based on  id  only ADMIN   can access. <br>
   - DELETE /api/v1/users/delete/{id} : by using this APi the we can delete the user based on user id only ADMIN can Access <br>  
   - PATCH /api/v1/users/updateRole/{id} : Most Spefic API where after registration user will get default VIEWER role, so the role is changed after if needed  ADMIN can access. <br> 
   - PATCH /api/v1/users/partialupdate/{id} : This API allows to update the user data Partially Accessed only by ADMIN  <br> 

   ## TransactionController APIs

   - POST    /api/v1/transactions/addTransaction  : This APIS works to create the transaction with fields like amount type,category, notes by taking the userId <br>
   - GET     /api/v1/transactions/fetchByID/{id}  : we can fetch the transactions based on the transaction id which returns the transactionsResponse as data Accessed by ADMIN and ANALYST  <br>
   - DELETE  /api/v1/transactions/delete/{id}  :  we can delete users on the basis of userId and return message not the data accessed by the ADMIN  <br>
   - GET     /api/v1/transactions/getTransactions/{id} : Here this APIs make some diffrence fetch we can get the all transactions of user based on userId <br>
   - GET     /api/v1/transactions/filterTransactions  : Here we filter the transaction with specific date, category, type " not all the fields are required we can fetch individually or by group"  <br>
   - PATCH   /api/v1/transactions/partialupdate/{id} : This   API will update the transactions partiallly and returns the TransactionResponse ADMIN level  <br>

    ## DashboardController APIs 

    - GET    /api/v1/dashBoard/totalIncome/{userId}  :  This apis helps to calculate the totalIncome of the user based on userId as itreates over the all transactions and fetch only the Transaction which is INCOME type and add to totalINcome returns BIGDECIMAL type totalIncome  accessd by "all ROLES"   <br>
    - GET    /api/v1/dashBoard/totalExpense/{userId}  :   Here we calculate totalExpenses by itreating   all transactions  of user and add to toatlExpense and returns totalExpenses accessed by all ROLES  <br>
    - GET    /api/v1/dashBoard/netBalance/{userId}   : This api returns netBlance of the users by calling the totalIncome and totalExpenses methods and calculate netBlance(diffrence of BIGDECIMAL valuse returend bby methods)   accessed by all ROLES <br> 
    - GET    /api/v1/dashBoard/getCategoryWiseTotal/{userId} : here we get category wise total amount used collection, lamdas to calculate   "ADMIN " and "ANALYST "  <br>
    - GET    /api/v1/dashBoard/getMonthlyTrends/{userId}    This api returns total income plus total Expenses together     "ADMIN " and "ANALYST " <br>
    - GET    /api/v1/dashBoard/getRecentActivity/{userId}   TTo retrive the recent transaction returns the lastest  5 transactions if < 5 we get transaction present in the Db  accessed "ADMIN " and "ANALYST "  <br>
    - GET    /api/v1/dashBoard/getMonthlyCategoryWiseTotal/{userId} :   This returns monthly category wise amount by gropuing month and category accessed by  "ADMIN " and "ANALYST " <br>

  

    

  

     
  
  
  
   
  
   
   
   
