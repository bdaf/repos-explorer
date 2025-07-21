<h1>Github repos explorer API.</h1>

Using this application after provided login of GitHub user we can get his all repos which are not forks. Informations, which we get in the response, are:

 - Repository Name
 - Owner Login
 - For each branch it’s name and last commit sha

Regarding limits on github API for not authenticated users, user can provide Authentication token in header.

<h2>Endpoint address and example of response.</h2>

<h3>Endpoint link:</h3>

    http://localhost:8080/github/repositories/<github_user>

<h3>Example with response:</h3>

Link:

    http://localhost:8080/github/repositories/<github_user>

Response:

<img width="1244" height="771" alt="image" src="https://github.com/user-attachments/assets/8f65bb03-a10b-4444-9dfa-88499b12887d" />

<h2>Exceptions in responses.</h2>


<h3>If login of GitHub user will not be found, following error response will occur:</h3>

<img width="1263" height="347" alt="image" src="https://github.com/user-attachments/assets/2aea477b-0d80-431a-8525-bd4e41d3ca37" />


<h3>If Authentication token will be provided and it will not be valid:</h3>

<img width="1248" height="420" alt="image" src="https://github.com/user-attachments/assets/da55a44e-6af4-4f8a-85fe-afb0249f8f87" />



