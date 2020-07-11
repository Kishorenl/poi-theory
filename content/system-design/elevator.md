---
reference: http://thought-works.blogspot.com/2012/11/object-oriented-design-for-elevator-in.html
---
### Object Oriented design for Elevator in a multi-storied apartment

**Object Oriented design for Elevator in a multi-storied apartment**\
**\
**Below is the design for an elevator in a multi-storied building.

A typical lift has buttons(Elevator buttons) inside the cabin to let the user who got in the lift to select his/her desired floor.Similarly each floor has buttons (Floor buttons) to summon the lift to go floors above and floor below respectively. The buttons illuminate indicating the request is accepted. And the lift reaches the requested floor the button stops illuminating.

**Use cases:**\
User

-   presses the floor button to summon the lift
-   presses the elevator button to make the lift move to the desired floor

Floor Button/Elevator Button

-   illuminates when pressed
-   places a elevator request when pressed

Elevator

-   Moves up/down as per instruction
-   Opens/closes the door

Each button press results in an elevator request which has to be served. Each of these requests is tracked at a global place. ElevatorRequests, the class which stores elevator requests can use different strategies to schedule the elevator requests. The elevator is controlled by a controller class which we call ElevatorController. The elevator controller instructs the elevator what to do and also can shutdown/start up the elevator of the building. The elevator controller reads the next elevator request to be processed and serves it.


[![](http://1.bp.blogspot.com/-z7GMS9_lG0E/UJ545jL2flI/AAAAAAAADFE/0eU-5yvhKQw/s1600/elevator-class+diagram.png)](http://1.bp.blogspot.com/-z7GMS9_lG0E/UJ545jL2flI/AAAAAAAADFE/0eU-5yvhKQw/s1600/elevator-class+diagram.png)


Button is abstract class defining common behavior like illuminate, doNotIlluminate. FloorButton, ElevatorButton extend Button type and define placeRequest() which is invoked when the button is pressed. Both the floor button presses and elevator button presses adds requests at a common place.\
ElevatorController runs the show by reading the next request to process and instructing the elevator what to do.\

### How can we extend this to multiple elevators in a sky scraper**\
**\
**In the single elevator scenario, there is an elevator and an elevator controller and a common area where the floor requests and the elevator button request are stored and processed as per the scheduling algorithm.

To extend this to multiple elevators, each elevator will have a corresponding elevator controller. Floor based requests can be served by any elevator where as elevator button requests will be served by the elevator in which the button is pressed.

FloorButton's placeRequest adds a request to the common area which is accessible to all controller. ElevatorButton's placeRequest adds a request to the controller's internal queue as only one elevator is supposed to serve it.\
**\
**Each elevator controller runs as a separate thread and checks on if it can process a floor request along with processing requests made by its own elevator button presses