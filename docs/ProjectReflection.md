
## Reflection

### Software Engineering Practices
Throughout this project, our team adopted several software engineering best practices to ensure the success of our application. One practice that proved effective was the use of the **Elo algorithm** for calculating the global ranking. This method provided a fair and dynamic way to rank users based on their performance in the head-to-head matches. By using Elo, we were able to ensure that the ranking system remained balanced and accurately reflected users' preferences over time.

However, some practices didn't work as well for us. In particular, we spent a lot of time early on making our architecture clean and easy to use. While we did well in the beginning, as we added more features over time, we didn’t stick to our planned architecture as rigorously. This led to a messy and inconsistent structure that caused some confusion and difficulty in maintaining the codebase. In future projects, we would focus on maintaining the initial architectural vision and ensure that new features align with it to avoid complications down the line.

### Team Adaptation
As the project progressed, we had to make several adjustments in both our workflow and communication. Initially, we underestimated the complexity of integrating various third-party APIs, especially **Spotify's API**. As we encountered difficulties with its functionality and frequent updates, our team became more agile in troubleshooting and rethinking our approach. We divided tasks more effectively and focused on fixing critical issues in the core features before tackling minor ones.

In retrospect, a more defined version control and issue-tracking system would have helped streamline our development and prevented some delays. As a team, we adapted by regularly holding check-ins and ensuring everyone was aligned on the project’s priorities.

### What Would I Do Differently in the Next Project?
In our next project, we would focus more on the **API integration** phase early on. Since working with external APIs introduced significant challenges in this project, getting a better understanding of the limitations and constraints of the tools we use will be essential for planning. We would also allocate more time for testing and debugging features as we develop them, especially when integrating external services.

Additionally, we would put more emphasis on **architecture**. It's crucial that every team member understands the chosen architecture clearly, knows how to use it, and actually follows it during implementation. In this project, we initially put effort into designing a clean and flexible architecture, but as the project progressed, we struggled to maintain consistency. In our next project, we will make sure to allocate time for team discussions to ensure everyone is on the same page regarding the architecture, and we will enforce its consistent usage throughout the development process. This will help us avoid confusion and prevent the codebase from becoming messy as new features are added.

### Technical/Design Challenges

#### Design/Solution We Are Proud Of
One solution we are particularly proud of is the design of the **ranking system** and how we integrated it seamlessly into the gameplay. The use of the **Elo algorithm** ensured that our rankings were dynamic and fair, reflecting user preferences over time. We designed the system to update in real-time as users played the head-to-head matches, which added an exciting and engaging element to the game. This solution brought a level of interaction that was both effective and satisfying for users.

#### Most Difficult Technical Challenge
The most difficult technical challenge our team faced was **querying the sorted personal ranking songs**. As users played the head-to-head matches, their song rankings were constantly changing, and maintaining an up-to-date sorted order in the database became problematic. Moving songs around in the database efficiently was challenging, as we had to keep the data synchronized and ensure accurate sorting without degrading performance. We spent considerable time brainstorming and testing solutions, and ultimately settled on a more optimized querying approach that minimized overhead.

Another major challenge we faced was working with the **Spotify API**. Initially, we could access a 30-second demo of each song, which was integral to the head-to-head gameplay experience. However, in the last release, Spotify updated its API, and we lost this feature. We had to quickly adapt and find alternative ways to present snippets of the songs, which added significant stress as we neared the final stages of the project.

### Conclusion
Overall, this project was a great learning experience. We were able to implement a solid ranking system and work through several challenges, particularly with third-party services and real-time data synchronization. By adapting our approach and working together as a team, we managed to deliver a functional and enjoyable app. Going forward, I will apply the lessons learned here to improve my workflow and technical decision-making in future projects.
