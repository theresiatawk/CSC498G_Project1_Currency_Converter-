# CSC498G_Project1_Currency_Converter-

It is a small application that provides an easy conversion of currency values (LBP and USD) based on a the daily lira rate.
Currency Converter Application that has two Apis:
•	First API: it scrape the Lira's website and get the updated currency rate using PHP, this rate is returned to the front-end as JSON object. 
•	Second API: receive the amount to be converted and the currency and the currency rate, then save them in MySQL database.
In addition this application consists of two pages: 
•	First page is a Landing Page which will sent you to the next page after few seconds(splash screen) 
• Second page is the Calculator Pager. This page calls the first API onCreate() and calls the second API when the user clicks on the conversion button. 
 
