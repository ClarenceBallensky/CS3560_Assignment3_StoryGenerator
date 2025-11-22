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

## Design Pattern Justifications
**Strategy Pattern:** Needed different AI behaviors (child vs teen vs adult reading level)

**Factory Pattern:** Modifying the prompt effectively

**Observer Pattern:** Curating the output to precise user specifications

## AI Usage
Used ChatGPT to explore alternatives to ComboBoxes in SceneBuilder

Asked: "What can I use instead of a ComboBox in SceneBuilder?"

Modified: Experimented with using a TitledPane in conbination with RadioButtons instead.

Verified: Compared GUI performance and aesthetics with both variations, decided to use a ComboBox paired with a descriptive label.  

## Time spent: ~7 hours
