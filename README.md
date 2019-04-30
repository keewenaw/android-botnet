# android-botnet
A Proof-of-Concept SMS-controlled botnet I made in grad school.

It's my understanding that this was one of the first - if not the first - open-source botnets for Android. It's pretty clearly a POC, I didn't include a zero-day or way to get the user to install the code. I'll leave that to you. I'll also state that I assumed in my testing that an install would occur with the help of some social engineering, as the code works if the user has already accepted all permissions the app needs. As of April 2019, I do not believe my methods will work anymore. I'll leave any updates as an exercise for the reader.

Obviously, I'm not putting the actual payloads online, but here's the root infrastructure. SMSReceiver would do some trickery to steal an SMS message and see if it's actually a command for our slave. Splicer would parse that command and pass relevant info along to the correct payload. The payload will then execute the command.

<i>Terms and conditions: This is provided for free and can be used in any manner you see fit, ONLY IF you clearly and prominently credit me as the original author (either by my Github handle @mrudy or my Twitter handle @keewenaw). If you use this, you take all responsibility for anything you may do with it or anything it does to your systems. Doing anything with this repo or code signifies acceptance of these conditions.</i>
