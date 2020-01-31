## WORK IN PROGRESS ##

This code is part of a coding test from a job interview. The code base isn't finished yet, but it already gives a decent idea of what the final code will evolve into.

### Description of the coding test ###

You should implement one screen with a list of currencies. Link to designs in figma:
https://www.figma.com/file/cUsxw4zNAvU47ADDCJemBM/Rates

The app must download and update rates every 1 second using API
https://revolut.duckdns.org/latest?base=EUR

List all currencies you get from the endpoint (one per row). Each row has an input where you
can enter any amount of money. When you tap on currency row it should slide to top and its
input becomes first responder. When you’re changing the amount the app must simultaneously
update the corresponding value for other currencies.

Use any libraries and languages(java/kotlin) you want. Please, note that the solution should be production ready.

Video demo: https://youtu.be/omcS-6LeKoo

