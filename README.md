# android-botnet
A POC SMS-controlled botnet I made in grad school.

Obviously, I'm not putting the actual payloads online, but here's the root infrastructure. SMSReceiver would do some trickery to steal an SMS message and see if it's actually a command for our slave. Splicer would parse that command and pass relevant info along to the correct payload. The payload will then execute the command.
