User service is used to create and authenticate users.
User can be a buyer or seller.
Buyers can further place bid on prducts created by seller.
Seller can end auction and view result for product. Buyer and price.
 **REFER /Document/Steps to test word file for detailed execution instructions.

Implemented use cases

•As a Seller, I want to be able to register a new product for auction and specify a minimum bid.
   -/products/add with ask price
   -/product/all
•As a Buyer, I want to bid in an auction any number of times.
  -/product/bid
•	As a Seller, I want to be able to end the action and see the winner and their bid.
  -/product/end/{id}  returns an auction entry with buyerid and biddingprice.


Assumptions:
1.User always has enough balance to place a bid 
2.username i.e sellerName or buyerId is filled in request from incoming service or UI. so setting this in request.
3.Seller manually calls end auction for single product by product id.

Future expansion scope:
1.Handle seller dashboard
2.Buyer dashboard
3.Admin to manage all users,products,autions
4.Real time scheduling to start and end auction.Notify user and sellers of auction win and completion.
5.Payment service 
6.Delivery service.


List of API's related to user management
Unauthenticated API's
-Register user
-Authenticate user

Brief overview of security implementation:

1.To authenticate a valid request from user service using JWT token based authentication with spring security.
2.JWT token is generated if user exists and registered.
3.Pass this token in Authentication Bearer <tokenvalue>  of each call to Auction service via auction client.
4.Auction serivce checks the validity and  processes request only for valid tokens.
5.Role based authentication implemented for two apis's for roles ROLE_BUYER and ROLE_SELLER for add product,end auction and bid on product api
6. *** Make sure copy and place the tokens for seller with role ROLE_SELLER
Buyer with role ROLE_BUYER 
As we manully pass token while testing via postman in Authentication field.



Authenticated API's calling auction service and passwing generated token from /authenticate request in header of each request

-Add Products
-View Products
-Bid on a product
-end an auction

Only BUYER can call
-View Products
-Bid on a product

Only SELLER can call
-Add Products
-View Products
-end an auction
