## Purpose

People have an inclination to compare, rank, and debate the quality of subjective works such as books, films, visual art, and music. Debates ensue in various situations: amongst friends in person or in online forums against strangers. In order to facilitate discussion and in an honest attempt of creating the closest possible thing to an objective ranking of music, we have chosen to build an Android application that allows users to create personal rankings of songs and albums using a one-on-one matchup game. The game works as follows: two options are presented, the user picks which one they prefer, and then the choice changes or maintains the user’s personal ranking. In addition, all users’ personal rankings of songs and albums contribute to universal rankings of each respective category. The application will also allow users to view others’ rankings and to comment their opinions on matchups and specific works. The inspiration for this idea comes from www.flickchart.com, which does something similar exclusively for films.

## Background

The typical users are music enthusiasts and critics, who want a method to rank songs in comprehensive lists. The user also wants a platform on which to discuss and share their music opinions and rankings with others that also share this passion. These users may enjoy ranking for fun, for the sake of realizing their own taste, or to share their “superior” opinion with others. We can observe online personalities, such as Anthony Fantano or Shawn Cee, that have amassed an audience by sharing their opinions, sometimes in the form of rankings. These lists are varied in their themes and criteria; this will play a key role in some of the features described below. We observe that people value these opinions by giving time to view these videos and even comment on their agreements and more importantly, disagreements. Why not allow the unsung music enthusiast to interactively participate in this?

A problem is that current ways to rank music aren't effective. Number of listens doesn't necessarily determine if a song is better than another, and rating systems aren't effective because songs don't get a unique score as they are individually ranked by users. Hence, there will be multiple songs with the same score, which doesn't show which song is better. The problem with these methods is that these metrics are isolated to each song and don't consider how each song relates to others. The solution of ranking via one-on-one matchups solves this problem and makes it easier for people to decisively rank music. 

By creating this application, we are valuing everyone’s opinion by giving them their own personal rankings, view the rankings of users whose opinions they value, and by giving them the opportunity to contribute to the community’s universal rankings.

[persona-Jamie_Gamer.pdf](images/persona-Jamie_Gamer.pdf)

## Requirements

**Personal Tracks Ranking** + **Personal Album Ranking** :

As mentioned, one of the core features of the project is for the user to have a personal ranking that we will store in a cloud database (once the user registers). We will start with Spotify's top ranking songs or albums (depending on which they choose to rank at the time) and once their personal ranking is large enough, we will use that data to give match-ups of songs or albums based on the genres and artists found in it. Obviously this can result in the user being offered songs they have not yet listened to (or do not wish to listen to), so there will be the option to skip on ranking either one of the options or even both.

The user will also have the option to display the top items in their ranked list while they are ranking. They can access their complete ranking, but it will take them away from the "game" screen.

**Register & Login Capability** :

If the user wishes to save their rankings with us and continue to add to it, they will have to register as a user. This means we need to have user register and log-in capabilities implemented in our application.

**Spotify Linking & Using User's Playlists**:

If the user decides to link their Spotify account, we will use the Spotify API to access their playlists and use those tracks or saved albums for the matchups. This will help in giving the user match-ups for music that they have already listened to, which will help keep them engaged. The user will have the ability to turn off this option if they wish to be given match-ups for other music as well. We can build upon this for users of other streaming platforms, such as Apple Music, Amazon Music, and YouTube Music later on. However, our initial focus is to build a minimum viable product that caters to Spotify users because the platform dominates the music streaming market as of September 2024

**Filters**:

Another feature we will implement is the ability to filter certain tracks and albums based on artists, genres, time of release, etc. so that the user can customize what they are offered in the matchups. For instance, the user may want to only rank earlier Linkin Park albums instead of the later ones, so they will have the option to do so by specifying the artist and time span. When they filter, the personal ranking display will adjust as such to only show the filtered ranking until the user wishes to revert to their overall personal ranking or change to another filter. They can also apply these filters when they are viewing their own personal rankings or the universal rankings. This is an important feature as it allows for users to rank what they wish to and contributes to addressing a key part of our problem: creating multiple rankings based on user needs. It is also just easier to compare and rank in regards to similar criteria, allowing for a less frustrating experience for the user.

**Search to Add to Personal Ranking** :

Another aspect of customization will be the search feature that will allow the user to search for specific tracks and albums they wish to add to their respective rankings. When they complete the search, we will implement an algorithm to insert the album or song into the appropriate ranking by having the user compare it to a few items in the ranking. In the case of large track charts, we may wish to adjust this into an optional queue as to not burden the user with too many match-ups with the same title. The items in the queue will be given priority over other items.

For albums, there will be an option to add every track to their tracks ranking if they wish to do so. They can also search their personal ranking or the universal rankings to view where certain titles rank.

**Searching Rankings** :

We want the user to be able to search both personal and universal rankings for specific items in order to locate where they rank.

**Universal Tracks Chart** + **Universal Albums Chart** :

There will also be the universal ranking, which will be constructed using the results of all personal rankings. This creates a fun community element to the application in which every user makes an impact on.

**Music Recommendations** :

Based on the highest items in the user's ranking, we wish to create a recommendations list for what the user may want to listen to next and once they have, they will have to add those items from the list into their rankings.

**Album Match-up Forums** :

Now when it comes to the aspect of discussion, it is likely overkill to have a discussion board for every possible matchup of tracks, so this will be limited to a comments section just for each album matchup. Most discussions in music often revolve around the creativity and talent of artists, so to have a forum based on a complete work (an album) of an artist makes much more sense than to have a discussion board comparing two individual songs.

**User Profiles** + **Following Other Users** :

To enhance the community aspect of our application, we also want the ability for users to follow other users. This could be for friends to compare their rankings and also apply filters to, for instance, see what songs their friend likes best from a specific genre. Or for fans of a particular celebrity or influencer to see what type of music they're into. Although generic at first, this can be enhanced later to allow for users to share what music they are listening to lately and to display what music has been climbing their personal rankings.

**Rough Ideas for the Future:**

_Social Page_

As was mentioned above briefly, the idea of facilitating interaction between users excites as it works towards creating a community for music enthusiasts as discussed in the _Background_ section. This page would allow users to share match-ups they found intriguing, music they are listening to, or share opinions about music with their followers. Followers can then leave comments under these posts.

_Match-up of the Day_

Another community aspect will be the "Match-Up of the Day" feature, where a particular match-up of two albums or tracks will be chosen by the developers based on some sort of theme. Each user will get to participate in a community vote, where the winner will advance. It is still undecided whether we want this to be a part of a monthly bracket, where one winner is crowned or a running contest, where the winner keeps advancing until they lose out to another track or album.

_Artist Ranking_

Even for a personal ranking, top tracks for a user will be hard to pin down as some can be incomparable. For instance, one song may get the user amped up while the other is of a pensive tone, which can be hard to compare as both may be just as great to the user for different reasons. That is why we have the filters feature and album rankings so there is more of an "apples to apples" comparison per se, which we expect to be the major uses of the app. In addition to this, we are also contemplating an artist ranking feature on top of this that may be included because it will probably be easier and of interest to users to see what artists they value most and who other users do too.