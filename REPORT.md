# Project Report
## Challenges we Faced
**Challenge 1: API Key Issues**

Problem: ChatGPT tokens were innefective as an API key

Solution: Used Google AI Studio's API key instead

Learned: Select a service to provide an API key based on cost, ease of use, and relevant functionality

**Challenge 2: JUnit Testing API Calls**

Problem: Doing real API calls for JUnit testing is slow and uses tokens

Solution: Created a "fake API" using an interface

Learned: JUnit tests shouldn't make real API calls, but should still test whether the correct inputs are being passed

**Challenge 3: SceneBuilder Configuration Setback**

Problem: SceneBuilder would not link with the IDE I was using, IntelliJ

Solution: Specified the proper controller class in SceneBuilder settings

Learned: How to properly configure SceneBuilder with IntelliJ

**Challenge 4: Output Formatting Discrepancy**

Problem: Story that was printed to output window, first it had duplicate chapter headings, then it had additional newlines that did not match the intended formatting or saved stories.

Solution: Modified how chapters were added and accessed, standardized spacing

Learned: How to debugging across different classes and standardize outputs 

## Design Pattern Justifications
**Strategy Pattern:** Needed different AI behaviors (child vs teen vs adult reading level)

**Factory Pattern:** Modifying the prompt effectively

**Observer Pattern:** Curating the output to precise user specifications

**Singleton Pattern:** Create a single instance for program

## OOP Four Pillars

**Abstraction:** We use a model, view, controller architecture that hides the implementation in the model and controller components 

**Encapsulation:** Book class uses private variables with public getters and setters

**Inheritance:** JavaFX uses inheritance for all its UI components, we extend from the parent Application in order to override its start() method in Main

**Polymorphism:** We have an Interface for the API so we can have a FakeClient class for testing purposes and an actual APIClient for making the API calls


## AI Usage
**Used ChatGPT to explore alternatives to ComboBoxes in SceneBuilder**

Asked: "What can I use instead of a ComboBox in SceneBuilder?"

Modified: Experimented with using a TitledPane in conbination with RadioButtons instead

Verified: Compared GUI performance and aesthetics with both variations, decided to use a ComboBox paired with a descriptive label


**Used ChatGPT to enhance maintainability**

Asked: "What's the best way to implement saving stories and adding new chapters?"

Modified: Per AI suggestion, we transformed the Story class into the Book class (and related methods)

Verified: Conducted extensive testing, including JUnit tests


## Time spent: ~20 hours
