#Recommendations - Domain


This project is an ongoing demo which purpose is modeling the entire domain problem of a recommendation web application using Scala and functional domain modeling (inspired by Domain Driven Design).

##The Problem:
The domain modeling aspect of the project aims to address a few major difficulties I had throughout my experience(as well as my colleagues’) as a backend developer:

1. Getting the business requirement right.

2. Getting a coherent component layout.

3. In case there’s a client aspect(web, mobile or other services) to the requirement, getting our interface right.

4. Let's now add the complexity("better get it right the first time, painful redesign later") distributed system design incurs..

5. All of the above - before I dive into technical level design (module structure, data/communication model choices etc.).

6. Needless to say, every reiteration deriving from one of the above misunderstandings is waste of time prone so wouldn't it be great if it took little time too? 

##The Goal:
- Understanding what is required:
	* From the service - grosso modo(expands to requests/responses).
	* From the service consumer.
	* From the service by 	its consumer(s), again grosso modo.
- Understanding the ***problem domain***:
	- ***Contexts***: 
		* which actors do we have in this play: Authentication/Billing/Marketing and so on.

	- Per each ***context***:
		* What ***behaviors*** are we looking for(methods)?
		* which ***entites***(User/Account/Product) make our ***context***?
		* which ***value objects***(userName, authenticationToken, productId) make the ***entities***?
- Composing the service external interface(API):
	* Per ***behavior***: Request/Response members(did someone say ***value objects***/***entities*** ;) ?)

- Realizing the above in 20(small feature/service) to 60(entire skeleton) minutes
	
##What's a micro service? 
Micro services are popular as a paradigm for designing distributed systems and rightfully so but challenge us with their very definition(what exactly would suite a micro service). In this project I would like to demonstrate one possible take on this which is:

***context*** = ***service***

