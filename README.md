# android-botnet
A Proof-of-Concept SMS-controlled botnet I made in grad school.

It's my understanding that this was one of the first - if not the first - open-source botnets for Android. The code I've uploaded is pretty clearly a POC. It's not the full code - I'm obviously not interested in posting functional payloads online. I also, by choice, didn't include a zero-day or way to get the user to install the code. I will testify some existed at the time I wrote this and that I successfully used them. I'll leave re-creating or finding them to you, Dear Reader.

Anyway, here's the root infrastructure. Essentially, SMSReceiver would do some trickery to steal an SMS message and see if it's actually a command for our slave. Splicer would parse that command and pass relevant info along to the correct payload. The payload will then execute the command.

As of April 2019, I do not believe the method I uploaded here will work anymore. I'll leave any updates as an exercise for the reader.

<i>Terms and conditions: This is provided for free and can be used in any manner you see fit, ONLY IF you clearly and prominently credit me as the original author (either by my Github handle @mrudy or my Twitter handle @keewenaw). If you use this, you take all responsibility for anything you may do with it or anything it does to your systems. Doing anything with this repo or code signifies acceptance of these conditions.</i>
