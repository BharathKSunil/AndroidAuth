## Android Authentication in MVP

This github repository shows you how I have implemented the MVP + repository
architecture for authentication(signIn/login etc...) on android with the unit
test cases implemented for each use case. You may download this github repository
and use the same in your project. It gives you the freedom to change the
repository(backend) and use the desired repository(backend) or model your app is
working on without much code change.
<br/>I will be implementing the backend in **firebase** and some other ways to show
you how this architecture gives you the flexibility.


### What is MVP?

MVP is a software development pattern which uses a layered architecture to
abstract the backend implementation to the view(UI) and vice-versa. This abstraction
provides independence between the layers, makes the code unit testable and
very easy to read and maintain by other developers.
 Here are the roles of every component:
- **Model**
  the data layer. Responsible for handling the business logic and communication
  with the network and database layers.
- **View**
  the UI layer. Displays the data and notifies the Presenter about user actions.
- **Presenter**
  retrieves the data from the Model, applies the UI logic and manages the state
  of the View, decides what to display and reacts to user input notifications
  from the View. Since the View and the Presenter work closely together, they
  need to have a reference to one another. To make the Presenter unit testable
  with JUnit, the View is abstracted and an interface for it used.
  [Architecture Design](mvp_structure.png)


### How to Use?

- Download or clone this github repository.
- Change the package name to your app's package name.
- Make changes as required to the UI in the `views` package.
- Change or add a repository in the `repository` package
- Add test cases to the presenters
