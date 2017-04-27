----------------------
Creator:
Patrick Houlihan
----------------------
Command Line Arguments
----------------------

Example:  java -jar spotify.jar

User will be prompted to enter in some song lyrics

Enter some lyrics:

User to enter in lyrics and hit enter, i.e.

Enter some lyrics: if i can't get it out of my mind

Program will return titles of song from Spotify that contain phrases/clause that exist in the entered text:

Enter some lyrics: if i can't get it out of my mind
Loading POS tagger from C:/Users/xilin/Google Drive/SQUANT/INFO/CODE/JARFILES/javaJars/stanford-postagger-full-2016-10-31/models/english-left3words-distsim.tagger ... done [2.4 sec].
Your Play List is Below:
https://open.spotify.com/track/7JeKXMQKm6GoLGTkNy2jZ0
https://open.spotify.com/track/3bkcmsP2pcRAczoFtWqntt
https://open.spotify.com/track/7m4HUtdXRUHEitLIqbVWxf

All dependencies have been packed into spotify.jar file, you can run from command line either Windows or Linux.

Program uses Stanfords Parts of Speech tagger to identify the most commonly used conjunction or prepositions that are starting words for phrases/clause and finds sequential combinations of at least 3 words between the next conjunction or prepositions