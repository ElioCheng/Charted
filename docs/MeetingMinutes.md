# Date: 2024-11-22 Team: 16 (Sprint 4)

Present: Darren, Alex, Elio

Agenda:
Go over docs
Go over current build
Assign features

Notes:
Full improvements list:
Improve ranking page ui Elio
Reduce clutter on head2head page
Top 3 get trophies
Replace row text with custom composable
click or swipe image to pick winner Elio
Increase padding on ranking page Elio
Click on song -> go to spotify page Elio
Profile remove heart, add album cover Darren
Playlist setting in popover Darren
Textfield doesn't extend on login page Darren
Tab icons Suhk
Crossed swords for head2head
App logo Suhk
Colour scheme Suhk
If I hit play on a song, rank a few songs, and the same song comes back, it'll resume playing, which is a bit unexpected Alex
The selection of songs does not seem particularly intelligent, do it later

Documentation:
User docs are done
Need to do design docs and reflection
Add search to user docs

Features:
Song search (optional)
Favourite artist
Search for other user

Actions:
Profile search Alex

# Date: 2024-11-20 Team: 16 (Sprint 4)

Present: Darren, Alex, Elio

Agenda:
Go over the UI together
Discuss features for final sprint

Notes:
Suggestions from TA:
Playlist setting in popover
Click on song -> go to spotify page
Textfield doesn't extend on login page
App logo

Additional improve:
Improve ranking page ui
Reduce clutter on head2head page
click or swipe image to pick winner
Increase padding on ranking page
Profile remove heart, add album cover
Tab icons
Crossed swords for head2head

Features:
Favourite artist
Search for other user
Search bar
Enter username
List of users
Click on user
See their profile but whole personal ranking

Decisions:
Copy spotify colour scheme
Drop search function
Cap global ranking at 100
No cap on personal ranking size

Actions:
Start final documentation Elio

# Date: 2024-11-08 Team: 16 (Sprint 3)

Present: Darren, Alex, Elio, Suhk 

Agenda:

* Assign tasks for the sprint
* Serverless architecture
* Go over dataflow
* Global ranking algorithm

 Notes: Universal ranking idea:

* Divide users ranking into percentage cutoffs
* Assign points for each range
* Track's global rank is determined by sum of every user's points

 Decisions:

* No more server
* Probably still use ELO

 Actions:

* Set up supabase by this weekend Elio
* Head2head and personal rankings use supabase to retrieve data Alex
* Universal rankings Darren
* Connect sprint 1,2 features to database
* Search for songs, Filters for tags Suhk
* Profile ranking Elio

# Date: 2024-11-06 Team: 16 (Sprint 3)

Present: Darren, Alex, Elio 

Agenda: 

Features for this sprint 

Scope for our final release 

Feature priority 

Notes:

 Feature Priority: 

High:

* Personal Ranking
* Universal Ranking
* Head2Head
* Upload playlist
* Playlist mode or Everything mode for song pools
* Profile

 Low:

* Recommendations
* Matchup comments
* Social features

 Decisions:

* Have input field for playlist on signup
* Focus on high priority features for this sprint
* Effort level -\> 7h/week

 Actions:

* Query database by Friday Elio
* Server feature example (Profile) by Friday Darren
* Login update by Friday Suhk
* Head2Head scroll fix by Friday Alex

# Date: 2024-10-25 Team: 16 (Sprint 2)

Present: Darren, Alex, Elio, Suhk

Agenda: Briefly go over last meeting with suhk Go over our architecture Refactor Tests

Notes: Search feature next sprint Database planning

Decisions: Don't work on rankings this sprint

Actions: Upload playlist spotify Alex

# Date: 2024-10-23 Team: 16 (Sprint 2)

Present: Darren, Alex, Elio, Sukh

Agenda:

* Reflecting last sprint done
* What to do this sprint
* Server vs Serverless
* Database type

Notes:

* Lets start week 1
* Communicate for stuff in discord
* Reopen issues that still need work
* Using firebase

Decisions

* Use Relational (SQL) database
* KTOR http library

Actions

* Connect our head2head page to spotify api Alex
* Create remote server Elio
  * Connection to database
  * Setup database
* Login/account saving Suhk
* Universal rankings Darren
* Consolidate ViewModel and Model logic Darren
* Refine previous ui elements Everyone
* Create google cloud project Suhk

# Date: 2024-10-04 Team: 16 (Sprint 1)

Present: Darren, Alex, Suhk

Agenda: Who is doing what Ranking system Ranking pool

Decisions ELO ranking for global rankings "jump-up" system for personal rankings insert one song -\> binary search or add to priority queue for now, song pool will be user's playlist

Actions Scrape data from spotify api using kotlin - alex head 2 head page - suhk ranking page - suhk profile page - darren recommendations page - elio skeleton - Alex done

# Date: 2024-10-02, Team: 16 (Sprint 1)

Present: Darren, Elio, Alex, Sukh

Agenda: What are we doing this sprint Who is doing what

Notes Lets try to get our skeleton up and the data scraping possibly make mock data file to test other features Main features: head2head and personal ranking pages

Decisions Have next meeting on Friday Discuss ranking methods/systems Discuss ranking pools

Actions Scrape data from spotify api using kotlin head 2 head page personal ranking page skeleton - Alex backend skeleton