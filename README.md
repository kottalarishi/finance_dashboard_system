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
        git clone https://github.com/kottalarishi/finance_dashboard_system
    2 . create a postgresSql database name 'finance_dashboard_system'
    3 . provied application.properties in gitIgnore as example, create file application.properties and fill with your values
    4 . Before running check the flyway it will create tables V6_ version is change the role intialized with my fileds so 
       please check into it 

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
  The Finacne Dashboard system is desgined based on role based access .
  Multiple users interact with multoiple records based on their sepcific role.

  ### user Flow
   - User registration- On registration user will get VIEWER role on default (registration named as saveUser as Api)
   - the One who need to be admin should change their Role to ADMIN By using the flyway after it he can change role to VIEWER TO ANALYST OR ADMIN ,ANALYST TO VIEWER OR ADMIN.
   - 
  
  
  
   
  
   
   
   
