# Table of Contents

1. [Concurrent Programming](#concurrent-programming---assignment-1)
2. [Public Chat](#public-chat---assignment-2)
3. [Quotes Management](#quotes-management---assignment-3)
4. [Choose Your Food](#choose-your-food---assignment-4)
5. [Public Blog - V1](#public-blog---assignment-5)
6. [Public Blog - V2](#public-blog---assignment-6)


# Concurrent Programming - Assignment 1

## Assignment Description

This assignment simulates a homework defense scenario involving students, a professor, and an assistant. The defense process is managed using concurrent programming techniques in Java.

## Simulation Details

### Participants

- **Professor:** Can oversee the defense of two students simultaneously.
- **Assistant:** Can oversee the defense of one student at a time.

### Defenses

- The professor and the assistant are available for 5 seconds from the start.
- Each student arrives at a random time between 0 and 1 second from the start.
- Each student defends for a random duration between 0.5 and 1 second.
- Defenses must be interrupted if the 5-second window expires.

### Behavior

- The professor waits for two students to start the defense.
- The assistant starts immediately when a student is ready.
- Students are randomly assigned to defend with the professor or the assistant (50% chance for each).

### Grades

- Each student receives a grade between 5 and 10.

## Constraints

- A student cannot defend their homework more than once.
- No defenses can occur after the 5-second window, even if started before it ends.
- The same student cannot be overseen by both the professor and the assistant.

## System Input

- **N:** The number of students.

## System Output

For each student who arrives or is interrupted in the defense, the following information is printed:

Thread: <Student thread name>
Arrival: <Time of student's arrival from the start of the defense>
Prof: <Assistant or professor thread name>
TTC: <Time taken to review the homework>:<Start time of the defense>
Score: <Grade received from 5 to 10>


## Implementation Details

- **Threads:** Created using a thread pool.
- **Professor:** Uses a `CyclicBarrier` to wait for two students to start the defense.

---

# Public Chat - Assignment 2

## Assignment Description

This project is a simple chat application developed using Java sockets that allows for public communication among all connected users. The application supports multiple clients, enforces unique usernames, and maintains a history of messages. It also includes a mechanism for message censorship.

## Features

### Public Communication

- All connected users can send and receive messages in a shared chat room.

### Unique Usernames

- Each client must have a unique username.

### Message History

- Stores and displays the last 100 messages.

### Message Censorship

- Predefined set of censored words, where offending words are partially masked.

---

# Quotes Management - Assignment 3

## Assignment Overview

This assignment is designed to allow users to save and view quotes, including the names of their authors. It provides a straightforward interface for entering new quotes and displays all saved entries in an organized list. Additionally, the application includes a feature that displays a "quote of the day" within the form for entering new quotes. This feature serves as a daily source of inspiration for users.
## Scenario

### Form for Entering a New Quote & List of Saved Quotes

- A GET request to the path `/quotes` returns an HTML page containing:
  - The form for entering a new quote
  - The quote of the day
  - All saved quotes

- By clicking on “Save Quote,” the entered data is sent to the server using the POST method of the HTTP protocol to the path `/save-quote`. 
- The server saves the submitted data and, in response, redirects the client to the location `/quotes` so the client can see their saved quote.

## Technical Requirements

### System Architecture

- The system is divided into two web servers (main and auxiliary-second), which communicate via the HTTP protocol.
- Both applications run on localhost but listen on different ports.

### Main Server

- **Responsibilities**:
  - Inputting quotes
  - Saving quotes
  - Displaying quotes to the client

### Auxiliary-Second Server

- **Responsibilities**:
  - Provides an endpoint that returns the quote of the day
  - Selects one quote from an existing set of quotes in an arbitrary manner and returns it as a JSON response
- **Communication**:
  - This server is considered an internal server and does not communicate directly with the client.
  - The main server requests the quote of the day from the auxiliary server to form a response for the client.

### Communication

- **Protocol**: HTTP
- **Restrictions**: 
  - The use of ready-made libraries (HTTP clients) is not allowed.
  - Communication between the servers must be enabled independently using Java Sockets.
  - Only libraries for parsing JSON are allowed.

## Implementation Details

### Main Server

- **Endpoints**:
  - `GET /quotes`: Returns an HTML page with the form for entering a new quote, the quote of the day, and all saved quotes.
  - `POST /save-quote`: Saves the submitted quote and redirects to `/quotes`.

### Auxiliary-Second Server

- **Endpoints**:
  - `GET /quote-of-the-day`: Returns a randomly selected quote from an existing set as a JSON response.

---

# Choose Your Food - Assignment 4

## Assignment Overview

**Choose Your Food** is a web application designed for a hospitality and food delivery service catering to employees in IT companies. This application allows users to select meals for each working day from a set of predefined options. Additionally, it provides an overview of all selected meals to help the client prepare the required quantity of meals for the upcoming week.

## Features

- **Meal Selection**: Users can choose one meal for each working day from a set of three options.
- **Order Confirmation**: Users receive a confirmation page after successfully selecting meals.
- **Order Overview**: Clients can view the quantity of ordered meals grouped by day.
- **Order Management**: Clients can delete all orders to reset the selection process for all users.
- **Session Management**: Users can select meals only once per session.
- **Password-Protected Pages**: Access to the order overview page is secured with a password.

## Technical Requirements

- **Servlet API**: The application is built using the Servlet API.
- **Data Loading**: Meals are loaded from text files (`monday.txt`, `tuesday.txt`, etc.) at server startup.
- **Session Handling**: The application ensures that users can only select meals once per session.
- **Instance-Level Storage**: The current state of the application (loaded meals and selected meals) is stored at the servlet instance level.
- **Password Security**: Access to the selected meals page is secured with a password retrieved from `password.txt` during servlet initialization.

---

# Public Blog - Assignment 5

### Assignment Overview

This project is a public platform for posting news articles and journalistic pieces. The platform is designed to be open to everyone, allowing any user to add new posts, view existing posts, comment on them, and read comments from others.

### Scenario

**List of Published Posts:**

- The main page displays a list of all existing posts.
- By selecting a post, the user is shown the full content of that post along with its comments and a form for adding a new comment.
- A "New post" button on the main page displays a form for creating a new post.

**Post Creation Form:**

- After entering and submitting the form, the form disappears, and the newly added post appears in the list of all posts.

**Post Content Display:**

- By clicking on a post, the content of the post is displayed on the same page, including the title, publication date, author, text, comments, and the option to add a new comment.
- After entering a new comment, the fields in the comment form become empty, and the newly added comment is added to the list of displayed comments.

### Technical Requirements

- The web application is a single-page application.
- After loading the page `index.html`, all subsequent actions are implemented by sending AJAX requests.
- The server-side implementation uses the JAX-RS specification to create a RESTful API.
- Posts and their comments are stored in memory.
- jQuery is used for the frontend.

---

# Public Blog - Assignment 6

### Assignment Overview
This platform serves as a public space for publishing posts, news, and journalistic articles. It is accessible to all registered users, who can add new posts by including the author's name, title, and content. The platform also allows users to view all published posts, leave comments, and read comments from others.

### Scenario
1. **Login Form:** To allow users to view existing posts and submit new ones, a login form is necessary. The login form contains two fields: username and password. After successful login, the user is issued a token that must be sent with every subsequent request (viewing posts, adding posts, commenting).

2. **List of Published Posts:** The main page displays all existing posts. By selecting a post, the user can view its full content along with comments and a form for adding a new comment. A "New post" button on the same page shows the form for creating a new post.

3. **Post Creation Form:** After entering and confirming the form, the form disappears from the page, and the newly added post appears in the list of all posts. The author field is automatically filled with the username of the logged-in user.

4. **Post Content Display:** By clicking on a post, its content is displayed on the same page: title, publication date, author, text, comments, and the option to add a new comment. The author field of the comment is automatically filled with the username of the logged-in user. After entering a new comment, the other fields in the comment form are cleared, and the newly added comment is added to the list of displayed comments.

### Technical Requirements
- **Single Page Application:** The application is designed as a single-page application (SPA). After loading the `posts.html` page, all interactions are handled via AJAX requests.
- **RESTful API:** The backend is implemented using the JAX-RS specification for a RESTful API.
- **Database:** Users, posts, and comments are stored in a MySQL database.
- **Frontend:** jQuery is used for the frontend.
