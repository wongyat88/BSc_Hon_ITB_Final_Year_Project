### BSc Hon ITB (2017~2020) Final Year Project
### POS for restaurant with backend management system

Details can be found from the attached PDF report.

### Technology
- Java (Swing)
- Orcale SQL
- Python (Flask)
- Web-Socket
- APIs

### Details
#### Backend (Java)
- Table Manage (Include share table with other people function) (With Shop selection)
- Booking Manage
- Take Out Manange
- Order Manage (Include Food selection and combo set selection, special request from clinet like Less salf / oil etc.)
- Coupon Manage
- Generate QR Code for Phone to access the web-base order system for client manually order
- Create Invoice (PDF)
- Admin Page
  - Manger Page
    - Menu Management
    - Shop Management
  - Statistic Page (Some Pie Cart, Line Cart on profit and other statistic)
    - Expenses Page
    - Trends Page (Some Pie Cart, Line Cart on sold food / combo set and other statistic)
  - Material Page
    - Shop Material Page (Showing each shop material distribution and quantity. Able to add, update and delete)
  - Company page
  - Transaction page
  - Member Page
  - Coupon Page
  - User’s Coupon and Log Page
  - Staff Management Page
  - Kitchen Page (Able to show all the order made in shop based on the login selection)
    - User can click to complete the order
  - Locales (Chinese and English)
      
#### Frontend (Python)
- Main Page (For user to select shop and select create seat reservation / scan QR Code / Take Out Order)
- Order Page (Page to show different food / Combo for user to select and create order)
- Order Confirm Page
- Payment Page (Fake Credit Card Payment) (And will return a QR code for staff to scan)
- User Page (Coupon Redeem, Order History
- Responsive Web Design
- Locales (Chinese and English)

#### User acceptance test:

##### Scenario 1 – Login on web-based online application:

  Tester: 1
  
    1. Click the login button on the right top corner.
    2. Click the register button on the button of the login button.
    3. Input the information in provide box.
    4. After inputted the phone number, press the button” verification code”.
    5. Read the message pop up and input the code to provide box.
    6. Click “submit” after complete all the boxes.
    7. Input username and password in login page.
    8. Press the user on the right top corner.
    9. Success to login and go to the user page.
    
  Result: Success

##### Scenario 2

  Scan QR Code and order 1 food item and 1 set item with special requests:
  Tester: 1
  
    1. Open the phone camera and scan the QR Code.
    2. Menu order page pop up.
    3. Click on “rice noodle” at the bottom part.
    4. Select one food item and press “add to cart”
    5. Select some special requests and press confirm
    6. Confirm the shopping cart has update the order items
    7. Click “set” at the bottom and set page show out.
    8. Select one set item and press “add to cart”
    9. Set item selection page show out.
    10.Select the items and add special request into it.
    11.After that, press “Confirm” button
    12.Press the confirm order button at the shopping cart.
    13.Check the order item record list, is the order item as same as the tester selected.
    14.Press confirm order.
    15.Press “invoice” button, it should show the correct order items just added.
     
  Result: Success

##### Scenario 3 – Redeem coupon:

  Tester: 1
  
    1. Click the login button on the right top corner.
    2. Input username as “qwe” and password “qwe” in login page.
    3. Press the user on the right top corner.
    4. Press “Redeem coupon”
    5. Select one coupon and press “redeem”
    6. Press the user on the right top corner.
    7. Press “User’s Coupon”
    8. Check that is there has the coupon which just redeemed.
    
  Result: Success

##### Scenario 4 – Add new order in GUI system:

  Tester: 1
  
    1. Login the Common staff column by user ID “2” and password “qwe”
    2. Press on label “t11” and press “add” on the right-hand side panel
    3. Input “2” in the box and press “ok”
    4. Confirm the label “t11” change to yellow
    5. Confirm the right-hand side table has “t11a” order
    6. Repeat step 2 to 3 again
    7. Confirm the label “t11” change to red
    8. Confirm the right-hand side table has “t11b” order
    
  Result: Success
